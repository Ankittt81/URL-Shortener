package url_shortener.services;

import url_shortener.dtos.CreateShortUrlRequest;

public interface IUrlShortenerService {
    String generateShortUrl(CreateShortUrlRequest request);
    String getLongUrl(String shortcode);
}
