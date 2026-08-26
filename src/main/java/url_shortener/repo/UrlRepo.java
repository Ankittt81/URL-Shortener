package url_shortener.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import url_shortener.models.Url;

import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<Url,Long> {
    Optional<Url> findByLongUrl(String longUrl);
    boolean existsByShortCode(String shortCode);
    Optional<Url> findByShortCode(String shortCode);
}
