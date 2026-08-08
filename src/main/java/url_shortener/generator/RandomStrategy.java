package url_shortener.generator;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
@Component("RandomStrategy")
public class RandomStrategy implements ShortCodeGeneratorStrategy{
    SecureRandom random = new SecureRandom();
    private String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generateShortCode(String longUrl) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<6;i++) {
            int randomInt = random.nextInt(62);
            char ch = characters.charAt(randomInt);
            sb.append(ch);
        }
        return sb.toString();
    }

    @Override
    public String generateAnotherCode(String longUrl) {
        return generateShortCode(longUrl);
    }

}
