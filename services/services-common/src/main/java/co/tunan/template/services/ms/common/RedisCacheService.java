package co.tunan.template.services.ms.common;

import cn.hutool.core.util.StrUtil;
import co.tunan.template.services.ms.common.configuration.AppConfig;
import lombok.Getter;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @title: Created by trifolium.
 * @author: trifolium
 * @date: 2021/9/1
 * @modified :
 */
@Component
public class RedisCacheService {

    private static final long NOT_EXPIRE = -1;

    @Inject
    private AppConfig appConfig;

    @Inject
    private RedisTemplate<String, Object> redisTemplate;

    @Inject
    private HashOperations<String, String, Object> hashOps;

    @Getter
    @Inject
    private ValueOperations<String, Object> valueOps;

    @Inject
    private ListOperations<String, Object> listOps;

    @Inject
    private SetOperations<String, Object> setOps;

    @Inject
    private ZSetOperations<String, Object> zSetOps;

    @Inject
    private GeoOperations<String, Object> geoOps;

    public void set(String key, Object value, long expire) {
        valueOps.set(prefixed(key), value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
    }

    public void setIfPresent(String key, Function<Object, Object> function, long expire, TimeUnit timeUnit) {
        if (function == null) {
            throw new NullPointerException("function 不能为空");
        }
        key = prefixed(key);
        Object oldValue = get(key, Object.class);
        Object newValue = function.apply(oldValue);
        if (newValue != null) {
            if (oldValue != null) {
                update(key, newValue);
            } else {
                set(key, newValue, expire, timeUnit);
            }
        } else {
            delete(key);
        }
    }

    public void setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        if (value == null) {
            return;
        }
        key = prefixed(key);
        Object oldValue = get(key, Object.class);
        if (oldValue == null) {
            set(key, value, expire, timeUnit);
        }
    }

