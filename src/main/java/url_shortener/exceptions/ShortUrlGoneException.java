package url_shortener.exceptions;

public class ShortUrlGoneException extends RuntimeException {
    public ShortUrlGoneException(String message) {
        super(message);
    }
}
