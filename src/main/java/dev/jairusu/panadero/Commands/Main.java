package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (!sender.isOp()) return Arrays.asList("ping");
      if (args.length != 1) return new ArrayList<>();
      return Arrays.asList("reload","ping");
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length != 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      if (args[0].equals("reload")) {
         Configuration.plugin.reloadConfig();
         sender.sendMessage("Configuration successfully reloaded");
      } else if (args[0].equals("ping")) {
         sender.sendMessage("pong");
      }
      return true;
   }

}
