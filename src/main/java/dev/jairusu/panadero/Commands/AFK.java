package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.AFKManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AFK implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player)) return true;
      Player player = (Player) sender;
      if (AFKManager.afkTeam().hasEntry(player.getName())) {
         AFKManager.removeToAFKTeam(player, AFKManager.afkTeam());
         return true;
      }

      AFKManager.addToAFKTeam(player, AFKManager.afkTeam());
      return true;
   }

}
