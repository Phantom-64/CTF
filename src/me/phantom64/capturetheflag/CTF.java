package me.phantom64.capturetheflag;

import me.phantom64.capturetheflag.commands.CommandJoin;
import me.phantom64.capturetheflag.commands.CommandSetSpawn;
import me.phantom64.capturetheflag.listeners.*;
import me.phantom64.capturetheflag.utils.GameManager;
import me.phantom64.capturetheflag.utils.LocationHandler;
import me.phantom64.capturetheflag.utils.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CTF extends JavaPlugin {

    public static String TAG_GREEN = "§5§l[§3§lCTF§5§l] §a";
    public static String TAG_BLUE = "§5§l[§3§lCTF§5§l] §9";
    public static String TAG_RED = "§5§l[§3§lCTF§5§l] §c";

    public static GameManager gm;
    public static LocationHandler lh;
    public static TeamManager tm;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        gm = new GameManager(this);
        lh = new LocationHandler(this);
        tm = new TeamManager(this);
        registerListeners(new AsyncPlayerChat(), new BlockListener(), new FoodLevelChange(), new InventoryClick(), new PlayerDropItem());
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
            getLogger().info("Event " + listener.getClass().getSimpleName() + " registered.");
        }
        getLogger().info("All events registered.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ctf")) {

            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length==0) {
                    p.sendMessage(CTF.TAG_BLUE + "CTF plugin made by Phantom_64!");
                } else if (args[0].equalsIgnoreCase("join")) {
                    CommandJoin.execute(p, args);
                } else if (args[0].equalsIgnoreCase("setspawn")) {
                    CommandSetSpawn.execute(p, args);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            }

        }

        return true;
    }

}
