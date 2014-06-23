package me.phantom64.capturetheflag.commands;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.entity.Player;

public class CommandSetSpawn {

    public static void execute(Player p, String[] a) {

        if (!p.isOp()) {
            p.sendMessage(CTF.TAG_RED + "You don't have the permission to do this!");
            return;
        }

        if (a.length!=2) {
            p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf setspawn <red/blue/exit>");
        } else {
            if (a[1].equalsIgnoreCase("red")) {
                CTF.lh.setRedSpawn(p.getLocation());
                p.sendMessage(CTF.TAG_GREEN + "§cRed §aspawn set!");
            } else if (a[1].equalsIgnoreCase("blue")) {
                CTF.lh.setBlueSpawn(p.getLocation());
                p.sendMessage(CTF.TAG_GREEN + "§9Blue §aspawn set!");
            } else if (a[1].equalsIgnoreCase("exit")) {
                CTF.lh.setExitSpawn(p.getLocation());
                p.sendMessage(CTF.TAG_GREEN + "Exit spawn set!");
            } else {
                p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf setspawn <red/blue/exit>");
            }
        }

    }

}
