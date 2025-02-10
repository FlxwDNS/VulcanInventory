package de.flxwdev.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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

    public static @NotNull VulcanItem skull(@NotNull String base64) {
        var item = new VulcanItem(Material.PLAYER_HEAD);
        item.editMeta(it -> {
            var meta = (SkullMeta) it;
            meta.setPlayerProfile(createProfile(base64));
        });
        return item;
    }

    public static @NotNull VulcanItem skull(@NotNull UUID uniqueId) {
        var item = new VulcanItem(Material.PLAYER_HEAD);
        item.editMeta(it -> {
            var meta = (SkullMeta) it;
            meta.setPlayerProfile(Bukkit.createProfile(uniqueId));
        });
        return item;
    }

    public static @NotNull VulcanItem of(@NotNull Material material) {
        return new VulcanItem(material);
    }


    private static PlayerProfile createProfile(String base64) {
        var profile = Bukkit.createProfile(UUID.randomUUID());
        var textures = profile.getTextures();
        try {
            var url = new String(Base64.getDecoder().decode(base64));
            var skinUrl = url.split("\"url\":\"")[1].split("\"")[0];
            textures.setSkin(new URL(skinUrl));
            profile.setTextures(textures);
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
        return profile;
    }
}
