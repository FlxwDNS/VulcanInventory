package de.flxwdev.test;

import de.flxwdev.inventory.InventorySettings;
import de.flxwdev.inventory.singleton.SingletonInventory;
import de.flxwdev.item.VulcanItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@InventorySettings(update = 20, placeholder = false)
public final class TestSingleton extends SingletonInventory {
    public TestSingleton(Player player) {
        super(player, Component.text("Test"));
    }

    @Override
    public List<String> layout() {
        return List.of(
                ".........",
                "....S....",
                "........."
        );
    }

    private final long openSince = System.currentTimeMillis();

    @Override
    public void onUpdate(Map<Character, VulcanItem> items) {
        System.out.println("Updating inventory");
        items.put('S', VulcanItem.skull(Bukkit.getPlayer("Swerion").getUniqueId()).display(Component.text("Stone " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - openSince))).onClick(() -> {
            this.getPlayer().closeInventory();
        }));
    }
}