    public int atomicOpt(String key, Integer initValue, Integer increment, long timeout, TimeUnit timeUnit) {
        key = prefixed(key);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            initValue = null;
        } else {
            if (initValue == null) {
                initValue = 0;
            }
        }
        RedisAtomicInteger counter;
        if (initValue == null) {
            counter = new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        } else {
            counter = new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()),
                    initValue);
        }
        //设置超时时间
        if (null != timeUnit) {
            counter.expire(timeout, timeUnit);
        }
        return counter.addAndGet(increment);
    }

    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        valueOps.set(prefixed(key), value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, timeUnit);
        }
    }

    public void setNoPrefix(String key, Object value, long expire) {
        valueOps.set(key, value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Boolean hasKey(String key) {

        return redisTemplate.hasKey(prefixed(key));
    }

    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    public void expire(String key, Long expire, TimeUnit timeUnit) {
        redisTemplate.expire(prefixed(key), expire, timeUnit);
    }

    public Long incrementAndGet(String key) {
        return redisTemplate.opsForValue().increment(prefixed(key));
    }

    public Long decrementAndGet(String key) {
        return redisTemplate.opsForValue().decrement(prefixed(key));
    }

    public Long incrementAndGetNotPrefix(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long decrementAndGetNotPrefix(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(prefixed(key));
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        Object value = valueOps.get(prefixed(key));

        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }

        return valueOf(value, clazz);

    }


    public <T> T getNoPrefix(String key, Class<T> clazz, long expire) {
        Object value = valueOps.get(key);

        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

        return valueOf(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * 设置值，但是不会重置过期时间
     *
     * @param key
     * @param value
     */
    public void update(String key, Object value) {
        Long expire = redisTemplate.getExpire(prefixed(key), TimeUnit.SECONDS);

        // 1秒的延迟
        if (expire != null && expire > 1) {
            redisTemplate.opsForValue().set(prefixed(key), value, expire, TimeUnit.SECONDS);
        }
    }

    public String getString(String key, long expire) {
        Object value = valueOps.get(prefixed(key));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public String getString(String key) {
        return getString(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(prefixed(key));
    }

    /**
     * 删除key开头的所有数据
     *
     * @param key
     */
    public void deleteKeys(String key) {
        Set<String> keys = redisTemplate.keys(prefixed(key) + "*");
        redisTemplate.delete(keys);
    }

    public void deleteNoPrefixedKeys(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(prefixed(key), TimeUnit.SECONDS);
    }

    private String prefixed(String key) {
        return StrUtil.isEmpty(appConfig.getCacheKeyPrefix()) ? key : appConfig.getCacheKeyPrefix() + key;
    }


    public Long leftPush(String key, Object value) {
        return leftPush(key, value, NOT_EXPIRE);
    }

    public Long rightPush(String key, Object value) {
        return listOps.rightPush(prefixed(key), value);
    }

    /**
     * 从key列表左边（从头部）插入值
     *
     * @param key    键（不能为空）
     * @param value  插入列表的值
     * @param expire 失效时间（秒）
     * @return 返回列表插入后的长度
     */
    public Long leftPush(String key, Object value, long expire) {
        Long size = listOps.leftPush(prefixed(key), value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        return size;
    }

    public Long leftPushAll(String key, Collection<Object> collection) {
        return leftPushAll(key, collection, NOT_EXPIRE);
    }

    public Long leftPushAll(String key, Collection<Object> collection, long expire) {
        Long size = listOps.leftPushAll(prefixed(key), collection);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        return size;
    }

    /**
     * 获取指定key的范围内的value值的List列表
     * （0，-1）反回所有值列表
     * 0 表示列表的第一个元素，
     * 1 表示列表的第二个元素，以此类推。
     * -1 表示列表的最后一个元素，
     * -2 表示列表的倒数第二个元素，以此类推。
     */
    public List<Object> range(String key, long start, long end) {
        return listOps.range(prefixed(key), start, end);
    }

    public <T> List<T> range(String key, long start, long end, Class<T> clazz) {

        List<Object> objects = listOps.range(prefixed(key), start, end);

        List<T> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(objects)) {
            objects.forEach(o -> result.add(valueOf(o, clazz)));
        }

        return result;
    }

    /**
     * 删除列表中第一个遇到的value值，count指定删除多少个
     *
     * @param key   键（不能为空）
     * @param count 删除个数
     * @param value 删除的值
     * @return 返回删除后列表长度
     */
    public Long remove(String key, long count, Object value) {
        return listOps.remove(prefixed(key), count, value);
    }

    /**
     * 移除列表中的第一个值，并返回该值
     *
     * @param key 键（不能为空）
     * @return 移除的值
     */
    public Object leftPop(String key) {
        return listOps.leftPop(prefixed(key));
    }

    /**
     * 移除列表中右边的第一个值，并返回该值
     *
     * @param key 键（不能为空）
     * @return 移除的值
     */
    public Object rightPop(String key) {
        return listOps.rightPop(prefixed(key));
    }

    /**
     * 移除列表的第一个值，并且返回（阻塞版本的leftPop(K key)）
     * 如果列表为空，则一直阻塞指定的时间单位
     *
     * @param key      键（不能为空）
     * @param timeout  阻塞时间
     * @param timeUnit 时间单位
     * @return 移除的值
     */
    public Object leftPop(String key, long timeout, TimeUnit timeUnit) {
        return listOps.leftPop(prefixed(key), timeout, timeUnit);
    }

    /**
     * 获取key列表的长度
     *
     * @param key 键（不能为空）
     * @return 列表的长度
     */
    public Long size(String key) {
        return listOps.size(prefixed(key));
    }

    private <T> T valueOf(Object value, Class<T> clazz) {

        if (value == null) {

            return null;
        }

        if (clazz.isArray() || clazz.isPrimitive()) {
            return (T) value;
        }

        try {
            if (clazz.isEnum()) {
                Method method = clazz.getMethod("valueOf", String.class);

                return clazz.cast(method.invoke("valueOf", value.toString()));
            }
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage(), e);
        }

        return clazz.cast(value);
    }

}

