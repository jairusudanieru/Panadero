package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Spawn implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length != 1) return new ArrayList<>();
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("You must be a player to use this command!");
         return true;
      }

      Location location = Configuration.getLocation("location.spawnLocation");
      if (location == null) {
         sender.sendMessage("Can't find that location!");
         return true;
      }

      if (args.length > 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      Player player = (Player) sender;
      if (args.length == 0 || !player.isOp()) {
         player.teleport(location);
         return true;
      }

      Player target = Bukkit.getPlayer(args[0]);
      if (target == null) {
         sender.sendMessage("Can't find that player!");
      } else {
         target.teleport(location);
      }
      return true;
   }

}
