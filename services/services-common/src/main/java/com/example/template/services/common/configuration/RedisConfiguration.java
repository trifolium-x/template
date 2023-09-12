package com.example.template.services.common.configuration;

import cn.hutool.system.SystemUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.redisson.Redisson;
import org.redisson.api.NameMapper;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/4/18 13:35
 * @modified :
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

    @Inject
    private RedisProperties redisProperties;

    @Inject
    private AppConfig appConfig;

    @Value("${spring.jackson.time-zone:'UTC+8'}")
    private String jsonTimeZone;

    @Value("${spring.jackson.date-format:'yyyy-MM-dd HH:mm:ss'}")
    private String dateFormat;


    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(createGenericObjectMapper()));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(createGenericObjectMapper()));

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    /**
     * 配置redisson
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setConnectionMinimumIdleSize(Runtime.getRuntime().availableProcessors());
        config.setThreads(Runtime.getRuntime().availableProcessors());
        config.setNettyThreads(Runtime.getRuntime().availableProcessors() + 1);
        config.setCodec(new JsonJacksonCodec());

        String pact = "redis";
        String redisUrl = String.format("%s://%s:%s", pact, redisProperties.getHost(), redisProperties.getPort());
        // 其他集群config.useReplicatedServers()...x

        TransportMode transportMode = TransportMode.NIO;
        if (SystemUtil.getOsInfo().isLinux()) {
            transportMode = TransportMode.EPOLL;
        } else if (SystemUtil.getOsInfo().isMacOsX()) {
            transportMode = TransportMode.KQUEUE;
        }
        config.setTransportMode(transportMode)
                .useSingleServer()
                .setAddress(redisUrl)
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase())
                .setNameMapper(new NameMapper() {
                    @Override
                    public String map(String name) {
                        return appConfig.getCacheKeyPrefix() + name;
                    }

                    @Override
                    public String unmap(String name) {
                        return name.substring(appConfig.getCacheKeyPrefix().length());
                    }
                });

        config.setLockWatchdogTimeout(60 * 100);

        return Redisson.create(config);
    }

    private ObjectMapper createGenericObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setTimeZone(TimeZone.getTimeZone(jsonTimeZone));

        objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        objectMapper.setDefaultLeniency(Boolean.FALSE);

        objectMapper.registerModule(new SimpleModule().addSerializer(new StdSerializer<>(NullValue.class) {
            private String classIdentifier;

            @Override
            public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider)
                    throws IOException {
                classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
                jgen.writeStartObject();
                jgen.writeStringField(classIdentifier, NullValue.class.getName());
                jgen.writeEndObject();
            }
        }));


        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL);

        return objectMapper;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(
                createGenericObjectMapper());
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                jackson2JsonRedisSerializer));
        return configuration;
    }

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public GeoOperations<String, Object> geoOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForGeo();
    }

    @Bean
    public HyperLogLogOperations<String, Object> hyperLogLogOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHyperLogLog();
    }

}
