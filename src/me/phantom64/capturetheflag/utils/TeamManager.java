package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManager {

    private CTF plugin;

    public TeamManager(CTF plugin) {
        this.plugin = plugin;
    }

    public static Map<Player, Team> teams = new HashMap<Player, Team>();

    public static List<Player> red = new ArrayList<Player>();
    public static List<Player> blue = new ArrayList<Player>();

    public void setTeam(Player p, Team team) {
        teams.put(p, team);
        if (team==Team.RED) {
            red.add(p);
            CTF.sm.getScoreBoard().getTeam("red").addPlayer(p);
        }
        else if (team==Team.BLUE) {
            blue.add(p);
            CTF.sm.getScoreBoard().getTeam("blue").addPlayer(p);
        }
    }

    public void removePlayerFromTeam(Player p, Team team) {
        teams.remove(p);
        if (team==Team.RED) {
            red.remove(p);
            CTF.sm.getScoreBoard().getTeam("red").removePlayer(p);
        }
        else if (team==Team.BLUE) {
            blue.remove(p);
            CTF.sm.getScoreBoard().getTeam("blue").removePlayer(p);
        }
    }

    public Team getTeam(Player p) {
        if (teams.containsKey(p)) {
            return teams.get(p);
        }
        return null;
    }

    public Team getValidTeam() {
        if (red.size()>blue.size()) {
            return Team.BLUE;
        } else if (red.size()<blue.size()) {
            return Team.RED;
        }
        return Team.RED;
    }

    public String getPlayerNameInTeamColor(Player p) {
        if (CTF.tm.getTeam(p)==Team.RED) return "§c" + p.getName();
        else if (CTF.tm.getTeam(p)==Team.BLUE) return "§9" + p.getName();
        return "§8" + p.getName();
    }

    public void givePlayerKit(Player p, Team team) {
        PlayerInventory inv = p.getInventory();
        inv.clear();
        inv.setHelmet(new ItemStack(Material.AIR, 1));
        inv.setChestplate(new ItemStack(Material.AIR, 1));
        inv.setLeggings(new ItemStack(Material.AIR, 1));
        inv.setBoots(new ItemStack(Material.AIR, 1));

        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));
        inv.addItem(new ItemStack(Material.IRON_SWORD, 1));
        inv.addItem(new ItemStack(Material.FISHING_ROD, 1));
        inv.addItem(new ItemStack(Material.BOW, 1));
        inv.addItem(new ItemStack(Material.ARROW, 32));
        if (team==Team.RED) {
            inv.setHelmet(new ItemStack(Material.REDSTONE_BLOCK, 1));
            p.sendMessage(CTF.TAG_GREEN + "You received kit §cRed§a.");
        }
        else if (team==Team.BLUE) {
            inv.setHelmet(new ItemStack(Material.LAPIS_BLOCK, 1));
            p.sendMessage(CTF.TAG_GREEN + "You received kit §9Blue§a.");
        }
    }

}
