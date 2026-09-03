package url_shortener.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import url_shortener.dtos.CreateShortUrlRequest;
import url_shortener.exceptions.AliasAlreadyExistsException;
import url_shortener.exceptions.ShortUrlGoneException;
import url_shortener.exceptions.ShortUrlNotFoundException;
import url_shortener.factory.StrategyFactory;
import url_shortener.generator.ShortCodeGeneratorStrategy;
import url_shortener.mapper.UrlMapper;
import url_shortener.models.Url;
import url_shortener.repo.UrlRepo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Primary
public class UrlShortenerServiceImpl implements  IUrlShortenerService{
    private UrlRepo urlRepo;
    private UrlMapper urlMapper;
    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;
    @Value("${short-url.base-url}")
    private String url;

    public UrlShortenerServiceImpl(UrlRepo urlRepo,UrlMapper urlMapper, StrategyFactory factory, @Value("${url.shortener.strategy}") String name) {
        this.urlRepo = urlRepo;
        this.urlMapper = urlMapper;
        this.shortCodeGeneratorStrategy = factory.getStrategy(name);
    }

    @Override
    public String generateShortUrl(CreateShortUrlRequest request) {
        String longUrl=request.getLongUrl();
        if(request.getAlias()!=null && !request.getAlias().isBlank()){
            return aliasUrl(request);
        }
        String shortcode=shortCodeGeneratorStrategy.generateShortCode(longUrl);
        while(true){
            boolean exists = urlRepo.existsByShortCode(shortcode);
            if(!exists){
                Url entity=urlMapper.toEntity(request,shortcode);
                try {
                    urlRepo.save(entity);
                    return url + shortcode;
                }catch (DataIntegrityViolationException e){
                    shortcode=shortCodeGeneratorStrategy.generateAnotherCode(longUrl);
                }
            }
            else shortcode=shortCodeGeneratorStrategy.generateAnotherCode(longUrl);
        }
    }

    public String aliasUrl(CreateShortUrlRequest request){
        boolean exists=urlRepo.existsByShortCode(request.getAlias());
        if(exists){
            throw new AliasAlreadyExistsException("Alias already exists");
        }
        else{
            String alias=request.getAlias();
            Url entity=urlMapper.toEntity(request,alias);
            try{
                urlRepo.save(entity);
            }catch (DataIntegrityViolationException e){
                throw new AliasAlreadyExistsException("Alias already exists");
            }
            return url+alias;
        }
    }

    @Transactional
    @Override
    public String getLongUrl(String shortcode) {
        Url url=urlRepo.findByShortCode(shortcode)
                .orElseThrow(()->new ShortUrlNotFoundException("Url not found!"));
        if(!url.getActive()){
            throw new ShortUrlGoneException("Url is not active!");
        }
        if(url.getExpiresAt()!=null && url.getExpiresAt().isBefore(Instant.now())){
            throw new ShortUrlGoneException("Url has expired!");
        }
        urlRepo.incrementClickCount(shortcode);
        return url.getLongUrl();
    }
}
