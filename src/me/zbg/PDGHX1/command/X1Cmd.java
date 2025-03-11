package me.zbg.PDGHX1.command;

import me.zbg.PDGHX1.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;

import me.zbg.PDGHX1.manager.*;
import me.zbg.PDGHX1.command.args.*;
import org.bukkit.*;

public class X1Cmd implements CommandExecutor {
    private final Main main;

    public X1Cmd(final Main main) {
        this.main = main;
        main.getCommand("x1").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage("§3[zBGX1] §aClass (X1Cmd) loaded.");
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(main.config.console);
            return true;
        }
        
        final Player p = (Player) sender;
        final X1Manager x1m = new X1Manager();
        if (args.length < 1) {
            sendHelp(sender);
            return true;
        }
        if (args.length < 2) {
            switch (args[0].toLowerCase()) {
                case "aceitar": {
                    new Aceitar(sender, main, p, x1m);
                    break;
                }
                case "camarote": {
                    new Camarote(sender, main, p, x1m);
                    break;
                }
                case "negar": {
                    new Negar(sender, main);
                    break;
                }
                case "setsaida": {
                    new SetSaida(sender, main, p);
                    break;
                }
                case "setcamarote": {
                    new SetCamarote(sender, main, p);
                    break;
                }
                case "setpos1": {
                    new SetPos1(sender, main, p);
                    break;
                }
                case "setpos2": {
                    new SetPos2(sender, main, p);
                    break;
                }
                default:
                    break;
            }
            return true;
        }
        if (main.getConfig().getString("Locais.Pos1").equalsIgnoreCase("none")) {
            sender.sendMessage(main.config.localNaoSetado);
            return true;
        }
        if (main.getConfig().getString("Locais.Pos2").equalsIgnoreCase("none")) {
            sender.sendMessage(main.config.localNaoSetado);
            return true;
        }
        if (main.getConfig().getString("Locais.Saida").equalsIgnoreCase("none")) {
            sender.sendMessage(main.config.localNaoSetado);
            return true;
        }
        final String n = args[1];
        final Player t = Bukkit.getPlayer(n);
        if (t == null) {
            sender.sendMessage(main.config.offline);
            return true;
        }
        if (t == p) {
            return true;
        }
        if (X1Manager.solicitacao.containsKey(t.getName())) {
            sender.sendMessage(main.config.jaTem);
            return true;
        }
        if (X1Manager.ocorrendo) {
            sender.sendMessage(main.config.jaOcorrendo);
            return true;
        }
        final int money = main.config.money;
        if (!main.econ.has((OfflinePlayer) p, (double) money) || !main.econ.has((OfflinePlayer) t, (double) money)) {
            sender.sendMessage(main.config.semMoney);
            return true;
        }
        Bukkit.getOnlinePlayers().forEach(ps -> main.config.desafiou.forEach(msg -> ps.sendMessage(msg.replace("{player1}", sender.getName()).replace("{player2}", n))));
        t.sendMessage("§3[Ⓧ①] §5✸ §f1 minuto para aceitar ou negar o x1.");
        t.sendMessage("§3[Ⓧ①] §5✸ §fAceite com o comando §a/x1 aceitar");
        t.sendMessage("§3[Ⓧ①] §5✸ §fNegue com o comando §a/x1 negar");
        X1Manager.solicitacao.put(t.getName(), p.getName());

        BukkitTask timeoutTask = Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run() {
                if (X1Manager.solicitacao.containsKey(t.getName())) {
                    X1Manager.solicitacao.remove(t.getName());
                    sender.sendMessage("§3[Ⓧ①]§a " + t.getName() + " §farregou o x1 de§a " + sender.getName() + " §fpois passou o tempo limite de resposta.");
                }
            }
        }, 60 * 20L);

        main.agendarX1Timeout(t.getName(), timeoutTask);
		return false;
    }

    private void sendHelp(final CommandSender sender) {
        sender.sendMessage("§3§lPDGH X1 - Comandos:");
        sender.sendMessage("§2/x1 desafiar <nick> §2-§a- §aDesafia um jogador para x1.");
        sender.sendMessage("§2/x1 aceitar §2-§a- §aAceita o x1 desafiado.");
        sender.sendMessage("§2/x1 negar §2-§a- §aRecusa o x1 desafiado.");
        sender.sendMessage("§2/x1 camarote §2-§a- §aTeleporta para o camarote do x1.");
        if (sender.hasPermission("pdgh.diretor")) {
            sender.sendMessage("§4/x1 setpos1 §4-§c- §cSeta a posição 1 do x1");
            sender.sendMessage("§4/x1 setpos2 §4-§c- §cSeta a posição 2 do x1");
            sender.sendMessage("§4/x1 setsaida §4-§c- §cSeta a saída do x1");
            sender.sendMessage("§4/x1 setcamarote §4-§c- §cSeta o camarote do x1");
        }
    }
}