package de.flxwdev;

import de.flxwdev.inventory.EmptyInventory;
import de.flxwdev.listener.InventoryClickListener;
import de.flxwdev.listener.InventoryCloseListener;
import de.flxwdev.listener.PlayerQuitListener;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class VulcanInventory {
    @Getter
    private JavaPlugin plugin;
    @Getter
    private VulcanSettings settings;

    @Getter
    private final List<EmptyInventory> inventories = new ArrayList<>();

    public void addInventory(EmptyInventory inventory) {
        inventories.add(inventory);
    }

    public void initialize(JavaPlugin javaPlugin) {
        plugin = javaPlugin;

        settings = plugin.getClass().getAnnotation(VulcanSettings.class);
        if(settings == null) {
            throw new RuntimeException("VulcanSettings annotation not found in main class! Disabling....");
        }

        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), plugin);
    }
}
