package url_shortener.mapper;

import org.springframework.stereotype.Component;
import url_shortener.dtos.CreateShortUrlRequest;
import url_shortener.models.Url;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class UrlMapper {

    public Url toEntity(CreateShortUrlRequest request,String shortCode){
        Url url=new Url();
        url.setShortCode(shortCode);
        url.setLongUrl(request.getLongUrl());
        if(request.getExpiresIn()!=null){
            Instant expires=Instant.now().plus(request.getExpiresIn(), ChronoUnit.DAYS);
            url.setExpiresAt(expires);
        }
        return url;
    }
}
