package me.zbg.PDGHX1.manager;

import java.util.*;
import me.zbg.PDGHX1.*;
import org.bukkit.configuration.*;

public class ConfigManager
{
    public String console;
    public String perm;
    public String semDuelo;
    public String semDueloPlayer;
    public String localNaoSetado;
    public String naoOcorrendo;
    public String teleportado;
    public String localSetado;
    public String aguarde;
    public String offline;
    public String jaOcorrendo;
    public String liberado;
    public String colete;
    public String jaTem;
    public String semMoney;
    public List<String> desafiou;
    public List<String> recusou;
    public List<String> aceitou;
    public List<String> ganhou;
    public int money;
    
    public ConfigManager() {
        this.desafiou = new ArrayList<String>();
        this.recusou = new ArrayList<String>();
        this.aceitou = new ArrayList<String>();
        this.ganhou = new ArrayList<String>();
    }
    
    public ConfigManager setupMensagens(final Main main) {
        final ConfigManager config = main.config;
        final ConfigurationSection mensagens = main.getConfig().getConfigurationSection("Mensagens");
        config.console = mensagens.getString("Console").replace('&', '§');
        config.perm = mensagens.getString("Perm").replace('&', '§');
        config.semDuelo = mensagens.getString("Sem duelo").replace('&', '§');
        config.semDueloPlayer = mensagens.getString("Sem duelo Player").replace('&', '§');
        config.localNaoSetado = mensagens.getString("Local nao setado").replace('&', '§');
        config.naoOcorrendo = mensagens.getString("Nao ocorrendo").replace('&', '§');
        config.teleportado = mensagens.getString("Teleportado").replace('&', '§');
        config.localSetado = mensagens.getString("Local setado").replace('&', '§');
        config.aguarde = mensagens.getString("Aguarde").replace('&', '§');
        config.offline = mensagens.getString("Offline").replace('&', '§');
        config.jaOcorrendo = mensagens.getString("Ja ocorrendo").replace('&', '§');
        config.liberado = mensagens.getString("Liberado").replace('&', '§');
        config.colete = mensagens.getString("Colete").replace('&', '§');
        config.jaTem = mensagens.getString("Ja tem").replace('&', '§');
        config.semMoney = mensagens.getString("Sem money").replace('&', '§');
        config.money = main.getConfig().getInt("Money");
        mensagens.getStringList("Desafiou").forEach(msg -> config.desafiou.add(msg.replace('&', '§')));
        mensagens.getStringList("Recusou").forEach(msg -> config.recusou.add(msg.replace('&', '§')));
        mensagens.getStringList("Aceitou").forEach(msg -> config.aceitou.add(msg.replace('&', '§')));
        mensagens.getStringList("Ganhou").forEach(msg -> config.ganhou.add(msg.replace('&', '§')));
        return this;
    }
}
