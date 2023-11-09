package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Suicide implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("You must be a player to use this command!");
         return true;
      }

      Player player = (Player) sender;
      World playerWorld = player.getWorld();

      if (playerWorld.equals(WorldGroups.lobbyWorld()) || WorldGroups.inArenaHub(player)) {
         sender.sendMessage("Unknown command. Type \"/help\" for help.");
         return true;
      }

      player.setHealth(0);
      return true;
   }

}
