package dev.test.plugin;

import de.flxwdev.inventory.InventorySettings;
import de.flxwdev.inventory.singleton.SingletonInventory;
import de.flxwdev.item.VulcanItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@InventorySettings(placeholder = false)
public final class TestSingleton extends SingletonInventory {
    public TestSingleton(Player player) {
        super(player, Component.text("Test"));

        this.addUpdateCondition((__) -> {
            if(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - openSince) == 20) {
                return false;
            }
            return true;
        });
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
        items.put('S', VulcanItem.skull(this.getPlayer().getUniqueId()).display(Component.text("Value: " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - openSince))).onClick(() -> {
            this.getPlayer().closeInventory();
        }));
    }
}
