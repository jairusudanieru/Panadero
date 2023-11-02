package dev.jairusu.panadero.Methods;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class LobbyItem {

   public static ItemStack LIME_DYE() {
      ItemStack itemStack = new ItemStack(Material.LIME_DYE);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.text("<reset>Players: <green>Visible"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>Click to hide other players"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack GRAY_DYE() {
      ItemStack itemStack = new ItemStack(Material.GRAY_DYE);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.text("<reset>Players: <red>Hidden"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>Click to show other players"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack INFO_BOOK() {
      ItemStack itemStack = new ItemStack(Material.BOOK);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.text("<reset>Pandesal Book"));
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>by Panadero"));
      itemMeta.lore(lore);
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack COMPASS() {
      ItemStack itemStack = new ItemStack(Material.COMPASS);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(Configuration.text("<reset>Server Selector"));
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public static ItemStack SURVIVAL() {
      String url = "http://textures.minecraft.net/texture/438cf3f8e54afc3b3f91d20a49f324dca1486007fe545399055524c17941f4dc";
      String itemName = "<white>Survival";
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>Journey through the Wilderness"));
      return getCustomHead(url, itemName, lore);
   }

   public static ItemStack CREATIVE() {
      String url = "http://textures.minecraft.net/texture/594c79f03e191b93477f7c19557408f7af4f9660e5dfb0687e3b8eb92fbd3ae1";
      String itemName = "<white>Creative";
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>Virtual Place for Creativity"));
      return getCustomHead(url, itemName, lore);
   }

   public static ItemStack PVP_ARENA() {
      String url = "http://textures.minecraft.net/texture/dd74d28d097e53db1112e390a7d4ff82df718866aea8f1650d946657a9369699";
      String itemName = "<white>PVP Arena";
      List<Component> lore = new ArrayList<>();
      lore.add(Configuration.text("<gray>Arena for the Strongest"));
      return getCustomHead(url, itemName, lore);
   }

   public static ItemStack getCustomHead(String texture, String itemName, List<Component> itemLore) {
      ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
      if (texture == null || texture.isEmpty()) return itemStack;

      SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
      headMeta.displayName(Configuration.text(itemName));
      headMeta.lore(itemLore);
      GameProfile profile = new GameProfile(UUID.randomUUID(), null);
      byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
      profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

      try {
         Field profileField = headMeta.getClass().getDeclaredField("profile");
         profileField.setAccessible(true);
         profileField.set(headMeta, profile);
      } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
         Bukkit.getLogger().warning(exception.getMessage());
      }

      itemStack.setItemMeta(headMeta);
      return itemStack;
   }

}
