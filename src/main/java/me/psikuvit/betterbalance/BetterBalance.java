package me.psikuvit.betterbalance;

import me.psikuvit.betterbalance.commands.CommandRegister;
import me.psikuvit.betterbalance.data.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BetterBalance extends JavaPlugin {

    private static BetterBalance plugin;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        mySQL = new MySQL();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        Objects.requireNonNull(getCommand("bb")).setExecutor(new CommandRegister(this));
        Objects.requireNonNull(getCommand("bb")).setTabCompleter(new CommandRegister(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        mySQL.disconnectFromDatabase();
    }

    public static BetterBalance getPlugin() {
        return plugin;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}
