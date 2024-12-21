package com.springmate.framework.annotation;

import java.lang.annotation.*;
import org.springframework.stereotype.Service;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ResourceService {
    String value() default "";  // Resource type name
}