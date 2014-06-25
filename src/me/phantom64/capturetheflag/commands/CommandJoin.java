package me.phantom64.capturetheflag.commands;

import me.phantom64.capturetheflag.CTF;
import me.phantom64.capturetheflag.utils.Team;
import org.bukkit.entity.Player;

public class CommandJoin {

    public static void execute(Player p, String[] a) {

        if (a.length!=1) {
            p.sendMessage(CTF.TAG_BLUE + "Usage: /ctf join");
        } else {
            if (!CTF.gm.isPlaying(p)) {
                Team team = CTF.tm.getValidTeam();
                CTF.gm.addPlayerToGame(p, team);
                CTF.tm.givePlayerKit(p, team);
                p.setScoreboard(CTF.sm.getScoreBoard());
                p.setHealth(20.0);
                p.setFoodLevel(20);
                CTF.gm.broadcastMessageInGame(CTF.TAG_GREEN + "§r" +  CTF.tm.getPlayerNameInTeamColor(p) + " §ajoined the game!");
            } else {
                p.sendMessage(CTF.TAG_BLUE + "You are already in the game!");
            }
        }

    }

}
