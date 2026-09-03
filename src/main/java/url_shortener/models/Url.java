package url_shortener.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(columnDefinition = "TEXT")
    private String longUrl;
    @Column(nullable = false,unique = true)
    private String shortCode;
    private Boolean active=true;
    private Long clickCount=0L;
    @CreationTimestamp
    private Instant createdAt;
    private Instant expiresAt;
}
