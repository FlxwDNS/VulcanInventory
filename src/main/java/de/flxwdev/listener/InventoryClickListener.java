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

        VulcanInventory.getInventories()
                .stream()
                .filter(it -> {
                    var result = event.getClickedInventory().equals(it.getInventory());
                    if(result) {
                        event.setCancelled(true);
                    }
                    return result;
                }).findFirst().ifPresent(inventory -> {
                    var item = inventory.getItemMap().get(event.getSlot());
                    if(!item.getOnClick().containsKey(event.getClick())) {
                        return;
                    }
                    item.getOnClick().get(event.getClick()).run();
                });
    }
}
