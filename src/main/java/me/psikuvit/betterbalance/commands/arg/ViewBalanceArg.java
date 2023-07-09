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

public class ViewBalanceArg extends CommandAbstract {

    public ViewBalanceArg(BetterBalance plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Messages.color("&cPlayer not found!"));
            return;
        }
        UUID uuid = target.getUniqueId();
        int balance = plugin.getMySQL().getPlayerBalance(uuid);
        sender.sendMessage(Messages.color("&b" + target.getName() + " has a balance of " + balance));

    }

    @Override
    public String correctArg() {
        return "/bb bal <target>";
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }

    @Override
    public int requiredArg() {
        return 1;
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
