package url_shortener.contollers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url_shortener.models.Url;
import url_shortener.services.IUrlShortenerService;

import java.net.URI;

@RestController
@RequestMapping("/url")
public class UrlShortenerController {
    private IUrlShortenerService urlShortenerService;

    public UrlShortenerController(IUrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping()
    public ResponseEntity<String> generateShortCode(@RequestBody Url url) {
        String code= urlShortenerService.generateShortUrl(url.getLongUrl());
        return ResponseEntity.ok().body(code);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<String> getUrl(@PathVariable("shortCode") String shortCode) {
        String url=urlShortenerService.getLongUrl(shortCode);
        if(url==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new ResponseEntity(headers, HttpStatusCode.valueOf(302));
    }
}
