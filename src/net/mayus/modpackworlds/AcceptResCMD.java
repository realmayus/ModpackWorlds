package net.mayus.modpackworlds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptResCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;
        if(args.length == 1) {
            if(IslandCommands.residentRequests.containsKey(p)) {
                if(IslandCommands.residentRequests.get(p).getName().equalsIgnoreCase(args[0])) {
                    IslandCommands.residentRequests.remove(p);
                    Config.addResident(Config.getWorldOfOwner(Bukkit.getPlayer(args[0]).getUniqueId().toString()), p.getUniqueId().toString());
                    Config.addLimitedPlayer(p.getUniqueId().toString());
                    p.sendMessage("§aDu hast die Anfrage angenommen!");
                    p.teleport(Bukkit.getWorld(Config.getWorldOfOwner(Bukkit.getPlayer(args[0]).getUniqueId().toString())).getSpawnLocation());
                } else {
                    p.sendMessage("§cDu hast keine Anfrage von diesem Spieler!");
                }
            } else {
                p.sendMessage("§cDu hast keine Anfragen!");
            }
        } else {
            p.sendMessage("§cBenutzung: /acceptres <Name des Inselowners>");
        }


        return true;
    }
}
