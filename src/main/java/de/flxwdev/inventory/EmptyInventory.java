package de.flxwdev.inventory;

import de.flxwdev.VulcanInventory;
import de.flxwdev.item.VulcanItem;
import dev.test.plugin.TestPlugin;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Getter
public abstract class EmptyInventory {
    private final Integer inventoryId = ThreadLocalRandom.current().nextInt(100000000, 999999999);

    @NotNull
    private final InventorySettings settings;

    private final Player player;
    private final Inventory inventory;

    private final List<Integer> scheduleIds = new ArrayList<>();
    private final Map<Integer, VulcanItem> itemMap = new HashMap<>();

    private final Map<Integer, Function<Void, Object>> updateFunctions = new HashMap<>();

    public EmptyInventory(Player player, Component title) {
        this.settings = this.getClass().getAnnotation(InventorySettings.class);
        this.player = player;

        // Create the inventory with the layout and title
        var layout = this.layout();
        var titleBuilder = Component.text();
        for (int i = 0; i < inventoryId.toString().length(); i++) {
            titleBuilder.append(Component.text("§" + inventoryId.toString().charAt(i)));
        }
        titleBuilder.append(title);

        this.inventory = Bukkit.createInventory(null, layout.size() * 9, titleBuilder.build());
        VulcanInventory.addInventory(this);

        // Update the inventory every X ticks
        if(this.settings.update() >= 0) {
            this.scheduleIds.add(Bukkit.getScheduler().runTaskTimerAsynchronously(VulcanInventory.getPlugin(), this::update, 0, this.settings.update()).getTaskId());
        } else {
            Map<Integer, Object> tempValues = new HashMap<>();
            this.scheduleIds.add(Bukkit.getScheduler().runTaskTimerAsynchronously(VulcanInventory.getPlugin(), () -> {
                for (int i = 0; i < this.updateFunctions.size(); i++) {
                    var value = this.updateFunctions.get(i).apply(null);
                    if(value != null) {
                        if(tempValues.size() > i) {
                            if(tempValues.get(i) != value) {
                                this.update();
                            }
                        }
                        tempValues.put(i, value);
                    }
                }
            }, 0, 5).getTaskId());
        }
    }

    public void addUpdateCondition(Function<Void, Object> function) {
        this.updateFunctions.put(this.updateFunctions.size(), function);
    }

    public void destroy() {
        // Cancel all scheduled tasks and remove the inventory from the list
        this.scheduleIds.forEach(Bukkit.getScheduler()::cancelTask);
        // Remove the inventory from the list
        VulcanInventory.getInventories().removeIf(it -> it.getInventoryId().equals(this.inventoryId));
        // Call the onDestroy method
        this.onDestroy();
    }

    public void update() {
        // Used by inventories
    }

    public void onOpen() {
        player.openInventory(this.inventory);
    }

    public abstract List<String> layout();
    public abstract void onUpdate(Map<Character, VulcanItem> items);

    public void onDestroy() {
        // Override this method if you want to do something when the inventory is destroyed
    }
}
