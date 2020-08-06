package com.coding.sirjavlux.orbbooster.orbs;

import com.coding.sirjavlux.orbbooster.api.events.OrbRemoveEvent;
import com.coding.sirjavlux.orbbooster.core.Main;
import com.coding.sirjavlux.orbbooster.utils.InventoryHandler;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class OrbManager {

    private HashMap<String, Orb> orbs = new HashMap<>();
    private OrbLoader loader;
    private String invisKey = ChatColor.translateAlternateColorCodes('&', "&7&8&1&2&9&3&7");

    /*//////////////////////////////
     * ORB DATA MANAGEMENT
     *//////////////////////////////

    public OrbManager() {
        loader = new OrbLoader();
        loader.loadOrbsFromConfig(this);
        //startItemUpdater();
    }

    public void addOrb(Orb orb) {
        orbs.put(orb.getName(), orb);
    }

    public Orb getOrb(String name) {
        return orbs.containsKey(name) ? orbs.get(name) : null;
    }

    public ItemStack generateOrbItem(Orb orb) {
        ItemStack item = new ItemStack(orb.getMaterial());
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
        Random r = new Random();
        double boost = orb.getBoostMin() + (orb.getBoostMax() - orb.getBoostMin()) * r.nextDouble();
        double chance = orb.getChanceMin() + (orb.getChanceMax() - orb.getChanceMin()) * r.nextDouble();
        tagComp.setString("orb-name", orb.getName());
        tagComp.setDouble("orb-boost", boost);
        tagComp.setDouble("orb-chance", chance);
        tagComp.setString("orb-uuid", UUID.randomUUID().toString());
        NMSItem.setTag(tagComp);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', orb.getDisplayName()));
        List<String> lore = new ArrayList<>();
        for (String str : orb.getLore()) lore.add(setPlaceholders(orb, boost, chance, str));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack generateOrbItem(Orb orb, double boost, double chance) {
        ItemStack item = new ItemStack(orb.getMaterial());
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
        tagComp.setString("orb-name", orb.getName());
        tagComp.setDouble("orb-boost", boost);
        tagComp.setDouble("orb-chance", chance);
        tagComp.setString("orb-uuid", UUID.randomUUID().toString());
        NMSItem.setTag(tagComp);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', orb.getDisplayName()));
        List<String> lore = new ArrayList<>();
        for (String str : orb.getLore()) lore.add(setPlaceholders(orb, boost, chance, str));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private String setPlaceholders(Orb orb, double boost, double chance, String str) {

        return ChatColor.translateAlternateColorCodes('&', str.replaceAll("%boost%", "" + ((int) ((boost - 1) * 100d))).replaceAll("%chance%", "" + ((int) (chance * 100d))));
    }

    public boolean isOrbItem(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("orb-name") && !tagComp.hasKey("orb-box")) {
                if (getOrb(tagComp.getString("orb-name")) != null) return true;
            }
        }
        return false;
    }

    public Orb getOrbFromItem(ItemStack item) {
        if (isOrbItem(item)) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            return getOrb(tagComp.getString("orb-name"));
        }
        return null;
    }

    public double getBoostFromOrbItem(ItemStack item) {
        if (isOrbItem(item)) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            return tagComp.getDouble("orb-boost");
        }
        return 1;
    }

    public double getChanceFromOrbItem(ItemStack item) {
        if (isOrbItem(item)) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            return tagComp.getDouble("orb-chance");
        }
        return 1;
    }

    /*//////////////////////////////
     * ORB BOX DATA MANAGEMENT
     *//////////////////////////////

    public void giveOrbToPlayer(Player p, Orb orb) {
        ItemStack item = generateOrbItem(orb);
        InventoryHandler.giveToPlayer(p, item, p.getLocation());
    }

    public ItemStack generateOrbBoxItem(Orb orb) {
        ItemStack item = new ItemStack(orb.getMaterialBox());
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
        tagComp.setString("orb-name", orb.getName());
        tagComp.setBoolean("orb-box", true);
        NMSItem.setTag(tagComp);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', orb.getDisplayNameBox()));
        List<String> lore = new ArrayList<>();
        for (String str : orb.getLoreBox()) lore.add(ChatColor.translateAlternateColorCodes('&', str));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isOrbBoxItem(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("orb-name") && tagComp.hasKey("orb-box")) {
                if (getOrb(tagComp.getString("orb-name")) != null) return true;
            }
        }
        return false;
    }

    public void giveOrbBoxToPlayer(Player p, Orb orb) {
        ItemStack item = generateOrbBoxItem(orb);
        InventoryHandler.giveToPlayer(p, item, p.getLocation());
    }

    /*//////////////////////////////
     * ORB DESTROYED DATA MANAGEMENT
     *//////////////////////////////

    public Orb getOrbFromBoxItem(ItemStack item) {
        if (isOrbBoxItem(item)) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            return getOrb(tagComp.getString("orb-name"));
        }
        return null;
    }

    public ItemStack generateOrbDestroyedItem(Orb orb) {
        ItemStack item = new ItemStack(orb.getDestMaterial());
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
        tagComp.setString("orb-name", orb.getName());
        tagComp.setBoolean("orb-destroyed", true);
        NMSItem.setTag(tagComp);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', orb.getDestDisplayName()));
        List<String> lore = new ArrayList<>();
        for (String str : orb.getDestLore()) lore.add(ChatColor.translateAlternateColorCodes('&', str));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isOrbDestroyedItem(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("orb-name") && tagComp.hasKey("orb-destroyed")) {
                if (getOrb(tagComp.getString("orb-name")) != null) return true;
            }
        }
        return false;
    }

    public void giveOrbDestroyedToPlayer(Player p, Orb orb) {
        ItemStack item = generateOrbDestroyedItem(orb);
        InventoryHandler.giveToPlayer(p, item, p.getLocation());
    }

    /*//////////////////////////////
     * ITEM ORB MANAGEMENT
     *//////////////////////////////

    public Orb getOrbFromDestroyedItem(ItemStack item) {
        if (isOrbDestroyedItem(item)) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            return getOrb(tagComp.getString("orb-name"));
        }
        return null;
    }

    public List<Orb> getItemOrbs(ItemStack item) {
        List<Orb> orbs = new ArrayList<>();
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("orbs")) {
                for (String str : tagComp.getString("orbs").split(",")) {
                    if (!str.isEmpty()) if (getOrb(str) != null) orbs.add(getOrb(str));
                }
            }
        }
        return orbs;
    }

    public ItemStack removeItemOrb(ItemStack item, int index, Player p, int slot) {
        List<Orb> orbs = getItemOrbs(item);
        List<Double> boosts = getItemBoosts(item);
        List<Double> chances = getItemChances(item);
        if (orbs.size() > index) {
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.getTag();
            orbs.remove(index);
            boosts.remove(index);
            chances.remove(index);
            String chanceStr = chances.size() > 0 ? chances.get(0) + "" : "";

            if (chances.size() > 0) chances.remove(0);
            for (double chance : chances) chanceStr += "," + chance;
            tagComp.setString("chances", chanceStr);
            String boostStr = boosts.size() > 0 ? boosts.get(0) + "" : "";
            if (boosts.size() > 0) boosts.remove(0);
            for (double boost : boosts) boostStr += "," + boost;
            tagComp.setString("boosts", boostStr);
            String orbStr = orbs.size() > 0 ? orbs.get(0).getName() : "";
            if (orbs.size() > 0) orbs.remove(0);
            for (Orb orb : orbs) orbStr += "," + orb.getName();
            tagComp.setString("orbs", orbStr);
            NMSItem.setTag(tagComp);
            item = CraftItemStack.asBukkitCopy(NMSItem);
            updateItemData(item, p, slot);
            return item;
        }
        return null;
    }

    public ItemStack addItemOrb(ItemStack item, ItemStack orbItem, Player p) {
        List<Orb> orbs = getItemOrbs(item);
        List<Double> boosts = getItemBoosts(item);
        List<Double> chances = getItemChances(item);
        if (orbs.size() < Main.getPlugin(Main.class).getConfigManager().getSettings().getMaxSlots()) {
            Orb orb = getOrbFromItem(orbItem);
            double boost = getBoostFromOrbItem(orbItem);
            double chance = getChanceFromOrbItem(orbItem);
            orbs.add(orb);
            boosts.add(boost);
            chances.add(chance);
            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
            String orbStr = orbs.size() > 0 ? orbs.get(0).getName() : "";
            if (orbs.size() > 0) orbs.remove(0);
            for (Orb orbT : orbs) orbStr += "," + orbT.getName();
            tagComp.setString("orbs", orbStr);
            String boostStr = boosts.size() > 0 ? boosts.get(0) + "" : "";
            if (boosts.size() > 0) boosts.remove(0);
            for (double boostT : boosts) boostStr += "," + boostT;
            tagComp.setString("boosts", boostStr);
            String chanceStr = chances.size() > 0 ? chances.get(0) + "" : "";
            if (chances.size() > 0) chances.remove(0);
            for (double chanceT : chances) chanceStr += "," + chanceT;
            tagComp.setString("chances", chanceStr);
            NMSItem.setTag(tagComp);
            item = CraftItemStack.asBukkitCopy(NMSItem);
            return item;
        }
        return item;
    }

    public List<Double> getItemChances(ItemStack item) {
        List<Double> chances = new ArrayList<>();
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("chances")) {
                for (String str : tagComp.getString("chances").split(",")) {
                    if (!str.isEmpty()) chances.add(Double.parseDouble(str));
                }
            }
        }
        return chances;
    }

    public List<Double> getItemBoosts(ItemStack item) {
        List<Double> boosts = new ArrayList<>();
        net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        if (NMSItem.hasTag()) {
            NBTTagCompound tagComp = NMSItem.getTag();
            if (tagComp.hasKey("boosts")) {
                for (String str : tagComp.getString("boosts").split(",")) {
                    if (!str.isEmpty()) boosts.add(Double.parseDouble(str));
                }
            }
        }
        return boosts;
    }

    public void updateItemData(ItemStack item, Player p, int slot) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            return;
        }
        Main plugin = Main.getPlugin(Main.class);
        List<Double> boosts = getItemBoosts(item);
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            List<String> lore = new ArrayList<>();
            if (boosts.size() > 0) lore.add("");
            for (double boost : boosts)
                lore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getSettings().getSellBoostString().replaceAll("%boost%", "" + ((int) (((boost - 1d) * 100d))))) + invisKey);
            meta.setLore(lore);
        } else {
            List<String> oldLore = meta.getLore();
            int i = 0;
            boolean first = true;
            for (String str : new ArrayList<>(oldLore)) {
                if (str.contains(invisKey)) {
                    if (first) {
                        oldLore.remove(i - 1);
                        first = false;
                    }
                    oldLore.remove(i - 1);
                } else i++;
            }
            if (boosts.size() > 0) oldLore.add("");
            for (double boost : boosts) {
                oldLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getSettings().getSellBoostString().replaceAll("%boost%", "" + ((int) (((boost - 1d) * 100d))))) + invisKey);
            }
            meta.setLore(oldLore);
        }
        item.setItemMeta(meta);
        PlayerInventory iv = p.getInventory();
        iv.setItem(slot, item.clone());
    }

    public ItemStack updateItemData(ItemStack item) {
        Main plugin = Main.getPlugin(Main.class);
        List<Double> boosts = getItemBoosts(item);
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            List<String> lore = new ArrayList<>();
            if (boosts.size() > 0) lore.add("");
            for (double boost : boosts)
                lore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getSettings().getSellBoostString().replaceAll("%boost%", "" + ((int) (((boost - 1d) * 100d))))) + invisKey);
            meta.setLore(lore);
        } else {
            List<String> oldLore = meta.getLore();
            int i = 0;
            boolean first = true;
            for (String str : new ArrayList<>(oldLore)) {
                if (str.contains(invisKey)) {
                    if (first) {
                        oldLore.remove(i - 1);
                        first = false;
                    }
                    oldLore.remove(i - 1);
                } else i++;
            }
            if (boosts.size() > 0) oldLore.add("");
            for (double boost : boosts)
                oldLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getSettings().getSellBoostString().replaceAll("%boost%", "" + ((int) (((boost - 1d) * 100d))))) + invisKey);
            meta.setLore(oldLore);
        }
        item.setItemMeta(meta);
        return item.clone();
    }

    /*/////////////////////////////////////////////
     * UNSAFE METHOD FIX SOMETHING ELSE LATER
     */////////////////////////////////////////////

    /*
    private void startItemUpdater() {
        Main plugin = Main.getPlugin(Main.class);
        if (plugin.isLevelingToolsEnabled()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        UUID uuid = p.getUniqueId();
                        PlayerInventory iv = p.getInventory();
                        for (int i = 0; i < 9; i++) {
                            ItemStack item = iv.getItem(i);
                            if (item != null) {
                                if (item.getType().name().toLowerCase().contains("pick")) {
                                    List<Orb> orbs = getItemOrbs(item);
                                    if (orbs.size() > 0) {
                                        if (plugin.isAutoMineEnabled()) {
                                            if (com.coding.sirjavlux.automine.core.Main.getPlugin(com.coding.sirjavlux.automine.core.Main.class).getAutoMineManager().isMining(uuid)) {
                                                continue;
                                            }
                                        }
                                        ItemMeta meta = item.getItemMeta();
                                        if (meta.hasLore()) {
                                            boolean hasKey = false;
                                            for (String str : meta.getLore()) {
                                                if (str.contains(invisKey)) {
                                                    hasKey = true;
                                                    break;
                                                }
                                            }
                                            if (hasKey) continue;
                                        }
                                        updateItemData(item, p, i);
                                    }
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 4, 4);
        }
    }
     */
}
