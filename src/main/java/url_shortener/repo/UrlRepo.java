package url_shortener.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import url_shortener.models.Url;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<Url,Long> {
    Optional<Url> findByLongUrl(String longUrl);
    boolean existsByShortCode(String shortCode);
    Optional<Url> findByShortCode(String shortCode);

    @Modifying
    @Query(""" 
           update Url u
           set u.clickCount=u.clickCount+1
           where u.shortCode=:shortCode
     """)
    void incrementClickCount(@Param("shortCode") String shortCode);

    @Modifying
    @Query(" delete from Url u where u.expiresAt is not null and  u.expiresAt<=:now")
    void deleteExpiredUrls(@Param("now") Instant now);
}
