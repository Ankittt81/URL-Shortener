package url_shortener.contollers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url_shortener.dtos.CreateShortUrlRequest;
import url_shortener.dtos.CreateShortUrlResponse;
import url_shortener.services.IUrlShortenerService;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/url")
public class UrlShortenerController {
    private IUrlShortenerService urlShortenerService;

    public UrlShortenerController(IUrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping()
    public ResponseEntity<CreateShortUrlResponse> generateShortCode( @RequestBody CreateShortUrlRequest request) {
        String code= urlShortenerService.generateShortUrl(request);
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

}
