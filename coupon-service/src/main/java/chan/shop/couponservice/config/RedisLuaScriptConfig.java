package chan.shop.couponservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisLuaScriptConfig {

    @Bean
    public DefaultRedisScript<Long> couponIssueScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                """
                local exists = redis.call('EXISTS', KEYS[2])
                if exists == 1 then
                  return -1
                end
    
                local count = redis.call('INCR', KEYS[1])
                if count > tonumber(ARGV[1]) then
                  return 0
                end
    
                redis.call('SET', KEYS[2], 1, 'EX', ARGV[2])
                return 1
                """
        );
        script.setResultType(Long.class);
        return script;
    }
}
