package de.flxwdev.listener;

import de.flxwdev.VulcanInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public final class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        VulcanInventory.inventories()
                .stream()
                .filter(it -> event.getInventory().equals(it.inventory()))
                .findFirst()
                .ifPresent(inventory -> {
                    inventory.onClose();
                    VulcanInventory.inventories().remove(inventory);
                });
    }
}
