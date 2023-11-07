package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.MSGManager;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Whisper implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (!(sender instanceof Player)) return new ArrayList<>();
      if (args.length > 1) return new ArrayList<>();
      Player player = (Player) sender;
      List<String> players = new ArrayList<>();
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         World onlinePlayerWorld = onlinePlayer.getWorld();
         if (!WorldGroups.worldGroups(player.getWorld()).contains(onlinePlayerWorld.getName())) continue;
         players.add(onlinePlayer.getName());
      }
      return players;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length < 2) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      Player target = Bukkit.getPlayerExact(args[0]);
      if (target == null) {
         sender.sendMessage("Can't find that player!");
         return true;
      }

      String message = StringUtils.join(args, " ");
      message = message.trim();
      message = message.substring(args[0].length() + 1);

      if (sender instanceof Player) {
         Player player = (Player) sender;
         MSGManager.senderSendMessage(player, target, message);
         MSGManager.targetSendMessage(player, target, message);
         MSGManager.lastReceived.put(player.getUniqueId(), target.getUniqueId());
         MSGManager.lastReceived.put(target.getUniqueId(), player.getUniqueId());
      } else {
         MSGManager.lastReceived.put(target.getUniqueId(), null);
      }
      return true;
   }

}
