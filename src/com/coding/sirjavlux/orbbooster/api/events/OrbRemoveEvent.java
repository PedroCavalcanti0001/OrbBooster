package com.coding.sirjavlux.orbbooster.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class OrbRemoveEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private ItemStack tool;
    private Player player;
    private boolean cancelled;


    public OrbRemoveEvent(ItemStack tool, Player player) {
        this.tool = tool;
        this.player = player;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ItemStack getTool() {
        return tool;
    }

    public void setTool(ItemStack tool) {
        this.tool = tool;
    }

    public Player getPlayer() {
        return player;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

