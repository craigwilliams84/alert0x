package eth.craig.alert0x.exception;

public class RateLimitReachedException extends Exception {

    public RateLimitReachedException() {
        super("Rate limit reached");
    }
}
