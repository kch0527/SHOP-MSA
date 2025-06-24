package chan.shop.userservice.response;

import chan.shop.userservice.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginResponse {
    private Long userId;
    private String name;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse from(User user, String accessToken, String refreshToken) {
        LoginResponse response = new LoginResponse();
        response.userId = user.getUserId();
        response.name = user.getName();
        response.accessToken = accessToken;
        response.refreshToken = refreshToken;

        return response;
    }
}
