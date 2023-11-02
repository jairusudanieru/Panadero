package dev.jairusu.panadero.Methods;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class AFKManager {

   public static final HashMap<Player, String> afkStatusHashMap = new HashMap<>();
   public static final HashMap<Player, Long> lastMovementHashMap = new HashMap<>();

   public static Team afkTeam() {
      Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
      Team afkTeam = scoreboard.getTeam("afk");
      if (afkTeam == null) {
         afkTeam = scoreboard.registerNewTeam("afk");
         afkTeam.color(NamedTextColor.GRAY);
      }
      return afkTeam;
   }

   public static void addToAFKTeam(Player player, Team afkTeam) {
      if (afkTeam.hasEntry(player.getName())) return;
      afkTeam.addEntry(player.getName());
      afkStatusHashMap.put(player,"AFK");
   }

   public static void removeToAFKTeam(Player player, Team afkTeam) {
      if (!afkTeam.hasEntry(player.getName())) return;
      afkTeam.removeEntry(player.getName());
      afkStatusHashMap.remove(player);
   }

   public static void checkPlayerStatus() {
      Bukkit.getScheduler().scheduleSyncRepeatingTask(Configuration.plugin, () -> {
         for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isPlayerAFK(player)) continue;
            if (afkStatusHashMap.containsKey(player)) continue;
            addToAFKTeam(player, afkTeam());
         }
      }, 0L, 20L);
   }

   public static void clearAFKTeamEntries() {
      Team afkTeam = afkTeam();
      for (String entries : afkTeam.getEntries()) {
         afkTeam.removeEntry(entries);
      }
   }

   private static boolean isPlayerAFK(Player player) {
      long afkTimeout = Configuration.getLong("config.afkTimeout") * 1000;
      if (afkTimeout == 0) afkTimeout = 300000;
      long currentTime = System.currentTimeMillis();
      long lastMovementTime = lastMovementHashMap.getOrDefault(player, currentTime);
      return currentTime - lastMovementTime >= afkTimeout;
   }

}
