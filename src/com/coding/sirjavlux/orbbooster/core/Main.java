package com.coding.sirjavlux.orbbooster.core;

import com.coding.sirjavlux.orbbooster.commands.CommandManager;
import com.coding.sirjavlux.orbbooster.gui.OrbGUIManager;
import com.coding.sirjavlux.orbbooster.listener.AutoSellListener;
import com.coding.sirjavlux.orbbooster.listener.InventoryListener;
import com.coding.sirjavlux.orbbooster.listener.PlayerListener;
import com.coding.sirjavlux.orbbooster.orbs.OrbManager;
import com.coding.sirjavlux.orbbooster.utils.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private String pluginPrefix = "Orb Booster";
    private ConfigManager configManager;
    private FileConfiguration orbConfig;
    private OrbManager orbManager;
    private OrbGUIManager orbGUIManager;
    private boolean autoMine = false;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configManager = new ConfigManager();

        File file = new File(this.getDataFolder() + "/orbs.yml");
        if (!file.exists()) FileManager.writeFileFromResources(this, this.getDataFolder() + "/orbs.yml", "orbs.yml");
        orbConfig = YamlConfiguration.loadConfiguration(file);

        orbManager = new OrbManager();
        orbGUIManager = new OrbGUIManager();

        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new AutoSellListener(), this);

        if (this.getServer().getPluginManager().getPlugin("AutoMine") != null) autoMine = true;

        this.getServer().getPluginCommand("orbbooster").setExecutor(new CommandManager());

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + pluginPrefix + " successfully enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + pluginPrefix + " disabled!");
    }

    public boolean isAutoMineEnabled() {
        return autoMine;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FileConfiguration getOrbConfig() {
        return orbConfig;
    }

    public OrbManager getOrbManager() {
        return orbManager;
    }

    public OrbGUIManager getOrbGUIManager() {
        return orbGUIManager;
    }
} 
