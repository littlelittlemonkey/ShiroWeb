package com.lyc.cache.shiro;

import com.lyc.cache.JedisUtil;
import com.lyc.utils.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * 实现shiro的缓存接口
 * 自定义redis接口
 * @param <K>
 * @param <V>
 */

@Component
public class RedisCache<K,V> implements Cache<K,V> {

    private Logger log = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private JedisUtil jedisUtil;

    //shiro缓存前缀
    private String keyPrefix = "shiro:";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        }else{
            return SerializeUtil.serialize((Serializable) key);
        }
    }
    @Override
    public Object get(Object key) throws CacheException {

        byte[] bytes = getByteKey(key);
        byte[] value = jedisUtil.getJedis().get(bytes);
        if(value == null){
            log.info("----------缓存中没有key："+getByteKey(key)+"----------");
            return null;
        }
        log.info("----------从缓存中获取key："+getByteKey(key)+"----------");
        return SerializeUtil.deserialize(value);
    }

    /**
     * 将shiro的缓存保存到redis中
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {

        Jedis jedis = jedisUtil.getJedis();
        String result = jedis.set(getByteKey(key), SerializeUtil.serialize(value));
//        byte[] bytes = jedis.get(getByteKey(key));
//        Object object = SerializeUtil.deserialize(bytes);
        log.info("----------将key:"+key+"存入redis中----------");
        log.info("----------将key:"+getByteKey(key)+"存入redis中----------");
        return result;

    }

    /**
     * 删除key
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        Jedis jedis = jedisUtil.getJedis();

        byte[] bytes = jedis.get(getByteKey(key));

        jedis.del(getByteKey(key));

        return SerializeUtil.deserialize(bytes);
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        jedisUtil.getJedis().flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = jedisUtil.getJedis().dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = jedisUtil.getJedis().keys(new String("*").getBytes());
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(SerializeUtil.deserialize(bs));
        }
        return set;
    }


    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            byte[] bytes = jedisUtil.getJedis().get(getByteKey(key));
            values.add(SerializeUtil.deserialize(bytes));
        }
        return values;
    }
}
