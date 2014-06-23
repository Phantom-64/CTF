package me.phantom64.capturetheflag.commands;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandLeave {

    public static void execute(Player p, String[] a) {

        if (a.length!=1) {
            p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf leave");
        } else {
            if (CTF.gm.isPlaying(p)) {
                PlayerInventory inv = p.getInventory();
                inv.setHelmet(new ItemStack(Material.AIR, 1));
                inv.setChestplate(new ItemStack(Material.AIR, 1));
                inv.setLeggings(new ItemStack(Material.AIR, 1));
                inv.setBoots(new ItemStack(Material.AIR, 1));
                inv.clear();
                CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r" + CTF.tm.getPlayerNameInTeamColor(p) + " §9left the game. ");
                CTF.gm.removePlayerFromGame(p, CTF.tm.getTeam(p));
            } else {
                p.sendMessage(CTF.TAG_BLUE + "You are not in the game!");
            }
        }

    }

}
