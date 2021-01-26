package thinking.cloud.utils.exception;

/**
 * @author thinking
 * @date 2021/1/18 17:00
 */
public class DateFormatException extends RuntimeException{
    public DateFormatException() {
    }

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateFormatException(Throwable cause) {
        super(cause);
    }
}
