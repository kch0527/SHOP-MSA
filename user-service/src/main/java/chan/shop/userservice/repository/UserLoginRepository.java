package chan.shop.userservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class UserLoginRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String REFRESH_TOKEN = "refresh-token::%s";

    private static final Duration TOKEN_TTL = Duration.ofDays(1);

    public boolean issueToken(Long userId, String refreshToken) {
        String key = generateKey(userId);
        return redisTemplate.opsForValue().setIfAbsent(key, refreshToken, TOKEN_TTL);
    }

    private String generateKey(Long userId) {
        return REFRESH_TOKEN.formatted(userId);
    }

}
