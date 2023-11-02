package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.Configuration;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetLocation implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
      if (args.length != 1) return new ArrayList<>();
      ConfigurationSection locationSection = Configuration.getConfigSection("location");
      if (locationSection == null) return new ArrayList<>();
      return new ArrayList<>(locationSection.getKeys(false));
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage(Configuration.text("You must be a player to use this command!"));
         return true;
      }

      if (args.length != 1) {
         sender.sendMessage(Component.text("Invalid command usage!"));
         return true;
      }

      ConfigurationSection locationSection = Configuration.getConfigSection("location");
      if (locationSection == null) return true;
      Set<String> locations = locationSection.getKeys(false);
      if (!locations.contains(args[0])) {
         sender.sendMessage(Component.text("Invalid command usage!"));
         return true;
      }

      Player player = (Player) sender;
      Configuration.setLocation("location." + args[0], player.getLocation());
      sender.sendMessage(Component.text(args[0] + " successfully set"));
      return true;
   }

}