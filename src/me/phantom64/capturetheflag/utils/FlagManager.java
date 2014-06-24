package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FlagManager {

    private CTF plugin;

    public FlagManager(CTF plugin) {
        this.plugin = plugin;
    }

    public static Map<Player, Team> flags = new HashMap<Player, Team>();

    public boolean hasFlag(Player p) {
        return flags.containsKey(p);
    }

    public void setFlag(Player p, Team team) {
        flags.put(p, team);
    }

    public void removeFlag(Player p) {
        flags.remove(p);
    }

    public Team getFlag(Player p) {
        if (flags.containsKey(p)) {
            return flags.get(p);
        }
        return null;
    }
    
    private Location redFlagSpawn;
    private Location blueFlagSpawn;

    public Location getRedFlagSpawn() {
        return new Location(Bukkit.getWorld(plugin.getConfig().getString("FlagSpawns.red.world")),
                plugin.getConfig().getInt("FlagSpawns.red.x"),
                plugin.getConfig().getInt("FlagSpawns.red.y"),
                plugin.getConfig().getInt("FlagSpawns.red.z"));
    }

    public void setRedFlagSpawn(Location redFlagSpawn) {
        this.redFlagSpawn = redFlagSpawn;
        plugin.getConfig().set("FlagSpawns.red.world", redFlagSpawn.getWorld().getName());
        plugin.getConfig().set("FlagSpawns.red.x", redFlagSpawn.getX());
        plugin.getConfig().set("FlagSpawns.red.y", redFlagSpawn.getY());
        plugin.getConfig().set("FlagSpawns.red.z", redFlagSpawn.getZ());
        plugin.saveConfig();
    }

    public Location getBlueFlagSpawn() {
        return new Location(Bukkit.getWorld(plugin.getConfig().getString("FlagSpawns.blue.world")),
                plugin.getConfig().getInt("FlagSpawns.blue.x"),
                plugin.getConfig().getInt("FlagSpawns.blue.y"),
                plugin.getConfig().getInt("FlagSpawns.blue.z"));
    }

    public void setBlueFlagSpawn(Location blueFlagSpawn) {
        this.blueFlagSpawn = blueFlagSpawn;
        plugin.getConfig().set("FlagSpawns.blue.world", blueFlagSpawn.getWorld().getName());
        plugin.getConfig().set("FlagSpawns.blue.x", blueFlagSpawn.getX());
        plugin.getConfig().set("FlagSpawns.blue.y", blueFlagSpawn.getY());
        plugin.getConfig().set("FlagSpawns.blue.z", blueFlagSpawn.getZ());
        plugin.saveConfig();
    }
}
