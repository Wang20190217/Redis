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

   @Autowired
    private StringRedisTemplate redisTemplate;


    @Qualifier("redisTemplate")
    @Autowired
   private RedisTemplate Template;

    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
        //向redis中添加数据
        redisTemplate.opsForValue().set("key8", "aaa");
        //根据键值取出数据
        System.out.println(redisTemplate.opsForValue().get("key"));

    }

    @Test
    void testTemplate(){
        //向redis中添加数据
        Template.opsForValue().set("key8", "aaa");
        //根据键值取出数据
        System.out.println(Template.opsForValue().get("key8"));

    }

    @Test
    public void testStringSetKeyUserCache() {
        UserCacheObject object = new UserCacheObject();
        object.setId(1);
        object.setName("Curley G");
        object.setGender(1); // 男
        String key = String.format("user:%d", object.getId());
        System.out.println(key);
        Template.opsForValue().set(key, object);
    }

    @Test
    public void testStringGetKeyUserCache() {
        String key = String.format("user:%d", 1);
        System.out.println(key);
        Object value = Template.opsForValue().get(key);
        System.out.println(value);
    }

    @Test
    public void testRedisUtil() {
     redisUtil.set("user:2",new UserCacheObject(1,"haha",2));
        System.out.println(redisUtil.get("user:2"));
    }


}
