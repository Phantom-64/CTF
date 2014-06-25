package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();

        if (e.getLine(0).equalsIgnoreCase("[ctf]")) {
            if (!p.isOp()) {
                e.setLine(0, "§4[CTF]");
                p.sendMessage(CTF.TAG_RED + "You don't have permission to create a sign!");
            } else {
                if (e.getLine(1).equalsIgnoreCase("join")) {
                    e.setLine(0, "§5[§3CTF§5]");
                    e.setLine(1, "§d§nJoin");
                    e.setLine(2, "§2Click here!");
                    p.sendMessage(CTF.TAG_GREEN + "Join sign created!");
                } else if (e.getLine(1).equalsIgnoreCase("leave")) {
                    e.setLine(0, "§5[§3CTF§5]");
                    e.setLine(1, "§2§nLeave");
                    e.setLine(2, "§dClick here!");
                    p.sendMessage(CTF.TAG_GREEN + "Leave sign created!");
                } else {
                    e.setLine(0, "§4[CTF]");
                    p.sendMessage(CTF.TAG_RED + "Invalid sign name. Available sign names: join, leave");
                }
            }
        }

    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction()==Action.RIGHT_CLICK_BLOCK||e.getAction()==Action.LEFT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType()== Material.SIGN_POST||b.getType()==Material.WALL_SIGN) {
                Sign s = (Sign) b.getState();
                if (s.getLine(0).equalsIgnoreCase("§5[§3CTF§5]")&&s.getLine(1).equalsIgnoreCase("§d§nJoin")) {
                    p.performCommand("ctf join");
                } else if (s.getLine(0).equalsIgnoreCase("§5[§3CTF§5]")&&s.getLine(1).equalsIgnoreCase("§2§nLeave")) {
                    p.performCommand("ctf leave");
                }
            }
        }
    }

}
