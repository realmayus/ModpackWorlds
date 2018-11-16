package net.mayus.modpackworlds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = (Player)commandSender;

        if(p.hasPermission("ModpackWorlds.SetSpawn")) {
            Config.setSpawn(p.getLocation());

            p.sendMessage("§aSpawn gesetzt.");
        }

        return false;
    }
}
