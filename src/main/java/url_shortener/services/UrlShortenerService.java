package url_shortener.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import url_shortener.generator.ShortCodeGeneratorStrategy;

import java.security.SecureRandom;
import java.util.HashMap;


@Service
public class UrlShortenerService implements IUrlShortenerService{
    private HashMap<String,String> shortToLong=new HashMap<>();
    private HashMap<String,String> longToShort=new HashMap<>();



    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;
    private String url="https://short.ly/";

    public UrlShortenerService(@Qualifier("RandomStrategy") ShortCodeGeneratorStrategy shortCodeGeneratorStrategy) {
        this.shortCodeGeneratorStrategy = shortCodeGeneratorStrategy;
    }

    @Override
    public String generateShortUrl(String longUrl) {
        if(longUrl!=null){
            longUrl=longUrl.trim();
        }
        if(longToShort.containsKey(longUrl)){
            return url+longToShort.get(longUrl);
        }
        String shortCode= shortCodeGeneratorStrategy.generateShortCode(longUrl);
        while(shortToLong.containsKey(shortCode)){
            shortCode= shortCodeGeneratorStrategy.generateAnotherCode(longUrl);
        }
        shortToLong.put(shortCode,longUrl);
        longToShort.put(longUrl,shortCode);
        String shortUrl=url+shortCode;
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortcode) {
        return shortToLong.get(shortcode);
    }
}
