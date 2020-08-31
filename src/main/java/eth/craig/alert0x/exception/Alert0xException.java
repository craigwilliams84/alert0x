package eth.craig.alert0x.exception;

public class Alert0xException extends RuntimeException {

    public Alert0xException(String message) {
        super(message);
    }

    public Alert0xException(String message, Throwable cause) {
        super(message, cause);
    }
}
