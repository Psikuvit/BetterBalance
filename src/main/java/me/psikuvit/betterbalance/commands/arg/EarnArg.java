package me.psikuvit.betterbalance.commands.arg;

import me.psikuvit.betterbalance.BetterBalance;
import me.psikuvit.betterbalance.commands.CommandAbstract;
import me.psikuvit.betterbalance.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class EarnArg extends CommandAbstract {

    private final Set<UUID> playersOnCooldown = new HashSet<>();
    public EarnArg(BetterBalance plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        if (playersOnCooldown.contains(playerUUID)) {
            player.sendMessage(Messages.color("&cOn cooldown!"));
        } else {
            int randomInt = new Random().nextInt(6);
            plugin.getMySQL().updatePlayerBalance(playerUUID, randomInt);
            player.sendMessage(Messages.color("&bYou received &f" + randomInt));
            playersOnCooldown.add(playerUUID);

            new BukkitRunnable() {
                @Override
                public void run() {
                    playersOnCooldown.remove(playerUUID);
                }
            }.runTaskLater(plugin, 60 * 20);
        }


    }

    @Override
    public String correctArg() {
        return "/bb earn";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 0;
    }

    @Override
    public int bypassArgLimit() {
        return 0;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
