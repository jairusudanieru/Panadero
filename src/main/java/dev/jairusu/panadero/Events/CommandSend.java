package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommandSend implements Listener {

   @EventHandler
   public void onArenaCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Collection<String> commands = event.getCommands();
      if (playerWorld.equals(WorldGroups.arenaWorld()) && !WorldGroups.inArenaHub(player)) return;
      commands.remove("arenastart");
   }

   @EventHandler
   public void onSuicideCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Collection<String> commands = event.getCommands();
      if (playerWorld.equals(WorldGroups.lobbyWorld())) {
         commands.remove("suicide");
         return;
      }

      if (playerWorld.equals(WorldGroups.creativeWorld())) {
         commands.remove("suicide");
         return;
      }

      if (playerWorld.equals(WorldGroups.authWorld())) {
         commands.remove("suicide");
      }
   }

   @EventHandler
   public void onLoginCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Collection<String> commands = event.getCommands();
      if (!playerWorld.equals(WorldGroups.authWorld())) {
         commands.removeAll(Arrays.asList("register","login","log","reg"));
         return;
      }

      commands.clear();
      commands.addAll(Arrays.asList("register","login","log","reg"));
   }

   @EventHandler
   public void onAFKCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Collection<String> commands = event.getCommands();
      List<String> worldGroups = WorldGroups.worldGroups(WorldGroups.survivalWorld());
      if (worldGroups.contains(playerWorld.getName())) return;
      commands.remove("afk");
   }

}
