package url_shortener.services;

import org.springframework.stereotype.Service;
import url_shortener.generator.ShortCodeGeneratorStrategy;

import java.util.HashMap;

@Service
public class UrlShortenerService implements IUrlShortenerService{
    private HashMap<String,String> urlMap;
    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;

    public UrlShortenerService(ShortCodeGeneratorStrategy shortCodeGeneratorStrategy,HashMap<String,String> urlMap) {
        this.shortCodeGeneratorStrategy = shortCodeGeneratorStrategy;
        this.urlMap = urlMap;
    }

    @Override
    public String generateShortUrl(String longUrl) {
        String shortCode= shortCodeGeneratorStrategy.generateShortCode(longUrl);
        urlMap.put(shortCode,longUrl);
        return shortCode;
    }

    @Override
    public String getLongUrl(String shortcode) {
        return urlMap.get(shortcode);
    }
}
