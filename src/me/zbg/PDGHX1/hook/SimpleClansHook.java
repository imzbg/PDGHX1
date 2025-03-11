package me.zbg.PDGHX1.hook;

import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.Bukkit;


public class SimpleClansHook {
	public static SimpleClans sc;
    public static void hookSimpleClans() {
        if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null) {
            SimpleClansHook.sc = (SimpleClans)Bukkit.getServer().getPluginManager().getPlugin("SimpleClans");
        }
    }
}
