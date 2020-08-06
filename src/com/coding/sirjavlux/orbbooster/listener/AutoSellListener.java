package com.coding.sirjavlux.orbbooster.listener;

import com.coding.sirjavlux.orbbooster.core.Main;
import me.clip.autosell.multipliers.Multipliers;
import me.clip.autosell.objects.Multiplier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AutoSellListener implements Listener {

//	@EventHandler
//	public void autoSellEvent(AutoSellEvent e) {
//		Player p = e.getPlayer();
//		Main plugin = Main.getPlugin(Main.class);
//		PlayerInventory iv = p.getInventory();
//		ItemStack itemHeld = iv.getItemInMainHand();
//		List<Double> boosts = plugin.getOrbManager().getItemBoosts(itemHeld);
//		double boost = 1;
//		for (double boostT : boosts) boost *= boostT;
//		e.setMultiplier(e.getMultiplier() * boost);
//	}

    public static void updateMultiplier(ItemStack item, Player p) {
        if (item != null) {
            if (item.getType().name().toLowerCase().contains("pick")) {
                Main plugin = Main.getPlugin(Main.class);
                List<Double> boosts = plugin.getOrbManager().getItemBoosts(item);
                double boost = 1;
                for (double boostT : boosts) boost *= boostT;
                boost = (double) Math.round((boost * 100d)) / 100d;
                Multiplier m = Multipliers.getMultiplierByUuid(p.getUniqueId().toString());
                if (m == null) {
                    m = new Multiplier(p.getUniqueId().toString(), p.getName(), 0, boost, true);
                    Multipliers.addMultiplier(m);
                }
                m.setMultiplier(boost);
            }
        }
    }

    @EventHandler
    public void swapHandEvent(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItem(e.getNewSlot());
        updateMultiplier(item, p);
    }
}