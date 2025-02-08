package de.flxwdev.listener;

import de.flxwdev.VulcanInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var inventory = VulcanInventory.inventories().stream()
                .filter(it -> event.getPlayer().equals(it.player()))
                .findFirst()
                .orElse(null);

        if(inventory == null) {
            return;
        }

        inventory.onClose();
        VulcanInventory.inventories().remove(inventory);
    }
}
