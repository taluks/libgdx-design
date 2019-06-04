package com.libgdxdesign.component.loader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {

	public ReflectionUtils() {
	}

	public static Class<?> getGenericParameterClass(Class<?> actualClass, int parameterIndex) {
        Type typeArgument = ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
        if(typeArgument instanceof Class) 
            return (Class<?>) typeArgument;
        if(typeArgument instanceof ParameterizedType)
            return (Class<?>) ((ParameterizedType) typeArgument).getRawType();
        throw new IllegalArgumentException("Unexpected type argument: " + typeArgument.getClass());
    }
}
