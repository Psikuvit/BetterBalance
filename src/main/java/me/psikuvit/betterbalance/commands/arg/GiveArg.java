package me.psikuvit.betterbalance.commands.arg;

import me.psikuvit.betterbalance.BetterBalance;
import me.psikuvit.betterbalance.commands.CommandAbstract;
import me.psikuvit.betterbalance.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GiveArg extends CommandAbstract {
    public GiveArg(BetterBalance plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            player.sendMessage(Messages.color("&cPlayer not found!"));
            return;
        }
        int senderBal = plugin.getMySQL().getPlayerBalance(player.getUniqueId());
        int toGive = Integer.parseInt(args[1]);
        if (senderBal >= toGive) {
            plugin.getMySQL().replace(player.getUniqueId(), senderBal - toGive);
            plugin.getMySQL().updatePlayerBalance(target.getUniqueId(), toGive);
            player.sendMessage(Messages.color("&bYou gave &f" + target.getName() + " " + toGive));
        } else {
            player.sendMessage(Messages.color("&cNot enough money!"));
        }
    }

    @Override
    public String correctArg() {
        return "/bb give <target> <amount>";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 2;
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
