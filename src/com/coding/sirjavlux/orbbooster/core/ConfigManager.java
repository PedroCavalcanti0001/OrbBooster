package com.coding.sirjavlux.orbbooster.core;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private FileConfiguration config;
    private Settings settings;
    private Menu menu;
    private Messages messages;

    public ConfigManager() {
        config = Main.getPlugin(Main.class).getConfig();
        this.settings = new Settings();
        this.menu = new Menu();
        this.messages = new Messages();
    }

    public Settings getSettings() {
        return settings;
    }

    public Menu getMenu() {
        return menu;
    }

    public Messages getMessages() {
        return messages;
    }

    public class Settings {

        private int maxSlots;
        private String sellBoostStr;
        private HashMap<Integer, Integer> slotRequirementsEzBlocks = new HashMap<>();

        public Settings() {
            maxSlots = config.contains("settings.max-orbs") ? config.getInt("settings.max-orbs") : 5;
            sellBoostStr = config.contains("settings.lore-text") ? config.getString("settings.lore-text") : "&a>> &7Sell boost &8%boost%";
        }

        public int getMaxSlots() {
            return maxSlots;
        }

        public String getSellBoostString() {
            return sellBoostStr;
        }

        public HashMap<Integer, Integer> getSlotRequirementsEzBlocks() {
            return new HashMap<>(slotRequirementsEzBlocks);
        }
    }

    public class Menu {

        private String emptyDisplayName;
        private List<String> emptyLore;
        private Material emptyMaterial;

        public Menu() {
            emptyDisplayName = config.contains("menu.empty.display-name") ? config.getString("menu.empty.display-name") : "&7Empty";
            emptyLore = config.contains("menu.empty.lore") ? config.getStringList("menu.empty.lore") : new ArrayList<>();
            try {
                emptyMaterial = Material.valueOf(config.getString("menu.empty.material"));
            } catch (Exception e) {
                emptyMaterial = Material.COAL;
            }
        }

        public String getEmptyDisplayName() {
            return emptyDisplayName;
        }

        public List<String> getEmptyLore() {
            return emptyLore;
        }

        public Material getEmptyMaterial() {
            return emptyMaterial;
        }
    }

    public class Messages {
        public String argsnotvalid, invalidargsgive, recievegive, boxgave, invalidnumber, invalidtarget, invalidorb, maxslotsreached, blocksleft;

        public Messages() {
            this.argsnotvalid = config.getString("messages.args-not-valid");
            this.invalidargsgive = config.getString("messages.invalid-args-give");
            this.recievegive = config.getString("messages.recieve-give");
            this.boxgave = config.getString("messages.box-gave");
            this.invalidnumber = config.getString("messages.invalid-number");
            this.invalidtarget = config.getString("messages.invalid-target");
            this.invalidorb = config.getString("messages.invalid-orb");
            this.maxslotsreached = config.getString("messages.max-slots-reached");
            this.blocksleft = config.getString("messages.blocks-left");
        }
    }
}