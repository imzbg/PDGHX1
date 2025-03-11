package me.zbg.PDGHX1;

import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.BukkitTask;

import net.milkbowl.vault.economy.*;
import java.io.*;
import org.bukkit.*;
import me.zbg.PDGHX1.manager.*;
import me.zbg.PDGHX1.command.*;
import me.zbg.PDGHX1.listener.*;
import me.zbg.PDGHX1.hook.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin
{
    public ConfigManager config;
    public Economy econ;
    
    public Main() {
        this.config = new ConfigManager();
    }
    
    public void onEnable() {
    	SimpleClansHook.hookSimpleClans();
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
        getServer().getConsoleSender().sendMessage("§3[PDGHX1] §2ativado - Plugin by: zbg");
		getServer().getConsoleSender().sendMessage("§3[PDGHX1] §2Acesse: http://pdgh.com.br/");
        this.register();
    }
    
    public void onDisable() {
        X1Manager.duelo.clear();
        X1Manager.solicitacao.clear();
        getServer().getConsoleSender().sendMessage("§3[PDGHX1] §2desativado - Plugin by: zbg");
		getServer().getConsoleSender().sendMessage("§3[PDGHX1] §2Acesse: http://pdgh.com.br/");
    }
    
    private void register() {
        new X1Cmd(this);
        new PlayerListeners(this);
        new SetupEconomy(this);
        this.config.setupMensagens(this);
    }
    private final Map<String, BukkitTask> x1TimeoutTasks = new HashMap<>();

    public void agendarX1Timeout(String playerName, BukkitTask task) {
        x1TimeoutTasks.put(playerName, task);
    }

    public void cancelarX1Timeout(String playerName) {
        if (x1TimeoutTasks.containsKey(playerName)) {
            BukkitTask task = x1TimeoutTasks.get(playerName);
            task.cancel();
            x1TimeoutTasks.remove(playerName);
        }
    }

}