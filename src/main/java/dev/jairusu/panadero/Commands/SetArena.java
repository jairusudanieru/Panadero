package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetArena implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      if (args.length != 1) return new ArrayList<>();
      ConfigurationSection locationSection = Configuration.getConfigSection("arenaHub");
      if (locationSection == null) return new ArrayList<>();
      return new ArrayList<>(locationSection.getKeys(false));
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("You must be a player to use this command!");
         return true;
      }

      Player player = (Player) sender;
      World playerWorld = player.getWorld();
      if (!playerWorld.equals(WorldGroups.arenaWorld())) return true;

      if (args.length != 1) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      ConfigurationSection locationSection = Configuration.getConfigSection("arenaHub");
      if (locationSection == null) return true;
      Set<String> locations = locationSection.getKeys(false);
      if (!locations.contains(args[0])) {
         sender.sendMessage("Invalid command usage!");
         return true;
      }

      Configuration.setLocation("arenaHub." + args[0], player.getLocation());
      sender.sendMessage(args[0] + " successfully set");
      return true;
   }

}
