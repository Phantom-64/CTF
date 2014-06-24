package me.phantom64.capturetheflag.utils;

import me.phantom64.capturetheflag.CTF;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.DisplaySlot;


public class ScoreManager {

    private CTF plugin;

    public ScoreManager(CTF plugin) {
        this.plugin = plugin;
    }

    public int getRedCaptures() {
        return plugin.getConfig().getInt("Captures.red");
    }

    public void addRedCaptures(int redCaptures) {
        plugin.getConfig().set("Captures.red", getRedCaptures() + redCaptures);
        plugin.saveConfig();
    }

    public void setRedCaptures(int redCaptures) {
        plugin.getConfig().set("Captures.red", redCaptures);
        plugin.saveConfig();
    }

    public int getBlueCaptures() {
        return plugin.getConfig().getInt("Captures.blue");
    }

    public void addBlueCaptures(int blueCaptures) {
        plugin.getConfig().set("Captures.blue", getBlueCaptures() + blueCaptures);
        plugin.saveConfig();
    }

    public void setBlueCaptures(int blueCaptures) {
        plugin.getConfig().set("Captures.blue", blueCaptures);
        plugin.saveConfig();
    }

    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard sb = manager.getNewScoreboard();

    public Scoreboard getScoreBoard() {
        return this.sb;
    }

    public void setupScoreboard() {
        Team red = getScoreBoard().registerNewTeam("red");
        Team blue = getScoreBoard().registerNewTeam("blue");
        red.setDisplayName("§cRed");
        red.setAllowFriendlyFire(false);
        red.setCanSeeFriendlyInvisibles(true);
        blue.setDisplayName("§9Blue");
        blue.setAllowFriendlyFire(false);
        blue.setCanSeeFriendlyInvisibles(true);
        Objective obj = getScoreBoard().registerNewObjective("captures", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§d§lCaptures");
        Score redScore = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Red"));
        Score blueScore = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Blue"));
        redScore.setScore(getRedCaptures());
        redScore.setScore(getBlueCaptures());
    }

    public void updateScoreboard() {
        getScoreBoard().getObjective(DisplaySlot.SIDEBAR).getScore
                (Bukkit.getOfflinePlayer(ChatColor.RED + "Red")).setScore(getRedCaptures());
        getScoreBoard().getObjective(DisplaySlot.SIDEBAR).getScore
                (Bukkit.getOfflinePlayer(ChatColor.BLUE + "Blue")).setScore(getBlueCaptures());
    }
}
