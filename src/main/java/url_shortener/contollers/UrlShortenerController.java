package url_shortener.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url_shortener.services.IUrlShortenerService;

@RestController
@RequestMapping("/url")
public class UrlShortenerController {
    private IUrlShortenerService urlShortenerService;

    public UrlShortenerController(IUrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping()
    public ResponseEntity<String> generateShortCode(@RequestBody String longUrl) {
        String code= urlShortenerService.generateShortUrl(longUrl);
        return ResponseEntity.ok().body(code);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<String> getUrl(@PathVariable("shortCode") String shortCode) {
        String url=urlShortenerService.getLongUrl(shortCode);
        return ResponseEntity.ok().body(url);
    }
}
