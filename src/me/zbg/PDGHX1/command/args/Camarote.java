package me.zbg.PDGHX1.command.args;

import org.bukkit.command.*;
import me.zbg.PDGHX1.*;
import org.bukkit.entity.*;
import me.zbg.PDGHX1.manager.*;
import org.bukkit.*;

public class Camarote
{
    public Camarote(final CommandSender sender, final Main main, final Player p, final X1Manager x1m) {
        if (main.getConfig().getString("Locais.Camarote").equalsIgnoreCase("none")) {
            sender.sendMessage(main.config.localNaoSetado);
            return;
        }
        if (!X1Manager.ocorrendo) {
            sender.sendMessage(main.config.naoOcorrendo);
            return;
        }
        final Location camarote = x1m.getDeserializedLocation(main.getConfig().getString("Locais.Camarote"));
        p.teleport(camarote);
        p.sendMessage(main.config.teleportado);
    }
}
