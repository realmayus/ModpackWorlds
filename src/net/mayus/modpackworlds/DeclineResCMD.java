package net.mayus.modpackworlds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeclineResCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;
        if(args.length == 1) {
            if(IslandCommands.residentRequests.containsKey(p)) {
                if(IslandCommands.residentRequests.get(p).getName().equalsIgnoreCase(args[0])) {
                    IslandCommands.residentRequests.remove(p);
                    p.sendMessage("§aDu hast die Anfrage §cabgelehnt!");
                    Bukkit.getPlayer(args[0]).sendMessage("§6" + p.getName() + " §chat deine Anfrage abgelehnt!");

                } else {
                    p.sendMessage("§cDu hast keine Anfrage von diesem Spieler!");
                }
            } else {
                p.sendMessage("§cDu hast keine Anfragen!");
            }
        } else {
            p.sendMessage("§cBenutzung: /declineres <Name des Inselowners>");
        }


        return true;
    }

}
