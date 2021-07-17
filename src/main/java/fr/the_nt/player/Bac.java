package fr.the_nt.player;

import net.badlion.heartbeatapi.HeartbeatApi;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class Bac extends Command {
    public Bac(Player player){
        super("bac");
    }

    HeartbeatApi badapi = HeartbeatApi.getInstance();

    public void execute(CommandSender sender, String[] args){
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args[0].equalsIgnoreCase("whitelist")){
                if (args[1].equalsIgnoreCase("add")){
                    ProxiedPlayer temp = ProxyServer.getInstance().getPlayer(args[2]);
                    UUID uuid = temp.getUniqueId();
                    HeartbeatApi.getInstance().addPlayerToWhitelist(uuid);
                }
                if (args[1].equalsIgnoreCase("remove")){
                    ProxiedPlayer temp = ProxyServer.getInstance().getPlayer(args[2]);
                    UUID uuid = temp.getUniqueId();
                    HeartbeatApi.getInstance().removePlayerFromWhitelist(uuid);
                }
                if (args[1].equalsIgnoreCase("list")){
                    HeartbeatApi.getInstance().getWhitelistedPlayers();
                }
            }
            if (args[0].equalsIgnoreCase("use")){
                ProxiedPlayer temp = ProxyServer.getInstance().getPlayer(args[1]);
                UUID uuid = temp.getUniqueId();
                HeartbeatApi.getInstance().isPlayerUsingBadlionAnticheat(uuid);
            }
        }
    }
}
