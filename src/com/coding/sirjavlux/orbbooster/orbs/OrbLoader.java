package com.coding.sirjavlux.orbbooster.orbs;

import com.coding.sirjavlux.orbbooster.core.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class OrbLoader {

    public void loadOrbsFromConfig(OrbManager manager) {
        Main plugin = Main.getPlugin(Main.class);
        FileConfiguration config = plugin.getOrbConfig();
        if (config.isConfigurationSection("orbs")) {
            for (String name : config.getConfigurationSection("orbs").getKeys(false)) {
                try {
                    String displayName = config.contains("orbs." + name + ".display-name") ? config.getString("orbs." + name + ".display-name") : "";
                    List<String> lore = config.contains("orbs." + name + ".lore") ? config.getStringList("orbs." + name + ".lore") : new ArrayList<>();
                    Material mat = Material.valueOf(config.getString("orbs." + name + ".material"));
                    String displayNameDest = config.contains("orbs." + name + ".destroyed-display-name") ? config.getString("orbs." + name + ".destroyed-display-name") : "";
                    List<String> loreDest = config.contains("orbs." + name + ".destroyed-lore") ? config.getStringList("orbs." + name + ".destroyed-lore") : new ArrayList<>();
                    Material matDest = Material.valueOf(config.getString("orbs." + name + ".destroyed-material"));
                    String displayNameBox = config.contains("orbs." + name + ".box-display-name") ? config.getString("orbs." + name + ".box-display-name") : "";
                    List<String> loreBox = config.contains("orbs." + name + ".box-lore") ? config.getStringList("orbs." + name + ".box-lore") : new ArrayList<>();
                    Material matBox = Material.valueOf(config.getString("orbs." + name + ".box-material"));
                    double boostMin = (config.contains("orbs." + name + ".boost-min") ? config.getDouble("orbs." + name + ".boost-min") : 2) + 100;
                    boostMin /= 100d;
                    double boostMax = (config.contains("orbs." + name + ".boost-max") ? config.getDouble("orbs." + name + ".boost-max") : 20) + 100;
                    boostMax /= 100d;
                    double chanceMin = config.contains("orbs." + name + ".chance-min") ? config.getDouble("orbs." + name + ".chance-min") : 70;
                    chanceMin /= 100d;
                    double chanceMax = config.contains("orbs." + name + ".chance-max") ? config.getDouble("orbs." + name + ".chance-max") : 90;
                    chanceMax /= 100d;
                    double chanceBreak = config.contains("orbs." + name + ".chance-break") ? config.getDouble("orbs." + name + ".chance-break") : 10;
                    chanceBreak /= 100d;
                    int craftAmount = config.contains("orbs." + name + ".craft-amount") ? config.getInt("orbs." + name + ".craft-amount") : 5;
                    Orb orb = new Orb(lore, name, displayName, mat, loreDest, displayNameDest, matDest, loreBox, displayNameBox, matBox, boostMin, boostMax, chanceMin, chanceMax, chanceBreak, craftAmount);
                    manager.addOrb(orb);
                } catch (Exception e) {
                }
            }
        }
    }
}