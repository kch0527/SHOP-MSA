package chan.shop.userservice.service;

import chan.shop.commonservice.outboxmessagerelay.OutboxEventPublisher;
import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.userservice.entity.User;
import chan.shop.userservice.repository.UserRepository;
import chan.shop.userservice.request.UserCreateRequest;
import chan.shop.userservice.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final Snowflake snowflake = new Snowflake();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if(optionalUser.isPresent()) {
            throw new IllegalArgumentException("An existing member.");
        }

        User user = userRepository.save(
                User.create(snowflake.nextId(), request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()))
        );

        // outboxEventPublisher 생성예정

        return UserResponse.from(user);
    }


}
