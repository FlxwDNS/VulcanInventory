package de.flxwdev.listener;

import de.flxwdev.VulcanInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player) || event.getClickedInventory() == null) {
            return;
        }

        VulcanInventory.inventories()
                .stream()
                .filter(it -> {
                    var result = event.getClickedInventory().equals(it.inventory());
                    if(result) {
                        event.setCancelled(true);
                    }
                    return result;
                }).findFirst().ifPresent(inventory -> {
                    var item = inventory.itemMap().get(event.getSlot());
                    if(!item.onClick().containsKey(event.getClick())) {
                        return;
                    }
                    item.onClick().get(event.getClick()).run();
                });
    }
}
