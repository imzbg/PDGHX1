package me.zbg.PDGHX1.command.args;

import org.bukkit.command.*;
import me.zbg.PDGHX1.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class SetSaida
{
    public SetSaida(final CommandSender sender, final Main main, final Player p) {
        if (!sender.hasPermission("pdgh.diretor")) {
            sender.sendMessage(main.config.perm);
            return;
        }
        final Location loc2 = p.getLocation();
        final String loc3 = String.valueOf(String.valueOf(String.valueOf(loc2.getWorld().getName()))) + ";" + loc2.getX() + ";" + loc2.getY() + ";" + loc2.getZ();
        main.getConfig().set("Locais.Saida", (Object)loc3);
        main.saveConfig();
        p.sendMessage(main.config.localSetado);
    }
}
