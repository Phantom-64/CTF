package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHandler {

    private CTF plugin;

    public LocationHandler(CTF plugin) {
        this.plugin = plugin;
    }

    private Location redSpawn;
    private Location blueSpawn;
    private Location exitSpawn;

    public Location getRedSpawn() {
        return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.red.world")),
                plugin.getConfig().getInt("Spawns.red.x"),
                plugin.getConfig().getInt("Spawns.red.y"),
                plugin.getConfig().getInt("Spawns.red.z"));
    }

    public void setRedSpawn(Location redSpawn) {
        plugin.getConfig().set("Spawns.red.world", redSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.red.x", redSpawn.getX());
        plugin.getConfig().set("Spawns.red.y", redSpawn.getY());
        plugin.getConfig().set("Spawns.red.z", redSpawn.getZ());
    }

    public Location getBlueSpawn() {
        return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.blue.world")),
                plugin.getConfig().getInt("Spawns.blue.x"),
                plugin.getConfig().getInt("Spawns.blue.y"),
                plugin.getConfig().getInt("Spawns.blue.z"));
    }

    public void setBlueSpawn(Location blueSpawn) {
        plugin.getConfig().set("Spawns.blue.world", blueSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.blue.x", blueSpawn.getX());
        plugin.getConfig().set("Spawns.blue.y", blueSpawn.getY());
        plugin.getConfig().set("Spawns.blue.z", blueSpawn.getZ());
    }

    public Location getExitSpawn() {
        return new Location(Bukkit.getWorld(plugin.getConfig().getString("Spawns.exit.world")),
                plugin.getConfig().getInt("Spawns.exit.x"),
                plugin.getConfig().getInt("Spawns.exit.y"),
                plugin.getConfig().getInt("Spawns.exit.z"));
    }

    public void setExitSpawn(Location exitSpawn) {
        plugin.getConfig().set("Spawns.exit.world", exitSpawn.getWorld().getName());
        plugin.getConfig().set("Spawns.exit.x", exitSpawn.getX());
        plugin.getConfig().set("Spawns.exit.y", exitSpawn.getY());
        plugin.getConfig().set("Spawns.exit.z", exitSpawn.getZ());
    }

    public void teleportPlayerToArena(Player p, Team team) {
        if (team==Team.RED) p.teleport(CTF.lh.getRedSpawn());
        else if (team==Team.BLUE) p.teleport(CTF.lh.getBlueSpawn());
    }

    public void teleportPlayerFromArena(Player p) {
        p.teleport(CTF.lh.getExitSpawn());
    }

}
