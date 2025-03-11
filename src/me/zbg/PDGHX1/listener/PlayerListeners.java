package me.zbg.PDGHX1.listener;

import me.zbg.PDGHX1.*;

import org.bukkit.plugin.*;
import me.zbg.PDGHX1.manager.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.player.*;

public class PlayerListeners implements Listener
{
    public final Main main;
    public PlayerListeners(final Main main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)main);
        Bukkit.getConsoleSender().sendMessage("§3[zBGX1] §aClass (PlayerListeners) loaded.");
    }
    
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if ((X1Manager.duelo.contains(p.getName()) || X1Manager.coletando == p) && !e.getMessage().split(" ")[0].equalsIgnoreCase("/g") && !e.getMessage().split(" ")[0].equalsIgnoreCase("/global") && !e.getMessage().split(" ")[0].equalsIgnoreCase("/bau")) {
            if (!e.getMessage().split(" ")[0].equalsIgnoreCase("/chest")) {
                p.sendMessage("§3[Ⓧ①] §aApenas os comandos §f(/bau, /chest e /g) §asão permitidos no x1");
            }
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (X1Manager.duelo.contains(p.getName()) && !X1Manager.liberado) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (X1Manager.duelo.contains(e.getPlayer().getName()) && !X1Manager.liberado) {
            if (e.getFrom().getX() != e.getTo().getX()) {
                e.getPlayer().teleport(e.getFrom());
            }
            if (e.getFrom().getZ() != e.getTo().getZ()) {
                e.getPlayer().teleport(e.getFrom());
            }
            if (e.getFrom().getY() != e.getTo().getY()) {
                e.getPlayer().teleport(e.getFrom());
            }
        }
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        if (X1Manager.duelo.contains(e.getEntity().getName())) {
            String outro = X1Manager.duelo.get(0);
            if (outro.equalsIgnoreCase(e.getEntity().getName())) {
                outro = X1Manager.duelo.get(1);
            }
            final Player t = Bukkit.getPlayer(outro);
            for (final Player ps : Bukkit.getOnlinePlayers()) {
                for (final String msg : this.main.config.ganhou) {
                    ps.sendMessage(msg.replace("{player1}", outro).replace("{player2}", e.getEntity().getName()));
                    ps.getPlayer().playSound(ps.getPlayer().getLocation(), Sound.WOLF_HOWL, 10.0f, 1.0f);
                }
            }
            X1Manager.desativarClanFF(e.getEntity());
            X1Manager.desativarClanFF(t);
            t.sendMessage(this.main.config.colete);
            this.main.econ.depositPlayer((OfflinePlayer)t, (double)(this.main.config.money * 2));
            X1Manager.duelo.clear();
            X1Manager.coletando = t;
            new BukkitRunnable() {
                public void run() {
                    if (t != null) {
                        final Location saida = new X1Manager().getDeserializedLocation(PlayerListeners.this.main.getConfig().getString("Locais.Saida"));
                        t.teleport(saida);
                    }
                    X1Manager.liberado = false;
                    X1Manager.ocorrendo = false;
                    X1Manager.coletando = null;
                }
            }.runTaskLater((Plugin)this.main, 600L);
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        if (X1Manager.duelo.contains(e.getPlayer().getName())) {
            String outro = X1Manager.duelo.get(0);
            if (outro.equalsIgnoreCase(e.getPlayer().getName())) {
                outro = X1Manager.duelo.get(1);
            }
            final Player t = Bukkit.getPlayer(outro);
            for (final Player ps : Bukkit.getOnlinePlayers()) {
                for (final String msg : this.main.config.ganhou) {
                    ps.sendMessage(msg.replace("{player1}", outro).replace("{player2}", e.getPlayer().getName()));
                    ps.getPlayer().playSound(ps.getPlayer().getLocation(), Sound.WOLF_HOWL, 10.0f, 1.0f);
                    
                }
            }
            X1Manager.desativarClanFF(e.getPlayer());
            X1Manager.desativarClanFF(t);
            t.sendMessage(this.main.config.colete);
            this.main.econ.depositPlayer((OfflinePlayer)t, (double)(this.main.config.money * 2));
            new BukkitRunnable() {
                public void run() {
                    final Location saida = new X1Manager().getDeserializedLocation(PlayerListeners.this.main.getConfig().getString("Locais.Saida"));
                    t.teleport(saida);
                    X1Manager.duelo.clear();
                    X1Manager.liberado = false;
                    X1Manager.ocorrendo = false;
                    X1Manager.coletando = null;
                }
            }.runTaskLater((Plugin)this.main, 600L);
        }
        String playerName = e.getPlayer().getName();
        if (X1Manager.solicitacao.containsKey(playerName)) {
            X1Manager.solicitacao.remove(playerName);
            Bukkit.getOnlinePlayers().forEach(ps ->
                ps.sendMessage("§3[Ⓧ①] §fO jogador§a " + playerName + "§f desconectou e a solicitação de x1 foi cancelada.")
            );
        }
    }
    }