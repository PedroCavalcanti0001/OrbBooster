package com.coding.sirjavlux.orbbooster.listener;

import com.coding.sirjavlux.orbbooster.core.Main;
import com.coding.sirjavlux.orbbooster.orbs.Orb;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void leaveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Main.getPlugin(Main.class).getOrbGUIManager().removeOrbGUI(uuid);
    }

    @EventHandler
    public void dropEvent(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType().name().toLowerCase().contains("pick")) {
            Main plugin = Main.getPlugin(Main.class);
            item = plugin.getOrbManager().updateItemData(item);
            e.getItemDrop().setItemStack(item);
        }
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack current = e.getItem();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Main plugin = Main.getPlugin(Main.class);

            /*//////////////////////////////
             * OPENING ORB BOX
             *//////////////////////////////

            if (plugin.getOrbManager().isOrbBoxItem(current)) {
                e.setCancelled(true);
                Orb orb = plugin.getOrbManager().getOrbFromBoxItem(current);
                if (orb != null) {
                    double destChance = orb.getChanceBreak();
                    if (current.getAmount() > 1) current.setAmount(current.getAmount() - 1);
                    else current.setType(Material.AIR);
                    p.getInventory().setItem(p.getInventory().getHeldItemSlot(), current.clone());
                    if (Math.random() <= destChance) {
                        plugin.getOrbManager().giveOrbDestroyedToPlayer(p, orb);
                    } else {
                        plugin.getOrbManager().giveOrbToPlayer(p, orb);
                    }
                }
            }

            /*//////////////////////////////
             * CRAFTING ORB BOX
             *//////////////////////////////

            else if (plugin.getOrbManager().isOrbDestroyedItem(current)) {
                Orb orb = plugin.getOrbManager().getOrbFromDestroyedItem(current);
                if (orb != null) {
                    int craftAmount = orb.getCraftAmount();
                    if (current.getAmount() >= craftAmount) {
                        e.setCancelled(true);
                        if (current.getAmount() > craftAmount) current.setAmount(current.getAmount() - craftAmount);
                        else current.setType(Material.AIR);
                        p.getInventory().setItem(p.getInventory().getHeldItemSlot(), current.clone());
                        plugin.getOrbManager().giveOrbBoxToPlayer(p, orb);
                    }
                }
            }
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item != null) {
            if (item.getType().name().toLowerCase().contains("pick")) {
                net.minecraft.server.v1_12_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tagComp = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
                int blocksBroken = 1 + (tagComp.hasKey("blocks-broken") ? tagComp.getInt("blocks-broken") : 0);
                tagComp.setInt("blocks-broken", blocksBroken);
                NMSItem.setTag(tagComp);
                item = CraftItemStack.asBukkitCopy(NMSItem);
                p.getInventory().setItemInMainHand(item);
            }
        }
    }
}
