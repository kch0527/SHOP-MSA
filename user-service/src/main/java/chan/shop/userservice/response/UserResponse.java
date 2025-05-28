package chan.shop.userservice.response;

import chan.shop.userservice.entity.Role;
import chan.shop.userservice.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createAt;

    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.userId = user.getUserId();
        response.name = user.getName();
        response.email = user.getEmail();
        response.password = user.getPassword();
        response.role = user.getRole();
        response.createAt = user.getCreateAt();
        return response;
    }
}
