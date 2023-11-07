package dev.jairusu.panadero;

import dev.jairusu.panadero.Commands.*;
import dev.jairusu.panadero.Events.*;
import dev.jairusu.panadero.Methods.AFKManager;
import dev.jairusu.panadero.Methods.SelectorGUI;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Panadero extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        SelectorGUI.makeInventory();
        AFKManager.checkPlayerStatus();
        WorldGroups.setDefaults();
    }

    @Override
    public void onDisable() {
        AFKManager.clearAFKTeamEntries();
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new AdvancementGain(),this);
        Bukkit.getPluginManager().registerEvents(new ChangeWorld(),this);
        Bukkit.getPluginManager().registerEvents(new CreeperExplode(),this);
        Bukkit.getPluginManager().registerEvents(new FarmProtect(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryMove(),this);
        Bukkit.getPluginManager().registerEvents(new LaunchPad(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuit(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(),this);
        Bukkit.getPluginManager().registerEvents(new SpawnEggs(),this);
    }

    public static void registerCommands() {
        registerCommand("afk", new AFK(), new AFK());
        registerCommand("arenastart", new ArenaStart(), new ArenaStart());
        registerCommand("panadero", new Main(), new Main());
        registerCommand("reply", new Reply(), new Reply());
        registerCommand("setarena", new SetArena(), new SetArena());
        registerCommand("setlocation", new SetLocation(), new SetLocation());
        registerCommand("spawn", new Spawn(), new Spawn());
        registerCommand("suicide", new Suicide(), new Suicide());
        registerCommand("whisper", new Whisper(), new Whisper());
    }

    private static void registerCommand(String command, CommandExecutor executor, TabCompleter completer) {
        PluginCommand pluginCommand = Objects.requireNonNull(Bukkit.getPluginCommand(command));
        pluginCommand.setExecutor(executor);
        pluginCommand.setTabCompleter(completer);
    }

}
