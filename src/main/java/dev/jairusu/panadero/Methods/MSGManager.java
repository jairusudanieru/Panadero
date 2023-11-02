package dev.jairusu.panadero.Methods;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MSGManager {

   public static HashMap<UUID, UUID> lastReceived = new HashMap<>();

   public static void senderSendMessage(Player sender, Player target, String message) {
      String senderMessage = Configuration.getString("message.senderMessage");
      if (senderMessage != null) {
         senderMessage = senderMessage.replace("%sender%",sender.getName())
                 .replace("%target%",target.getName())
                 .replace("%message%",message);
         sender.sendMessage(Configuration.text(senderMessage));
      }
   }

   public static void targetSendMessage(Player sender, Player target, String message) {
      String targetMessage = Configuration.getString("message.targetMessage");
      if (targetMessage != null) {
         targetMessage = targetMessage.replace("%sender%",sender.getName())
                 .replace("%target%",target.getName())
                 .replace("%message%",message);
         target.sendMessage(Configuration.text(targetMessage));
      }
   }

   public static void sendJoinMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = WorldGroups.worldGroups(world);
      if (!Configuration.isAuthenticated(player)) return;
      String joinMessage = Configuration.getString("message.joinMessage");
      if (joinMessage == null || joinMessage.isEmpty()) return;
      joinMessage = joinMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(WorldGroups.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(joinMessage));
   }

   public static void sendQuitMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = WorldGroups.worldGroups(world);
      if (!Configuration.isAuthenticated(player)) return;
      String quitMessage = Configuration.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(WorldGroups.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(quitMessage));
   }

   public static void sendLogoutMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = WorldGroups.worldGroups(world);
      String quitMessage = Configuration.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(WorldGroups.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(quitMessage));
   }

}
