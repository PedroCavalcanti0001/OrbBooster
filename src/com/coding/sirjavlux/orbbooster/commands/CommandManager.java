package com.coding.sirjavlux.orbbooster.commands;

import com.coding.sirjavlux.orbbooster.core.Main;
import com.coding.sirjavlux.orbbooster.orbs.Orb;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //help command
        Main plugin = Main.getPlugin(Main.class);
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players may use this command!");
                return true;
            }
            Player p = (Player) sender;
            if (p.getInventory().getItemInMainHand().getType().name().toLowerCase().contains("pick"))
                plugin.getOrbGUIManager().getOrbGUI(p).openInventory();
        } else {
            String definer = args[0].toUpperCase();
            switch (definer) {
                case "GIVE":
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        if (!p.hasPermission("orbbooster.give")) {
                            sender.sendMessage(ChatColor.RED + "You didn't have permission to use this command!");
                            return true;
                        }
                    }
                    if (args.length == 3) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(ChatColor.RED + "Only players may use this command!");
                            return true;
                        }
                        Player target = (Player) sender;
                        Orb orb = plugin.getOrbManager().getOrb(args[1]);
                        if (orb == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidorb.replaceAll("%arg%", args[1])));
                            return true;
                        }
                        int count = 1;
                        try {
                            count = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidnumber.replaceAll("%arg%", args[2])));
                            return true;
                        }
                        for (int i = 0; i < count; i++) plugin.getOrbManager().giveOrbBoxToPlayer(target, orb);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().recievegive.replaceAll("%count%", count + "").replaceAll("%orb-name%", orb.getDisplayNameBox())));
                    } else if (args.length > 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidtarget.replaceAll("%arg%", args[1])));
                            return true;
                        }
                        Orb orb = plugin.getOrbManager().getOrb(args[2]);
                        if (orb == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidorb.replaceAll("%arg%", args[2])));
                            return true;
                        }
                        int count = 1;
                        try {
                            count = Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidnumber.replaceAll("%arg%", args[3])));
                            return true;
                        }
                        for (int i = 0; i < count; i++) plugin.getOrbManager().giveOrbBoxToPlayer(target, orb);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().recievegive.replaceAll("%count%", count + "").replaceAll("%orb-name%", orb.getDisplayNameBox())));
                        if (sender instanceof Player) {
                            if (target.getUniqueId() == ((Player) sender).getUniqueId()) return true;
                        }
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().boxgave.replaceAll("%target%", target.getName()).replaceAll("%count%", count + "").replaceAll("%orb-name%", orb.getDisplayNameBox())));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().invalidargsgive));
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().argsnotvalid.replaceAll("%arg%", definer)));
                    break;
            }
        }
        return true;
    }

}
