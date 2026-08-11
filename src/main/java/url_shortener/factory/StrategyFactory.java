package url_shortener.factory;

import org.springframework.stereotype.Component;
import url_shortener.generator.HashStrategy;
import url_shortener.generator.IncrementalIdStrategy;
import url_shortener.generator.RandomStrategy;
import url_shortener.generator.ShortCodeGeneratorStrategy;

@Component
public class StrategyFactory {
    private HashStrategy  hashStrategy;
    private RandomStrategy  randomStrategy;
    private IncrementalIdStrategy incrementalIdStrategy;

    public StrategyFactory(HashStrategy hashStrategy, RandomStrategy randomStrategy, IncrementalIdStrategy incrementalIdStrategy) {
        this.hashStrategy = hashStrategy;
        this.randomStrategy = randomStrategy;
        this.incrementalIdStrategy = incrementalIdStrategy;
    }

    public ShortCodeGeneratorStrategy getStrategy(String type) {
        if("random".equalsIgnoreCase(type)) {
            return randomStrategy;
        }
        else  if("hash".equalsIgnoreCase(type)) {
            return hashStrategy;
        }
        else if("increment".equalsIgnoreCase(type)) {
            return incrementalIdStrategy;
        }
        throw new IllegalArgumentException("Invalid strategy type");
    }
}
