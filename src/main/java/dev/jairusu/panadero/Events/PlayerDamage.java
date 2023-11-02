package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

   @EventHandler
   public void onPlayerDamage(EntityDamageEvent event) {
      if (!(event.getEntity() instanceof Player)) return;
      Player player = (Player) event.getEntity();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      event.setCancelled(true);
   }

}
