package me.zbg.PDGHX1.command.args;

import org.bukkit.command.*;
import me.zbg.PDGHX1.*;
import org.bukkit.entity.*;
import me.zbg.PDGHX1.manager.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import org.bukkit.*;

public class Aceitar
{
    public Aceitar(final CommandSender sender, final Main main, final Player p, final X1Manager x1m) {
        if (X1Manager.solicitacao.size() <= 0) {
            sender.sendMessage(main.config.semDuelo);
            return;
        }
        if (!X1Manager.solicitacao.containsKey(sender.getName())) {
            sender.sendMessage(main.config.semDueloPlayer);
            return;
        }
        final String n = X1Manager.solicitacao.get(sender.getName());
        final Player t = Bukkit.getPlayer(n);
        if (t == null) {
            sender.sendMessage(main.config.offline);
            X1Manager.solicitacao.remove(sender.getName());
            return;
        }
        X1Manager.solicitacao.clear();
        Bukkit.getOnlinePlayers().forEach(ps -> main.config.aceitou.forEach(msg -> ps.sendMessage(msg.replace("{player1}", sender.getName()).replace("{player2}", n))));
        final Location pos1 = x1m.getDeserializedLocation(main.getConfig().getString("Locais.Pos1"));
        final Location pos2 = x1m.getDeserializedLocation(main.getConfig().getString("Locais.Pos2"));
        p.teleport(pos1);
        t.teleport(pos2);
        main.econ.withdrawPlayer((OfflinePlayer)p, (double)main.config.money);
        main.econ.withdrawPlayer((OfflinePlayer)t, (double)main.config.money);
        X1Manager.duelo.add(p.getName());
        X1Manager.duelo.add(n);
        X1Manager.liberado = false;
        X1Manager.ocorrendo = true;
        X1Manager.ativarClanFF(p);
        X1Manager.ativarClanFF(t);
        new BukkitRunnable() {
            int countdown = 10;
            
            public void run() {
                if (this.countdown > 0) {
                    p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.NOTE_PLING, 10.0f, 1.0f);
                    t.getPlayer().playSound(t.getPlayer().getLocation(), Sound.NOTE_PLING, 10.0f, 1.0f);
                    p.sendMessage("§3[Ⓧ①] §fo x1 começa em §a" + this.countdown + " §fsegundos.");
                    t.sendMessage("§3[Ⓧ①] §fo x1 começa em §a" + this.countdown + " §fsegundos.");
                    --this.countdown;
                }
                else {
                    p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_LAND, 10.0f, 1.0f);
                    t.getPlayer().playSound(t.getPlayer().getLocation(), Sound.ANVIL_LAND, 10.0f, 1.0f);
                    X1Manager.liberado = true;
                    p.sendMessage(main.config.liberado);
                    t.sendMessage(main.config.liberado);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)main, 0L, 20L);
    }
}
