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

      boolean isArmorStand = entityType.equals(EntityType.ARMOR_STAND);
      boolean isItemFrame = entityType.equals(EntityType.ITEM_FRAME);
      boolean isMinecart = entityType.equals(EntityType.MINECART);

      if (!damageFrom.equals(EntityType.CREEPER)) return;
      if (!isArmorStand && !isItemFrame && !isMinecart) return;
      event.setCancelled(true);
   }

}
