package me.phantom64.capturetheflag.commands;

import me.phantom64.capturetheflag.CTF;
import me.phantom64.capturetheflag.utils.Team;
import org.bukkit.entity.Player;

public class CommandJoin {

    public static void execute(Player p, String[] a) {

        if (a.length!=1) {
            p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf join");
        } else {
            Team team = CTF.tm.getValidTeam();
            CTF.gm.addPlayerToGame(p, team);
            p.sendMessage(CTF.TAG_GREEN + "You recieved kit §cRed§a.");
            CTF.tm.givePlayerKit(p, team);
            CTF.gm.broadcastMessageInGame(CTF.TAG_GREEN + "§r" +  CTF.tm.getPlayerNameInTeamColor(p) + " §ajoined the game!");
        }

    }

}
