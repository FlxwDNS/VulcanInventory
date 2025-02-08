package de.flxwdev;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VulcanSettings {
    Material fill() default Material.GRAY_STAINED_GLASS_PANE;
}
