package dev.jairusu.panadero.Methods;

import dev.jairusu.panadero.Panadero;
import fr.xephi.authme.api.v3.AuthMeApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Configuration {

   public static JavaPlugin plugin = JavaPlugin.getPlugin(Panadero.class);

   public static int getInt(String key) {
      return Configuration.plugin.getConfig().getInt(key);
   }

   public static long getLong(String key) {
      return Configuration.plugin.getConfig().getLong(key);
   }

   public static double getDouble(String key) {
      return Configuration.plugin.getConfig().getDouble(key);
   }

   public static String getString(String key) {
      return Configuration.plugin.getConfig().getString(key);
   }

   public static ConfigurationSection getConfigSection(String key) {
      return Configuration.plugin.getConfig().getConfigurationSection(key);
   }
   
   public static List<String> getStringList(String key) {
      return Configuration.plugin.getConfig().getStringList(key);
   }

   public static Location getLocation(String key) {
      return Configuration.plugin.getConfig().getLocation(key);
   }

   public static World getWorld(String key) {
      String string = Configuration.plugin.getConfig().getString(key);
      if (string == null) return null;
      return Bukkit.getWorld(string);
   }

   public static Component text(String string) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(string).decoration(TextDecoration.ITALIC,false);
   }

   public static void setLocation(String key, Location location) {
      Configuration.plugin.getConfig().set(key, location);
      Configuration.plugin.saveConfig();
   }

   public static boolean isAuthenticated(Player player) {
      return AuthMeApi.getInstance().isAuthenticated(player);
   }

}
