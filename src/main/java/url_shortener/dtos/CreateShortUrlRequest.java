package url_shortener.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShortUrlRequest {
    private String longUrl;
    private Integer expiresIn;
    private String alias;
}
