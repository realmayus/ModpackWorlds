package net.mayus.modpackworlds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class removeWorldCMD implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        //Alle Residents von LimitedPlayers entfernen
        if(Config.getResidents(Config.getWorldOfOwner(p.getUniqueId().toString())) != null) {
            for(String uid : Config.getResidents(Config.getWorldOfOwner(p.getUniqueId().toString()))) {
                Config.removeLimitedPlayer(uid);
                for(Player pl : Bukkit.getOnlinePlayers()) {
                    if(pl.getUniqueId().toString().equalsIgnoreCase(uid)) {
                        pl.sendMessage("§cDie Welt, in der du Mitbewohner warst, wurde aufgelöst!");
                        pl.teleport(Config.getSpawn());
                    }
                }
            }
        }

        p.teleport(Config.getSpawn());
        API.unloadWorld(Bukkit.getWorld(Config.getWorldOfOwner(p.getUniqueId().toString())));
        //API.deleteWorld(Bukkit.getWorld(Config.getWorldOfOwner(p.getUniqueId().toString())).getWorldFolder());

        //Welt-ID zum Debuggen ausgeben
        Bukkit.broadcastMessage(Config.getWorldOfOwner(p.getUniqueId().toString()));

        //Pfad der Welt ausgeben
        Bukkit.broadcastMessage(Bukkit.getWorld(Config.getWorldOfOwner(p.getUniqueId().toString())).getWorldFolder().getAbsolutePath());
        //^^^^^^^^ Hieran scheitert's

        //Owner von LimitedPlayers entfernen
        Config.removeLimitedPlayer(p.getUniqueId().toString());
        Config.removeIsland(Config.getWorldOfOwner(p.getUniqueId().toString()));
        return true;
    }





}
