package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import me.phantom64.capturetheflag.utils.Team;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;

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
                        + " §2was killed by §r" + CTF.tm.getPlayerNameInTeamColor(killer) + "§2!");
                p.setGameMode(GameMode.ADVENTURE);
            }
        } else {
            if (CTF.gm.isPlaying(p)) {
                p.setHealth(20.0);
                CTF.lh.teleportPlayerToTeamSpawn(p, CTF.tm.getTeam(p));
                e.setDeathMessage("");
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r " + CTF.tm.getPlayerNameInTeamColor(p)
                        + " §2died!");
                p.setGameMode(GameMode.ADVENTURE);
            }
        }
        if (CTF.fm.hasFlag(p)) {
            Team flag = CTF.fm.getFlag(p);
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            if (flag==Team.RED) {
                CTF.fm.getRedFlagSpawn().getBlock().setType(Material.WOOL);
                CTF.fm.getRedFlagSpawn().getBlock().setData(DyeColor.RED.getData());
                CTF.fm.removeFlag(p);
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p) + " §2dropped the §cRed flag§9!");
            } else if (flag==Team.BLUE) {
                CTF.fm.getBlueFlagSpawn().getBlock().setType(Material.WOOL);
                CTF.fm.getBlueFlagSpawn().getBlock().setData(DyeColor.BLUE.getData());
                CTF.fm.removeFlag(p);
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p) + " §2dropped the Blue flag!");
            }
        }
    }

}
