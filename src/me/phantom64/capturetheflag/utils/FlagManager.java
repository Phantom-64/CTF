package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
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

}
