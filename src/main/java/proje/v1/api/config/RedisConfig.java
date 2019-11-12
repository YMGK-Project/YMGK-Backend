package proje.v1.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import proje.v1.api.domian.rollcall.RollCall;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory getJedisConnectionFactory(){
        return new JedisConnectionFactory(); // default configuration
    }

    @Bean
    public RedisTemplate<String, RollCall> redisTemplate(){
        RedisTemplate<String,RollCall> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
