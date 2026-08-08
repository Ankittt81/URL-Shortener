package url_shortener.services;

import org.springframework.stereotype.Service;
import url_shortener.generator.ShortCodeGeneratorStrategy;

import java.security.SecureRandom;
import java.util.HashMap;


@Service
public class UrlShortenerService implements IUrlShortenerService{
    private HashMap<String,String> shortToLong=new HashMap<>();
    private HashMap<String,String> longToShort=new HashMap<>();
    SecureRandom  random = new SecureRandom();
    private String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private ShortCodeGeneratorStrategy  shortCodeGeneratorStrategy;
    private String url="https://short.ly/";

    public UrlShortenerService(ShortCodeGeneratorStrategy shortCodeGeneratorStrategy) {
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
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<6;i++){
                int randomInt=random.nextInt(62);
                char ch=characters.charAt(randomInt);
                sb.append(ch);
            }
            String suffix=sb.toString();
            shortCode= shortCodeGeneratorStrategy.generateShortCode(longUrl+suffix);
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
