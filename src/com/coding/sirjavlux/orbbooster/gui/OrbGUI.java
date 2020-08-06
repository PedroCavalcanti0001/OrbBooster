package com.coding.sirjavlux.orbbooster.gui;

import com.coding.sirjavlux.orbbooster.core.Main;
import com.coding.sirjavlux.orbbooster.orbs.Orb;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class OrbGUI {

    public final static String ivName = ChatColor.translateAlternateColorCodes('&', "&8Orb Menu");

    private ItemStack fillBlack, fillGray, fillEmpty, emptyOrb;
    private Player p;
    private Inventory iv;
    private UUID target;
    private int maxSlots;

    public OrbGUI(Player p) {
        this.p = p;
        this.target = p.getUniqueId();
        generateItems();
        creatInventory();
        updateInventory(p.getInventory().getItemInMainHand());
    }

    private void generateItems() {
        Main plugin = Main.getPlugin(Main.class);
        //gray fill item
        fillGray = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
        ItemMeta fillGrayM = fillGray.getItemMeta();
        fillGrayM.setDisplayName(" ");
        fillGrayM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fillGray.setItemMeta(fillGrayM);
        //black fill item
        fillBlack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta fillBlackM = fillBlack.getItemMeta();
        fillBlackM.setDisplayName(" ");
        fillBlackM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fillBlack.setItemMeta(fillBlackM);
        //fill empty
        fillEmpty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 8);
        ItemMeta fillEmptyM = fillEmpty.getItemMeta();
        fillEmptyM.setDisplayName(" ");
        fillEmptyM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fillEmpty.setItemMeta(fillEmptyM);
        //empty orb slot
        emptyOrb = new ItemStack(plugin.getConfigManager().getMenu().getEmptyMaterial());
        ItemMeta emptyM = emptyOrb.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String str : plugin.getConfigManager().getMenu().getEmptyLore())
            lore.add(ChatColor.translateAlternateColorCodes('&', str));
        emptyM.setLore(lore);
        emptyM.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMenu().getEmptyDisplayName()));
        emptyM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        emptyOrb.setItemMeta(emptyM);
    }

    private void creatInventory() {
        updateMaxSlots();
        double nr = ((double) maxSlots) / 7d;
        int rows = (int) nr;
        if (nr - rows > 0) rows++;
        int ivSize = 18 + rows * 9;
        this.iv = Bukkit.createInventory(p, ivSize, ChatColor.translateAlternateColorCodes('&', ivName));
    }

    public void updateInventory(ItemStack item) {
        //get data
        Main plugin = Main.getPlugin(Main.class);
        List<Orb> orbs = plugin.getOrbManager().getItemOrbs(item);
        List<Double> boosts = plugin.getOrbManager().getItemBoosts(item);
        List<Double> chances = plugin.getOrbManager().getItemChances(item);

        //set items default items
        for (int i = 0; i < iv.getSize(); i++) {
            iv.setItem(i, fillGray);
        }

        HashMap<Integer, Integer> slotMap = generateSlotMap();
        for (Entry<Integer, Integer> entry : slotMap.entrySet()) {
            int vSlot = entry.getKey();
            int orbSlot = entry.getValue();
            if (orbs.size() > orbSlot) {
                iv.setItem(vSlot, plugin.getOrbManager().generateOrbItem(orbs.get(orbSlot), boosts.get(orbSlot), chances.get(orbSlot)));
            } else {
                iv.setItem(vSlot, emptyOrb);
            }
        }
    }

    public HashMap<Integer, Integer> generateSlotMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        updateMaxSlots();
        double nr = ((double) maxSlots) / 7d;
        int rows = (int) nr;
        if (nr - rows > 0) rows++;
        int slot = 0;
        for (int i = 0; i < rows; i++) {
            int rowAmount = (int) (((double) maxSlots) - ((double) i) * 7d);
            int rowChange = (int) (maxSlots - (i + 1) * 7 >= 7 ? 0 : (7 - rowAmount) / 2);
            for (int i2 = 0; i2 < 7; i2++) {
                if (i2 + 1 > rowChange
                        && i2 < rowAmount + rowChange) {
                    map.put((i + 1) * 9 + 1 + i2, slot);
                    slot++;
                }
            }
        }
        return map;
    }

    public void updateMaxSlots() {
        Main plugin = Main.getPlugin(Main.class);
        ItemStack item = p.getInventory().getItemInMainHand();
        List<Orb> orbs = plugin.getOrbManager().getItemOrbs(item);
        maxSlots = plugin.getConfigManager().getSettings().getMaxSlots() < orbs.size() ? orbs.size() : plugin.getConfigManager().getSettings().getMaxSlots();
        maxSlots = maxSlots > 28 ? 28 : maxSlots;
    }

    public void openInventory() {
        creatInventory();
        updateInventory(p.getInventory().getItemInMainHand());
        if (p.isOnline()) p.openInventory(iv);
    }

    public UUID getTargetUniqueId() {
        return target;
    }

    public Player getPlayer() {
        return p;
    }

    public Inventory getInventory() {
        return iv;
    }
}