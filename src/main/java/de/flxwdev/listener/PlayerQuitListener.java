package de.flxwdev.listener;

import de.flxwdev.VulcanInventory;
import de.flxwdev.inventory.EmptyInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        VulcanInventory.getInventories().stream()
                .filter(it -> event.getPlayer().equals(it.getPlayer()))
                .findFirst().ifPresent(EmptyInventory::destroy);

    }
}
