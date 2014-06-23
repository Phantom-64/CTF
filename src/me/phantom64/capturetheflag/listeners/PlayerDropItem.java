package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (CTF.gm.isPlaying(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

}
