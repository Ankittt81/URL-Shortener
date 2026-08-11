package url_shortener.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import url_shortener.exceptions.ShortUrlNotFoundException;
import url_shortener.factory.StrategyFactory;
import url_shortener.generator.ShortCodeGeneratorStrategy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class UrlShortenerService implements IUrlShortenerService{
    private Map<String,String> shortToLong=new ConcurrentHashMap<>();
    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;
    private String url="https://short.ly/";

    public UrlShortenerService(StrategyFactory factory, @Value("${url.shortener.strategy}") String name) {
        this.shortCodeGeneratorStrategy = factory.getStrategy(name);
    }

    @Override
    public String generateShortUrl(String longUrl) {
        if(longUrl==null || longUrl.trim().isEmpty()){
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if(!isValidUrl(longUrl)){
            throw new IllegalArgumentException("URL is not valid");
        }
        String shortCode= shortCodeGeneratorStrategy.generateShortCode(longUrl);
        while(true){
            String existing=shortToLong.putIfAbsent(shortCode,longUrl);
            if(existing==null){
                return url+shortCode;
            }
            else shortCode= shortCodeGeneratorStrategy.generateAnotherCode(longUrl);
        }
    }

    @Override
    public String getLongUrl(String shortcode) {
        if(!shortToLong.containsKey(shortcode)){
            throw new ShortUrlNotFoundException("Short Url Not Found");
        }
        return shortToLong.get(shortcode);
    }

    private boolean isValidUrl(String url){
        try{
            URI uri = new URI(url);
            return ("http".equalsIgnoreCase(uri.getScheme())
                    || ("https".equalsIgnoreCase(uri.getScheme())
                     && uri.getHost()!=null));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
