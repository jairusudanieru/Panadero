package dev.jairusu.panadero.Commands;

import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArenaStart implements TabCompleter, CommandExecutor {

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

      if (!playerWorld.equals(WorldGroups.arenaWorld())) {
         sender.sendMessage("Unknown command. Type \"/help\" for help.");
         return true;
      }

      player.teleport(WorldGroups.arenaLocation());
      player.getInventory().clear();

      player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
      player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
      player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
      player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
      player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));

      player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
      player.getInventory().setItem(1, new ItemStack(Material.DIAMOND_AXE));
      player.getInventory().setItem(2, new ItemStack(Material.CROSSBOW));
      player.getInventory().setItem(3, new ItemStack(Material.BOW));
      player.getInventory().setItem(4, new ItemStack(Material.ARROW,10));
      return true;
   }

}
