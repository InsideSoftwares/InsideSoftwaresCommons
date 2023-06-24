package br.com.insidesoftwares.commons.configuration.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsideCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(final Object target, final Method method,
                           final Object... params) {

        final List<Object> keys = new ArrayList<>();
        keys.add(method.getDeclaringClass().getSimpleName());
        keys.add(method.getName());
        keys.addAll(Arrays.asList(params));

        return keys;
    }
}