package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (CTF.gm.isPlaying(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (CTF.gm.isPlaying(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

}
