package dev.jairusu.panadero.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplode implements Listener {

   @EventHandler
   public void onCreeperExplode(EntityExplodeEvent event) {
      if (event.getEntityType().equals(EntityType.CREEPER)) {
         event.setCancelled(true);
      }
   }

   @EventHandler
   public void onCreeperDamage(EntityDamageByEntityEvent event) {
      EntityType damageFrom = event.getDamager().getType();
      EntityType entityType = event.getEntityType();

      if (entityType.equals(EntityType.PLAYER) || !damageFrom.equals(EntityType.CREEPER)) return;
      event.setCancelled(true);
   }

}
