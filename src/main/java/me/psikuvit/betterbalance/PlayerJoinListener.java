package me.psikuvit.betterbalance;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final BetterBalance plugin = BetterBalance.getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Check if player already exists in the database
        boolean playerExists = plugin.getMySQL().ifExists(playerId);

        if (!playerExists) {
            plugin.getMySQL().saveIntegerValue(playerId, 0);
        }
    }
}
