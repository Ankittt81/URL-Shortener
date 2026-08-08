package url_shortener.services;

public interface IUrlShortenerService {
    String generateShortUrl(String longUrl);
    String getLongUrl(String shortcode);
}
