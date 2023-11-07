package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

   @EventHandler
   public void onPlayerDamage(EntityDamageEvent event) {
      if (!(event.getEntity() instanceof Player)) return;
      Player player = (Player) event.getEntity();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      event.setCancelled(true);
   }

   @EventHandler
   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
      EntityType entityDamager = event.getDamager().getType();
      EntityType entityType = event.getEntityType();
      if (!entityType.equals(EntityType.PLAYER)) return;
      if (!entityDamager.equals(EntityType.PLAYER)) return;

      Player player = (Player) event.getDamager();
      World world = player.getWorld();
      if (!world.getName().equals("pvp_arena")) return;

      if (!WorldGroups.inArenaHub(player)) return;
      event.setCancelled(true);
   }

}
