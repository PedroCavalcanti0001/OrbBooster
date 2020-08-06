package com.coding.sirjavlux.orbbooster.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class OrbGUIManager {

    private HashMap<UUID, OrbGUI> guis = new HashMap<>();

    public OrbGUI getOrbGUI(Player p) {
        UUID uuid = p.getUniqueId();
        return guis.containsKey(uuid) ? guis.get(uuid) : new OrbGUI(p);
    }

    public void removeOrbGUI(UUID uuid) {
        if (guis.containsKey(uuid)) guis.remove(uuid);
    }
}
