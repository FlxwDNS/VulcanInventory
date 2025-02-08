package de.flxwdev.item;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class VulcanItem extends ItemStack {
    private final Map<ClickType, Runnable> onClick = new HashMap<>();

    public VulcanItem(Material material) {
        super(material);
    }

    public VulcanItem onClick(Runnable runnable) {
        Arrays.stream(ClickType.values()).forEach(it -> this.onClick.put(it, runnable));
        return this;
    }

    public VulcanItem onClick(ClickType clickType, Runnable runnable) {
        this.onClick.put(clickType, runnable);
        return this;
    }

    public VulcanItem display(Component text) {
        var meta = this.getItemMeta();
        meta.customName(text.decoration(TextDecoration.ITALIC, false));
        this.setItemMeta(meta);

        return this;
    }

    public VulcanItem amount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public static @NotNull VulcanItem of(@NotNull Material material) {
        return new VulcanItem(material);
    }
}
