package de.flxwdev.inventory.singleton;

import de.flxwdev.VulcanInventory;
import de.flxwdev.item.VulcanItem;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public abstract class SingletonInventory {
    private final Integer inventoryId = ThreadLocalRandom.current().nextInt(100000000, 999999999);

    public abstract List<String> layout();
    public abstract Map<Character, VulcanItem> items();
    public abstract void onClose();

    private final Map<Integer, VulcanItem> itemMap;

    private final Player player;
    private final Inventory inventory;

    public SingletonInventory(Player player, Component title) {
        this.itemMap = new HashMap<>();
        this.player = player;

        var layout = this.layout();
        var titleBuilder = Component.text();
        for (int i = 0; i < inventoryId.toString().length(); i++) {
            titleBuilder.append(Component.text("§" + inventoryId.toString().charAt(i)));
        }
        titleBuilder.append(title);

        this.inventory = Bukkit.createInventory(null, layout.size() * 9, titleBuilder.build());
        this.update();

        VulcanInventory.addInventory(this);

        this.onOpen();
    }

    public void update() {
        var layout = this.layout();
        var items = this.items();

        for (int rowIndex = 0; rowIndex < layout.size(); rowIndex++) {
            var row = layout.get(rowIndex);
            for (int charIndex = 0; charIndex < 9; charIndex++) {
                VulcanItem item;
                if(!items.containsKey(row.charAt(charIndex))) {
                    item = VulcanItem.of(VulcanInventory.settings().fill()).display(Component.empty());
                } else {
                    item = items.get(row.charAt(charIndex));
                }

                this.inventory.setItem(charIndex + (rowIndex * 9), item);
                this.itemMap.put(charIndex + (rowIndex * 9), item);
            }
        }
    }

    private void onOpen() {
        player.openInventory(this.inventory);
    }
}
