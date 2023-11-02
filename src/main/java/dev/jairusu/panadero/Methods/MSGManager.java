package dev.jairusu.panadero.Methods;

import org.bukkit.entity.Player;

import java.util.HashMap;
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

}
