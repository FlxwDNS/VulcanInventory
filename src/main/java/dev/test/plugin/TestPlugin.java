package dev.test.plugin;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import de.flxwdev.VulcanInventory;
import de.flxwdev.VulcanSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@VulcanSettings
public class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        VulcanInventory.initialize(this);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(PlayerJumpEvent event) {
        new TestSingleton(event.getPlayer());
    }
}
