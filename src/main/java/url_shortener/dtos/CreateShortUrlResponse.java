package url_shortener.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShortUrlResponse {
    private String shortUrl;

    public CreateShortUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
