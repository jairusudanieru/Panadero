package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Arrays;
import java.util.List;

public class AdvancementGain implements Listener {

   @EventHandler
   public void onAdvancementGain(PlayerAdvancementDoneEvent event) {
      event.message(null);
      Player player = event.getPlayer();
      String playerName = player.getName();
      World playerWorld = player.getWorld();

      Advancement advancement = event.getAdvancement();
      String namespace = advancement.getKey().getNamespace();
      if (!namespace.contains("minecraft")) return;
      String key = advancement.getKey().getKey();
      if (key.contains("recipe/") || key.contains("recipes/")) return;

      AdvancementDisplay advancementDisplay = advancement.getDisplay();
      if (advancementDisplay == null) return;
      Component displayName = advancementDisplay.displayName();

      Component advancementTitle = advancementDisplay.title();
      String advancementName = PlainTextComponentSerializer.plainText().serialize(advancementTitle);
      List<String> list = Arrays.asList("Adventure", "Husbandry", "Minecraft", "Nether", "The End");
      if (list.contains(advancementName)) return;

      String message = switch (advancementDisplay.frame().name()) {
         case "CHALLENGE" -> " has completed the challenge ";
         case "TASK" -> " has made the advancement ";
         case "GOAL" -> " has reached the goal ";
         default -> null;
      };

      if (message == null) return;
      String fullMessage = playerName + message;

      List<String> allowedWorlds = Configuration.getStringList("advancements.allowed");
      if (!allowedWorlds.contains(playerWorld.getName())) return;

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!WorldGroups.worldGroups(playerWorld).contains(onlinePlayer.getWorld().getName())) continue;
         if (!allowedWorlds.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(Configuration.text(fullMessage).append(displayName));
      }
   }

   @EventHandler
   public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
      Player player = event.getPlayer();
      int playerLevel = player.getLevel();
      float playerExp = player.getExp();

      Advancement advancement = event.getAdvancement();
      String playerWorld = player.getWorld().getName();

      List<String> allowedWorlds = Configuration.getStringList("advancements.allowed");
      if (allowedWorlds.contains(playerWorld)) return;

      for (String criteria : advancement.getCriteria()) {
         player.getAdvancementProgress(advancement).revokeCriteria(criteria);
      }

      Bukkit.getScheduler().runTaskLater(Configuration.plugin, () -> {
         int playerNewLevel = player.getLevel();
         float playerNewExp = player.getExp();
         if (playerNewExp != playerExp || playerNewLevel != playerLevel) {
            player.setLevel(playerLevel);
            player.setExp(playerExp);
         }
      }, 1L);
   }


}
