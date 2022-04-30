package cn.curleyg.redis;

import cn.curleyg.redis.entity.UserCacheObject;
import cn.curleyg.redis.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class RedisApplicationTests {

    @Test
    void contextLoads() {

    }

}
