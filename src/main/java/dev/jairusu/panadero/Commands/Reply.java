package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.MSGManager;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Reply implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand (@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("You must be a player to use this command!");
         return true;
      }

      Player player = (Player) sender;
      if (!MSGManager.lastReceived.containsKey(player.getUniqueId()) || MSGManager.lastReceived.get(player.getUniqueId()) == null) {
         sender.sendMessage("You have no one to reply to!");
         return true;
      }

      Player target = Bukkit.getPlayer(MSGManager.lastReceived.get(player.getUniqueId()));
      if (target == null) {
         player.sendMessage("Can't find that player!");
         return true;
      }

      if (args.length < 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      String message = StringUtils.join(args, " ");
      message = message.trim();
      MSGManager.senderSendMessage(player, target, message);
      MSGManager.targetSendMessage(player, target, message);
      MSGManager.lastReceived.put(player.getUniqueId(), target.getUniqueId());
      MSGManager.lastReceived.put(target.getUniqueId(), player.getUniqueId());
      return true;
   }

}
