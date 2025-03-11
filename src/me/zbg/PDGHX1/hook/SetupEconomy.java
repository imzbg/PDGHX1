package me.zbg.PDGHX1.hook;

import me.zbg.PDGHX1.*;
import org.bukkit.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.plugin.*;

public class SetupEconomy
{
    public SetupEconomy(final Main main) {
        if (main.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("§3[zBGX1] §aPlugin (Vault) não encontrado. Desabilitando plugin...");
            main.getPluginLoader().disablePlugin((Plugin)main);
            return;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)main.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            Bukkit.getConsoleSender().sendMessage("§3[zBGX1] §aPlugin de Economia não encontrado. Desabilitando plugin...");
            main.getPluginLoader().disablePlugin((Plugin)main);
            return;
        }
        Bukkit.getConsoleSender().sendMessage("§3[zBGX1] §aPlugin (Vault e Economia) encontrado. Realizando Hook...");
        main.econ = (Economy)rsp.getProvider();
    }
}
