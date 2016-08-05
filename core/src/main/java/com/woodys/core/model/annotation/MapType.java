package com.woodys.core.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

/**
 * @author: woodys
 * @date: 2016-05-31 14:15

 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapType {
    Class map() default HashMap.class;
    Class key() default String.class;
    Class value();
}
