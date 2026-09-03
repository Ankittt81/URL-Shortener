package url_shortener.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import url_shortener.repo.UrlRepo;

import java.time.Instant;

@Component
public class CleanupScheduler {
    @Autowired
    private UrlRepo  urlRepo;

    @Transactional
    @Scheduled(fixedRate = 300000)
    public void deleteExpiredUrls() {
        urlRepo.deleteExpiredUrls(Instant.now());
    }
}
