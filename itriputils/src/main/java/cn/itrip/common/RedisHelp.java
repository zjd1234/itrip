package cn.itrip.common;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
@Repository
public class RedisHelp {
    public  void setRedis(String key,String value,int time){
        Jedis redis=new Jedis("127.0.0.1",6379);
        redis.auth("123456");
        redis.setex(key,time,value);
    }

    public  String getRedis(String key){
        Jedis redis=new Jedis("127.0.0.1",6379);
        redis.auth("123456");
        return redis.get(key);

    }
}
