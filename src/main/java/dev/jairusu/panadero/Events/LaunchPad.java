package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LaunchPad implements Listener {

   @EventHandler
   public void onLaunchPads(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;

      if (event.getAction() != Action.PHYSICAL) return;
      Block block = event.getClickedBlock();
      if (block == null) return;
      Material material = block.getType();
      Location blockUnder = player.getLocation();
      blockUnder.setY(blockUnder.getY() - 1);

      double power = Configuration.getDouble("config.launchpadPower");
      double height = Configuration.getDouble("config.launchpadHeight");

      if (!material.name().contains("PRESSURE_PLATE")) return;
      event.setCancelled(true);

      if (!blockUnder.getBlock().getType().equals(Material.GOLD_BLOCK)) return;
      player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
      player.setVelocity(player.getLocation().getDirection().multiply(power).setY(height));
   }

}
