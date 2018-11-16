package net.mayus.modpackworlds.tpa;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptTpaCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if(strings.length == 1) {
            AcceptTpa.acceptTpaByPlayer(p, strings[0]);
        } else {
            p.sendMessage("§cUsage: /tpaccept <Player>");
        }



        return true;
    }
}
