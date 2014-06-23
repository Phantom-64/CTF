package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static List<Player> playing = new ArrayList<Player>();

    private CTF plugin;

    public GameManager(CTF plugin) {
        this.plugin = plugin;
    }

    public void addPlayerToGame(Player p, Team team) {
        playing.add(p);
        CTF.tm.setTeam(p, team);
        p.setGameMode(GameMode.ADVENTURE);
        CTF.lh.teleportPlayerToArena(p, team);
    }

    public void removePlayerFromGame(Player p, Team team) {
        playing.remove(p);
        CTF.tm.removePlayerFromTeam(p, team);
        p.setGameMode(GameMode.SURVIVAL);
        CTF.lh.teleportPlayerFromArena(p);
    }

    public boolean isPlaying(Player p) {
        return playing.contains(p);
    }

    public void broadcastMessageInGame(String message) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (isPlaying(pl)) {
                pl.sendMessage(message);
            }
        }
    }

}
