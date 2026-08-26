package url_shortener.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import url_shortener.exceptions.ShortUrlNotFoundException;
import url_shortener.factory.StrategyFactory;
import url_shortener.generator.ShortCodeGeneratorStrategy;
import url_shortener.models.Url;
import url_shortener.repo.UrlRepo;

import java.util.Optional;

@Service
@Primary
public class UrlShortenerServiceImpl implements  IUrlShortenerService{
    private UrlRepo urlRepo;
    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;
    @Value("${short-url.base-url}")
    private String url;

    public UrlShortenerServiceImpl(UrlRepo urlRepo, StrategyFactory factory, @Value("${url.shortener.strategy}") String name) {
        this.urlRepo = urlRepo;
        this.shortCodeGeneratorStrategy = factory.getStrategy(name);
    }

    @Override
    public String generateShortUrl(String longUrl) {
        Optional<Url> optional = urlRepo.findByLongUrl(longUrl);
        if(optional.isPresent()){
            String shortCode=optional.get().getShortCode();
            return url+shortCode;
        }
        String shortcode=shortCodeGeneratorStrategy.generateShortCode(longUrl);
        while(true){
            boolean exists = urlRepo.existsByShortCode(shortcode);
            if(!exists){
                Url entity=new Url();
                entity.setShortCode(shortcode);
                entity.setLongUrl(longUrl);
                urlRepo.save(entity);
                return url+shortcode;
            }
            else shortcode=shortCodeGeneratorStrategy.generateAnotherCode(shortcode);
        }
    }

    @Override
    public String getLongUrl(String shortcode) {
        Url url=urlRepo.findByShortCode(shortcode).orElseThrow(()->new ShortUrlNotFoundException("Url not found!"));
        return url.getLongUrl();
    }
}
