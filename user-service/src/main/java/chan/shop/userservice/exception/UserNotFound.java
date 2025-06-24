package chan.shop.userservice.exception;

public class UserNotFound extends BaseException{
    private static final String MESSAGE = "존재하지 않는 상품";

    public UserNotFound(String message) {
        super(message);
    }

    public UserNotFound() {
        super(MESSAGE);
    }
}
