package me.phantom64.capturetheflag.commands;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.entity.Player;

public class CommandSetFlagSpawn {

    public static void execute(Player p, String[] a) {

        if (!p.isOp()) {
            p.sendMessage(CTF.TAG_RED + "You don't have permission to do this!");
            return;
        }

        if (a.length!=2) {
            p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf setflagspawn <red/blue>");
        } else {
            if (a[1].equalsIgnoreCase("red")) {
                CTF.fm.setRedFlagSpawn(p.getLocation());
                p.sendMessage(CTF.TAG_GREEN + "§cRed flag §aspawn set!");
            } else if (a[1].equalsIgnoreCase("blue")) {
                CTF.fm.setBlueFlagSpawn(p.getLocation());
                p.sendMessage(CTF.TAG_GREEN + "§9Blue flag §aspawn set!");
            } else {
                p.sendMessage(CTF.TAG_BLUE + "Invalid flag spawn type. Available flag spawn types: red, blue");
            }
        }

    }

}
