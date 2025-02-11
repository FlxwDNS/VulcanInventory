package de.flxwdev.listener;

import de.flxwdev.VulcanInventory;
import de.flxwdev.inventory.EmptyInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public final class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        VulcanInventory.getInventories()
                .stream()
                .filter(it -> event.getInventory().equals(it.getInventory()))
                .findFirst()
                .ifPresent(EmptyInventory::destroy);
    }
}
