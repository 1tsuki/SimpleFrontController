package com.astrider.sfc.lib.helper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Column {
    String value();
}
