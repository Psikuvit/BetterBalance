package me.psikuvit.betterbalance.commands;

import me.psikuvit.betterbalance.BetterBalance;
import org.bukkit.command.CommandSender;

import java.util.List;
public abstract class CommandAbstract {

    protected BetterBalance plugin;
    public CommandAbstract(final BetterBalance plugin) {
        this.plugin = plugin;
    }

    public abstract void executeCommand(final String[] args, final CommandSender sender);

    public abstract String correctArg();

    public abstract boolean onlyPlayer();

    public abstract int requiredArg();

    public abstract int bypassArgLimit();

    public abstract List<String> tabComplete(final String[] args);
}