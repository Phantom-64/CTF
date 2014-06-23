package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (CTF.gm.isPlaying(p)) {
                e.setCancelled(true);
            }
        }
    }

}
