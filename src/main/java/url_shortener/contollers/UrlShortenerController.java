package url_shortener.contollers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url_shortener.dtos.CreateShortUrlRequest;
import url_shortener.dtos.CreateShortUrlResponse;
import url_shortener.exceptions.ShortUrlNotFoundException;
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
    public ResponseEntity<CreateShortUrlResponse> generateShortCode( @RequestBody CreateShortUrlRequest request) {
        String code= urlShortenerService.generateShortUrl(request.getLongUrl());
        return ResponseEntity.ok().body(new CreateShortUrlResponse(code));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getUrl(@PathVariable("shortCode") String shortCode) {
        String url=urlShortenerService.getLongUrl(shortCode);
        if(url==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//        HttpHeaders headers=new HttpHeaders();
//        headers.setLocation(URI.create(url));
//        return new ResponseEntity(headers, HttpStatusCode.valueOf(302));
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<String> handleShortUrlNotFoundException(ShortUrlNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
