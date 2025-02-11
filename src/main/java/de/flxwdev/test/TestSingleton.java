package de.flxwdev.test;

import de.flxwdev.inventory.InventorySettings;
import de.flxwdev.inventory.singleton.SingletonInventory;
import de.flxwdev.item.VulcanItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        items.put('S', VulcanItem.skull(UUID.fromString("a6342afc-9e31-45cd-8098-14543afe3239")).display(Component.text("Stone " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - openSince))).onClick(() -> {
            this.getPlayer().closeInventory();
        }));
    }
}
