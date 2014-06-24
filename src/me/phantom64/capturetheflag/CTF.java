package me.phantom64.capturetheflag;

import me.phantom64.capturetheflag.commands.CommandJoin;
import me.phantom64.capturetheflag.commands.CommandLeave;
import me.phantom64.capturetheflag.commands.CommandSetFlagSpawn;
import me.phantom64.capturetheflag.commands.CommandSetSpawn;
import me.phantom64.capturetheflag.listeners.*;
import me.phantom64.capturetheflag.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * TO-DO:
 */

public class CTF extends JavaPlugin implements Listener {

    public static String TAG_GREEN = "§5§l[§3§lCTF§5§l] §a";
    public static String TAG_BLUE = "§5§l[§3§lCTF§5§l] §2";
    public static String TAG_RED = "§5§l[§3§lCTF§5§l] §c";

    public static GameManager gm;
    public static LocationHandler lh;
    public static TeamManager tm;
    public static FlagManager fm;
    public static ScoreManager sm;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        gm = new GameManager(this);
        lh = new LocationHandler(this);
        tm = new TeamManager(this);
        fm = new FlagManager(this);
        sm = new ScoreManager(this);
        sm.setupScoreboard();
        sm.setRedCaptures(0);
        sm.setBlueCaptures(0);
        sm.updateScoreboard();
        registerListeners(this, new AsyncPlayerChat(), new BlockListener(), new FoodLevelChange(),
                new InventoryClick(), new PlayerDropItem(), new PlayerDeath(), new PlayerJoin(),
                new PlayerQuit());
        getLogger().info("CTF has been enabled!");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (fm.getRedFlagSpawn().getBlock().getType()!=Material.AIR) {
                    fm.getRedFlagSpawn().getWorld().playEffect(fm.getRedFlagSpawn(), Effect.MOBSPAWNER_FLAMES, 2004);
                }
            }
        }, 0L, 10L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (fm.getBlueFlagSpawn().getBlock().getType()!=Material.AIR) {
                    fm.getBlueFlagSpawn().getWorld().playEffect(fm.getBlueFlagSpawn(), Effect.MOBSPAWNER_FLAMES, 2004);
                }
            }
        }, 0L, 10L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player pl : getServer().getOnlinePlayers()) {
                    if (gm.isPlaying(pl)) {
                        if (fm.hasFlag(pl)) {
                            pl.getWorld().playEffect(pl.getLocation(), Effect.MOBSPAWNER_FLAMES, 2004);
                        }
                    }
                }
            }
        }, 0L, 10L);
    }

    @Override
    public void onDisable() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        getLogger().info("CTF has been disabled.");
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
            getLogger().info("Event " + listener.getClass().getSimpleName() + " registered.");
        }
        getLogger().info("All events registered!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ctf")) {

            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length==0) {
                    p.sendMessage(CTF.TAG_BLUE + "CTF plugin made by Phantom_64!");
                } else if (args[0].equalsIgnoreCase("join")) {
                    CommandJoin.execute(p, args);
                } else if (args[0].equalsIgnoreCase("setspawn")) {
                    CommandSetSpawn.execute(p, args);
                } else if (args[0].equalsIgnoreCase("leave")) {
                    CommandLeave.execute(p, args);
                } else if (args[0].equalsIgnoreCase("setflagspawn")) {
                    CommandSetFlagSpawn.execute(p, args);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            }

        }

        return true;
    }

    private void shootFirework(Location loc) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.CREEPER;
        if (rt == 5) type = FireworkEffect.Type.STAR;

        Color c1 = Color.AQUA;
        Color c2 = Color.LIME;

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        fwm.addEffect(effect);

        fwm.setPower(1);

        fw.setFireworkMeta(fwm);
    }

    @EventHandler
    public void onFlagTake(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction()== Action.RIGHT_CLICK_BLOCK||e.getAction()==Action.LEFT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (CTF.gm.isPlaying(p)) {
                    if (b.getType()== Material.WOOL&&b.getData()== DyeColor.RED.getData()) {
                        if (b.getLocation().equals(CTF.fm.getRedFlagSpawn())) {
                            if (CTF.gm.getPlaying().size()>=getConfig().getInt("min_players_for_pickable_flag")) {
                                if (CTF.tm.getTeam(p)!=Team.RED) {
                                    CTF.fm.setFlag(p, Team.RED);
                                    b.setType(Material.AIR);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 2));
                                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r" + CTF.tm.getPlayerNameInTeamColor(p)
                                            + " §2picked up the §cRed flag§2!");
                                } else {
                                    if (CTF.fm.hasFlag(p)) {
                                        CTF.fm.removeFlag(p);
                                        CTF.fm.getBlueFlagSpawn().getBlock().setType(Material.WOOL);
                                        CTF.fm.getBlueFlagSpawn().getBlock().setData(DyeColor.BLUE.getData());
                                        for (PotionEffect effect : p.getActivePotionEffects()) {
                                            p.removePotionEffect(effect.getType());
                                        }
                                        CTF.sm.addRedCaptures(1);
                                        CTF.sm.updateScoreboard();
                                        CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p)
                                                + " §9captured the flag for their team!");
                                        if (CTF.sm.getRedCaptures()>=getConfig().getInt("captures_to_win")) {
                                            CTF.gm.broadcastMessageInGame(CTF.TAG_GREEN + "§cTeam Red §awon the game!");
                                            for (Player pl : p.getServer().getOnlinePlayers()) {
                                                if (CTF.gm.isPlaying(pl)) {
                                                    for (PotionEffect effect : pl.getActivePotionEffects()) {
                                                        pl.removePotionEffect(effect.getType());
                                                    }
                                                    CTF.lh.teleportPlayerToTeamSpawn(pl, CTF.tm.getTeam(pl));
                                                    shootFirework(CTF.lh.getRedSpawn());
                                                    sm.setRedCaptures(0);
                                                    sm.setBlueCaptures(0);
                                                    sm.updateScoreboard();
                                                }
                                            }
                                        }
                                    } else {
                                        p.sendMessage(CTF.TAG_BLUE + "That's your flag!");
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(CTF.TAG_BLUE + "There aren't enough players to start!");
                            }
                        }
                    } else if (b.getType()==Material.WOOL&&b.getData()==DyeColor.BLUE.getData()) {
                        if (b.getLocation().equals(CTF.fm.getBlueFlagSpawn())) {
                            if (CTF.gm.getPlaying().size()>=getConfig().getInt("min_players_for_pickable_flag")) {
                                if (CTF.tm.getTeam(p)!=Team.BLUE) {
                                    CTF.fm.setFlag(p, Team.BLUE);
                                    b.setType(Material.AIR);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 2));
                                    CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + "§r" + CTF.tm.getPlayerNameInTeamColor(p)
                                            + " §2picked up §9the Blue flag§2!");
                                } else {
                                    if (CTF.fm.hasFlag(p)) {
                                        CTF.fm.removeFlag(p);
                                        CTF.fm.getRedFlagSpawn().getBlock().setType(Material.WOOL);
                                        CTF.fm.getRedFlagSpawn().getBlock().setData(DyeColor.RED.getData());
                                        for (PotionEffect effect : p.getActivePotionEffects()) {
                                            p.removePotionEffect(effect.getType());
                                        }
                                        CTF.sm.addBlueCaptures(1);
                                        CTF.sm.updateScoreboard();
                                        CTF.gm.broadcastMessageInGame(CTF.TAG_BLUE + CTF.tm.getPlayerNameInTeamColor(p)
                                                + " §2captured the flag for their team!");
                                        if (CTF.sm.getBlueCaptures()>=getConfig().getInt("captures_to_win")) {
                                            CTF.gm.broadcastMessageInGame(CTF.TAG_GREEN + "§9Team Blue §awon the game!");
                                            for (Player pl : p.getServer().getOnlinePlayers()) {
                                                if (CTF.gm.isPlaying(pl)) {
                                                    for (PotionEffect effect : pl.getActivePotionEffects()) {
                                                        pl.removePotionEffect(effect.getType());
                                                    }
                                                    CTF.lh.teleportPlayerToTeamSpawn(pl, CTF.tm.getTeam(pl));
                                                    shootFirework(CTF.lh.getBlueSpawn());
                                                    sm.setRedCaptures(0);
                                                    sm.setBlueCaptures(0);
                                                    sm.updateScoreboard();
                                                }
                                            }
                                        }
                                    } else {
                                        p.sendMessage(CTF.TAG_BLUE + "That's your flag!");
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                                p.sendMessage(CTF.TAG_BLUE + "There aren't enough players to start!");
                            }
                    }

                }
            }
        }
    }

}
