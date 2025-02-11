package de.flxwdev.inventory.singleton;

import de.flxwdev.VulcanInventory;
import de.flxwdev.inventory.EmptyInventory;
import de.flxwdev.item.VulcanItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class SingletonInventory extends EmptyInventory {
    public SingletonInventory(Player player, Component title) {
        super(player, title);

        this.update();
        this.onOpen();
    }

    @Override
    public void update() {
        var layout = this.layout();
        var items = new HashMap<Character, VulcanItem>();
        this.onUpdate(items);

        for (int rowIndex = 0; rowIndex < layout.size(); rowIndex++) {
            var row = layout.get(rowIndex);
            for (int charIndex = 0; charIndex < 9; charIndex++) {
                VulcanItem item;
                if(!items.containsKey(row.charAt(charIndex))) {
                    if(this.getSettings().placeholder()) {
                        item = VulcanItem.of(VulcanInventory.getSettings().fill()).display(Component.empty());
                    } else {
                        item = VulcanItem.of(Material.AIR);
                    }
                } else {
                    item = items.get(row.charAt(charIndex));
                }

                this.getInventory().setItem(charIndex + (rowIndex * 9), item);
                this.getItemMap().put(charIndex + (rowIndex * 9), item);
            }
        }
    }
}
