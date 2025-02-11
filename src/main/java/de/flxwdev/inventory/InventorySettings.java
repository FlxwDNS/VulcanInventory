package de.flxwdev.inventory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InventorySettings {
    int update() default -1;
    boolean placeholder() default true;
}
