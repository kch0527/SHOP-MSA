package chan.shop.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "user")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private Long userId;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
    private LocalDateTime createAt;

    public static User create(Long userId, String name, String email, String password) {
        User user = new User();
        user.userId = userId;
        user.name = name;
        user.email = email;
        user.password = password;
        user.createAt = LocalDateTime.now();
        return user;
    }
}
