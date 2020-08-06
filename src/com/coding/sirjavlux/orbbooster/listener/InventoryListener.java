package com.coding.sirjavlux.orbbooster.listener;

import com.coding.sirjavlux.orbbooster.api.events.OrbApplyEvent;
import com.coding.sirjavlux.orbbooster.api.events.OrbRemoveEvent;
import com.coding.sirjavlux.orbbooster.core.Main;
import com.coding.sirjavlux.orbbooster.gui.OrbGUI;
import com.coding.sirjavlux.orbbooster.orbs.Orb;
import com.coding.sirjavlux.orbbooster.orbs.OrbManager;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class InventoryListener implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        Inventory iv = e.getClickedInventory();
        if (iv != null && e.getWhoClicked() instanceof Player) {
            Main plugin = Main.getPlugin(Main.class);
            int slot = e.getSlot();
            String title = e.getView().getTitle();
            Player p = (Player) e.getWhoClicked();
            if (title.equals(OrbGUI.ivName)) {

                /*//////////////////////////////
                 * REMOVING ORB FROM ITEM
                 *//////////////////////////////
                e.setCancelled(true);
                ItemStack heldItem = p.getInventory().getItemInMainHand();
                OrbGUI gui = plugin.getOrbGUIManager().getOrbGUI(p);
                HashMap<Integer, Integer> map = gui.generateSlotMap();
                if (map.containsKey(slot)) {
                    ItemStack itemStack = plugin.getOrbManager().removeItemOrb(heldItem, map.get(slot), p, p.getInventory().getHeldItemSlot());
                   if(itemStack == null) return;
                    OrbRemoveEvent orbRemoveEvent = new OrbRemoveEvent(itemStack, p);
                    Bukkit.getServer().getPluginManager().callEvent(orbRemoveEvent);
                    if (!orbRemoveEvent.isCancelled()) {
                        p.getInventory().setItemInMainHand(orbRemoveEvent.getTool());
                        gui.openInventory();
                    }
                }

            } else {
                ItemStack cursor = e.getCursor();
                ItemStack current = e.getCurrentItem();

                /*//////////////////////////////
                 * APPLYING ORB ON PICKAXE
                 *//////////////////////////////

                if (current.getType().name().toLowerCase().contains("pick")
                        && plugin.getOrbManager().isOrbItem(cursor)) {
                    e.setCancelled(true);
                    Orb orb = plugin.getOrbManager().getOrbFromItem(cursor);
                    double chance = plugin.getOrbManager().getChanceFromOrbItem(cursor);
                    if (orb != null) {
                        List<Orb> orbs = plugin.getOrbManager().getItemOrbs(current);
                        int maxOrbs = plugin.getConfigManager().getSettings().getMaxSlots();
                        //deny
                        if (maxOrbs <= orbs.size()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().maxslotsreached));
                            e.setCancelled(false);
                            return;
                        } else {
                            net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(current);
                            int blocks = 0;
                            int blocksNeeded = 0;
                            int maxOrbsSlots = 1;
                            if (NMSItem.hasTag()) {
                                NBTTagCompound tagComp = NMSItem.getTag();
                                if (tagComp.hasKey("blocks-broken")) {
                                    blocks = tagComp.getInt("blocks-broken");
                                }
                            }
                            for (Entry<Integer, Integer> entry : plugin.getConfigManager().getSettings().getSlotRequirementsEzBlocks().entrySet()) {
                                int slotOrb = entry.getKey();
                                int amount = entry.getValue();
                                if (amount > blocks) {
                                    maxOrbsSlots = slotOrb - 1;
                                    blocksNeeded = amount - blocks;
                                    break;
                                }
                            }
                            if (orbs.size() >= maxOrbsSlots) {
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().blocksleft.replaceAll("%blocks%", blocksNeeded + "")));
                                e.setCancelled(false);
                                return;
                            }
                        }
                        //apply orb
                        //if failed

                        ItemStack item = plugin.getOrbManager().addItemOrb(current, cursor.clone(), p);

                        if (Math.random() > chance) {
                            OrbApplyEvent orbApplyEvent = new OrbApplyEvent(item, p, false);
                            Bukkit.getServer().getPluginManager().callEvent(orbApplyEvent);
                            if (!orbApplyEvent.isCancelled()) {
                                p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1, 1);
                                if (cursor.getAmount() > 1) cursor.setAmount(cursor.getAmount() - 1);
                                else cursor.setType(Material.AIR);
                                p.setItemOnCursor(cursor);
                            }
                        }
                        //if success
                        else {
                            OrbApplyEvent orbApplyEvent = new OrbApplyEvent(item, p, true);
                            Bukkit.getServer().getPluginManager().callEvent(orbApplyEvent);
                            if (!orbApplyEvent.isCancelled()) {
                                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                                if (cursor.getAmount() > 1) cursor.setAmount(cursor.getAmount() - 1);
                                else cursor.setType(Material.AIR);
                                p.setItemOnCursor(cursor);
                                AutoSellListener.updateMultiplier(item, p);
                                new OrbManager().updateItemData(item, p, slot);
                                e.setCurrentItem(orbApplyEvent.getTool());
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
