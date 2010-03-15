package com.fieldexpert.fbapi4j;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
@interface EntityConfig {
	String element();

	String list();

	String single();

	String id();

	String name() default "";
}
