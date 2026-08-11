package url_shortener.generator;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component("increment")
public class IncrementalIdStrategy implements ShortCodeGeneratorStrategy{
    private final AtomicLong counter = new AtomicLong(62L * 62 * 62 * 62 * 62 * 62);
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    @Override
    public String generateShortCode(String longUrl) {
        long id = counter.getAndIncrement();
        return base62Encode(id);
    }

    @Override
    public String generateAnotherCode(String longUrl) {
        return generateShortCode(longUrl);
    }
    private String base62Encode(long num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % 62);
            sb.append(CHARACTERS.charAt(remainder));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
