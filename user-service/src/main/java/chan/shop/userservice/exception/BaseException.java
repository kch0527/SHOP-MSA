package chan.shop.userservice.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    public BaseException(final String message) {
        super(message);
    }
}
