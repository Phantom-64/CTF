package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import me.phantom64.capturetheflag.utils.Team;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private CTF plugin;

    @EventHandler
    public void onFlagTake(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction()== Action.RIGHT_CLICK_BLOCK||e.getAction()==Action.LEFT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (CTF.gm.isPlaying(p)) {
                if (b.getType()==Material.WOOL&&b.getData()==DyeColor.RED.getData()) {
                    CTF.fm.setFlag(p, Team.RED);
                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r" + CTF.tm.getPlayerNameInTeamColor(p)
                            + " §9picked up the §cRed flag§9!");

                } else if (b.getType()==Material.WOOL&&b.getData()==DyeColor.BLUE.getData()) {
                    CTF.fm.setFlag(p, Team.BLUE);
                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r" + CTF.tm.getPlayerNameInTeamColor(p)
                            + " §9picked up the Blue flag!");
                }
            }
        }
    }

}
