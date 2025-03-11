package me.zbg.PDGHX1.manager;

import org.bukkit.entity.*;

import me.zbg.PDGHX1.hook.SimpleClansHook;

import java.util.*;
import org.bukkit.*;

public class X1Manager {
    public static boolean ocorrendo;
    public static boolean liberado;
    public static Player coletando;
    public static ArrayList<String> duelo;
    public static HashMap<String, String> solicitacao;
    public static HashMap<String, Integer> tarefasDeTimeout;

    static {
        X1Manager.ocorrendo = false;
        X1Manager.liberado = false;
        X1Manager.coletando = null;
        X1Manager.duelo = new ArrayList<String>();
        X1Manager.solicitacao = new HashMap<String, String>();
        X1Manager.tarefasDeTimeout = new HashMap<String, Integer>();
    }
    
    public Location getDeserializedLocation(final String paramString) {
        if (paramString.equalsIgnoreCase("none")) {
            return null;
        }
        final String[] arrayOfString = paramString.split(";");
        final World world = Bukkit.getServer().getWorld(arrayOfString[0]);
        final double d1 = Double.parseDouble(arrayOfString[1]);
        final double d2 = Double.parseDouble(arrayOfString[2]);
        final double d3 = Double.parseDouble(arrayOfString[3]);
        return new Location(world, d1, d2, d3);
    }
    public static void desativarClanFF(final Player p) {
        if (Bukkit.getPluginManager().getPlugin("SimpleClans") != null && SimpleClansHook.sc.getClanManager().getClanPlayer(p) != null) {
            SimpleClansHook.sc.getClanManager().getClanPlayer(p).setFriendlyFire(false);
        }
    }
    
    public static void ativarClanFF(final Player p) {
        if (Bukkit.getPluginManager().getPlugin("SimpleClans") != null && SimpleClansHook.sc.getClanManager().getClanPlayer(p) != null) {
        	SimpleClansHook.sc.getClanManager().getClanPlayer(p).setFriendlyFire(true);
        }
    }
}