package me.phantom64.capturetheflag.listeners;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (CTF.gm.isPlaying(e.getPlayer())) {
            e.setFormat(CTF.TAG_GREEN + "§r" + CTF.tm.getPlayerNameInTeamColor(e.getPlayer()) + " §b> §d" + e.getMessage());
        } else {
            e.setFormat("§f" + e.getPlayer().getName() + " §0> §7" + e.getMessage());
        }
    }

}
