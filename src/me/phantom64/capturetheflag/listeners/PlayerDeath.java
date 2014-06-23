package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.getKiller() instanceof Player) {
            Player killer = p.getKiller();
            if (CTF.gm.isPlaying(p)) {
                p.setHealth(20.0);
                CTF.lh.teleportPlayerToTeamSpawn(p, CTF.tm.getTeam(p));
                e.setDeathMessage("");
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r " + CTF.tm.getPlayerNameInTeamColor(p)
                        + " §9was killed by §r" + CTF.tm.getPlayerNameInTeamColor(killer) + "§9!");
                p.setGameMode(GameMode.ADVENTURE);
            }
        } else {
            if (CTF.gm.isPlaying(p)) {
                p.setHealth(20.0);
                CTF.lh.teleportPlayerToTeamSpawn(p, CTF.tm.getTeam(p));
                e.setDeathMessage("");
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r " + CTF.tm.getPlayerNameInTeamColor(p)
                        + " §9died!");
                p.setGameMode(GameMode.ADVENTURE);
            }
        }
    }

}
