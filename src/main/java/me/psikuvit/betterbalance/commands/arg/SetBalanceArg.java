package me.psikuvit.betterbalance.commands.arg;

import me.psikuvit.betterbalance.BetterBalance;
import me.psikuvit.betterbalance.commands.CommandAbstract;
import me.psikuvit.betterbalance.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class SetBalanceArg extends CommandAbstract {
    public SetBalanceArg(BetterBalance plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        if (!sender.hasPermission("op")) {
            sender.sendMessage(Messages.color("&cNo permission"));
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Messages.color("&cPlayer not found!"));
            return;
        }
        UUID uuid = target.getUniqueId();
        int newBal = Integer.parseInt(args[1]);
        plugin.getMySQL().replace(uuid, newBal);
        sender.sendMessage(Messages.color("&b" + target.getName() + "'s balance set to " + newBal));
    }

    @Override
    public String correctArg() {
        return "/bb setbalance <target> <amount>";
    }

    @Override
    public boolean onlyPlayer() {
        return false;
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
