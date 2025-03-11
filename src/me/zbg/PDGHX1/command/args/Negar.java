package me.zbg.PDGHX1.command.args;

import org.bukkit.command.*;
import me.zbg.PDGHX1.*;
import me.zbg.PDGHX1.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class Negar {
    public Negar(final CommandSender sender, final Main main) {
        if (X1Manager.solicitacao.size() <= 0) {
            sender.sendMessage(main.config.semDuelo);
            return;
        }
        if (!X1Manager.solicitacao.containsKey(sender.getName())) {
            sender.sendMessage(main.config.semDueloPlayer);
            return;
        }

        if (X1Manager.tarefasDeTimeout.containsKey(sender.getName())) {
            Bukkit.getScheduler().cancelTask(X1Manager.tarefasDeTimeout.get(sender.getName()));
            X1Manager.tarefasDeTimeout.remove(sender.getName());
        }

        final String n2 = X1Manager.solicitacao.get(sender.getName());
        X1Manager.solicitacao.remove(sender.getName());
        Bukkit.getOnlinePlayers().forEach(ps -> main.config.recusou.forEach(msg -> ps.sendMessage(msg.replace("{player1}", sender.getName()).replace("{player2}", n2))));
    }
}
