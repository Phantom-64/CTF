package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import me.phantom64.capturetheflag.utils.Team;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (CTF.gm.isPlaying(p)) {
            PlayerInventory inv = p.getInventory();
            inv.setHelmet(new ItemStack(Material.AIR, 1));
            inv.setChestplate(new ItemStack(Material.AIR, 1));
            inv.setLeggings(new ItemStack(Material.AIR, 1));
            inv.setBoots(new ItemStack(Material.AIR, 1));
            inv.clear();
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            if (CTF.fm.hasFlag(p)) {
                Team flag = CTF.fm.getFlag(p);
                if (flag == Team.RED) {
                    CTF.fm.getRedFlagSpawn().getBlock().setType(Material.WOOL);
                    CTF.fm.getRedFlagSpawn().getBlock().setData(DyeColor.RED.getData());
                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p) + " §2dropped the §cRed flag§2!");
                } else if (flag == Team.BLUE) {
                    CTF.fm.getBlueFlagSpawn().getBlock().setType(Material.WOOL);
                    CTF.fm.getBlueFlagSpawn().getBlock().setData(DyeColor.BLUE.getData());
                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p) + " §2dropped the §9Blue flag§2!");
                }
            }
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            e.setQuitMessage("");
            CTF.gm.broadcastMessageInGame(CTF.TAG_RED + "§r" + CTF.tm.getPlayerNameInTeamColor(p) + " §cleft the game. ");
            CTF.gm.removePlayerFromGame(p, CTF.tm.getTeam(p));
        }
    }

}
