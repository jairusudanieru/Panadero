package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.Collection;

public class PlayerCommand implements Listener {

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
      if (playerWorld.equals(WorldGroups.lobbyWorld())) return;
      if (playerWorld.equals(WorldGroups.creativeWorld())) return;
      if (WorldGroups.inArenaHub(player)) return;
      commands.remove("suicide");
   }

   @EventHandler
   public void onLoginCommand(PlayerCommandSendEvent event) {
      Player player = event.getPlayer();

      Collection<String> commands = event.getCommands();
      if (Configuration.isAuthenticated(player)) return;
      commands.clear();
      commands.addAll(Arrays.asList("register","login","log","reg"));
   }

}
