package com.lyc.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Repository
public class JedisUtil {

    private Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisConnectionException e) {
        	String message = StringUtils.trim(e.getMessage());
        	if("Could not get a resource from the pool".equalsIgnoreCase(message)){
        		System.out.println("---------请检查你的redis服务---------");
        		System.exit(0);//停止项目
        	}
        	throw new JedisConnectionException(e.getMessage());
        } catch (Exception e) {
            System.out.println("---------请检查你的redis服务---------");
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        jedis.close();
    }
}
