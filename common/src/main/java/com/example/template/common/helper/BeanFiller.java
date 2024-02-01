package com.example.template.common.helper;

import com.example.template.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @title: 对象拷贝
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
@Slf4j
public class BeanFiller<T> {

    private final Class<T> targetClass;

    private BeanFiller(Class<T> targetClass) {

        this.targetClass = targetClass;
    }

    public static <T> BeanFiller<T> target(Class<T> targetClass) {
        return new BeanFiller<>(targetClass);
    }

    private T createTarget() {

        try {
            return targetClass.getDeclaredConstructor().newInstance();

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 拷贝属性到一个对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void acceptObject(Object source, Object target) {
        try {
            PropertyUtils.copyProperties(target, source);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("bean filter error", e);
        }
    }

    public <S> T accept(S source) {

        return accept(source, Collections.singletonList(defaultCopy()));
    }

    public <S> T accept(S source, BiConsumer<T, S> consumer) {

        return accept(source, Collections.singletonList(consumer));
    }

    public <S> T acceptDefault(S source, BiConsumer<T, S> consumer) {

        return accept(source, Arrays.asList(defaultCopy(), consumer));
    }

    public <S> T accept(S source, List<BiConsumer<T, S>> consumers) {

        T target = createTarget();
        process(target, source, consumers);

        return target;
    }

    public <S> List<T> acceptList(Collection<S> sources) {

        return acceptList(sources, Collections.singletonList(defaultCopy()));
    }

    public <S> List<T> acceptList(Collection<S> sources, BiConsumer<T, S> consumer) {

        return acceptList(sources, Collections.singletonList(consumer));
    }

    public <S> List<T> acceptListDefault(Collection<S> sources, BiConsumer<T, S> consumer) {

        return acceptList(sources, Arrays.asList(defaultCopy(), consumer));
    }

    public <S> List<T> acceptList(Collection<S> sources, List<BiConsumer<T, S>> consumers) {

        List<T> targets = new ArrayList<>(sources.size());
        Iterator<S> it = sources.iterator();

        for (int i = 0; i < sources.size(); i++) {

            T target = createTarget();
            targets.add(target);

            process(target, it.next(), consumers);
        }

        return targets;
    }


    public static <T, S> List<BiConsumer<T, S>> afterDefault(BiConsumer<T, S> consumer) {

        return afterDefault(Collections.singletonList(consumer));
    }

    public static <T, S> List<BiConsumer<T, S>> afterDefault(List<BiConsumer<T, S>> afterConsumers) {

        List<BiConsumer<T, S>> consumers = new ArrayList<>();
        consumers.add(defaultConsumer());
        consumers.addAll(afterConsumers);

        return consumers;
    }

    private static <T, S> BiConsumer<T, S> defaultConsumer() {
        return (target, source) -> {
            try {
                PropertyUtils.copyProperties(target, source);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("bean filter error", e);
            }
        };
    }

    private <S> void process(T target, S source, List<BiConsumer<T, S>> consumers) {

        for (BiConsumer<T, S> consumer : consumers) {
            consumer.accept(target, source);
        }
    }

    /**
     * 基础属性拷贝方法，先使用apache BeanUtils进行拷贝，如果失败则使用json的方式进行拷贝
     */
    private <T, S> BiConsumer<T, S> defaultCopy() {

        return (target, source) -> {
            try {
                PropertyUtils.copyProperties(target, source);
            } catch (Exception e) {
                // 如果转换失败，则尝试使用json的方式进行转化，效率比较慢。
                try {
                    PropertyUtils.copyProperties(target, JsonUtil.fromJson(JsonUtil.toJson(source), targetClass));
                } catch (Exception jsonE) {
                    log.warn("{}", e.getMessage());
                    throw new RuntimeException("bean filter error " + jsonE.getMessage(), jsonE);
                }
            }
        };
    }

}
