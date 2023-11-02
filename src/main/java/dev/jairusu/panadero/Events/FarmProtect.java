package dev.jairusu.panadero.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FarmProtect implements Listener {

   @EventHandler
   public void onFarmTrample(EntityChangeBlockEvent event) {
      if (event.getBlock().getType().equals(Material.FARMLAND)) {
         event.setCancelled(true);
      }
   }

}
