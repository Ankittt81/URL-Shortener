package url_shortener.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Url {
    private String longUrl;
    private String shortcode;
}
