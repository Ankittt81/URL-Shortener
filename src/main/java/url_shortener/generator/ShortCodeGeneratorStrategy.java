package url_shortener.generator;


public interface ShortCodeGeneratorStrategy {
    String generateShortCode(String longUrl);
}
