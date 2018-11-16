package net.mayus.modpackworlds;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IslandCommands implements CommandExecutor {


    //Receiver, Sender
    public static HashMap<Player, Player> residentRequests = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("create")) {
                if(!Config.hasAlreadyAWorld(p.getUniqueId().toString()) && !Config.getUIDListOfLimitedPlayers().contains(p.getUniqueId().toString())) {
                    Config.createIsland(p);
                    p.teleport(Bukkit.getWorld(Config.getWorldOfOwner(p.getUniqueId().toString())).getSpawnLocation());
                    Config.addLimitedPlayer(p.getUniqueId().toString());
                } else {
                    p.sendMessage("§cDu hast bereits eine Insel/bist Bewohner von einer Insel!");
                }
            } else if(args[0].equalsIgnoreCase("tp")) {

                //Wenn Resident
                if(Config.getWorldOfOwner(p.getUniqueId().toString()) == null) {
                    p.teleport(Bukkit.getWorld(Config.getWorldOfResident(p.getUniqueId().toString())).getSpawnLocation());
                    //Wenn Owner
                } else if(Config.getWorldOfResident(p.getUniqueId().toString()) == null) {
                    p.teleport(Bukkit.getWorld(Config.getWorldOfOwner(p.getUniqueId().toString())).getSpawnLocation());
                } else {
                    p.sendMessage("§cDu hast keine Welt!");
                }
            } else if(args[0].equalsIgnoreCase("remove")) {

                p.sendMessage("Möchtest Du wirklich deine Welt §clöschen§a? Dies kann §cNICHT §rrückgängig gemacht werden!");
                TextComponent message1 = new TextComponent( "[§aJa§r] " );
                message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/reallydeleteworld"));
                TextComponent message2 = new TextComponent( "[§cAblehnen§r]" );
                message1.addExtra(message2);
                p.spigot().sendMessage(message1);

            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("addresident")) {
                if(Bukkit.getPlayer(args[1]) != null) {

                    Player rec = Bukkit.getPlayer(args[1]);
                    if(!Config.getUIDListOfLimitedPlayers().contains(rec.getUniqueId().toString())) {
                        residentRequests.put(rec, p);
                        rec.sendMessage("§aDer Spieler §6" + p.getName() + " §amöchte dich als Mitbewohner werben.");
                        TextComponent message1 = new TextComponent( "[§aAnnehmen§r] " );
                        message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/acceptres " + p.getName()) );
                        TextComponent message2 = new TextComponent( "[§cAblehnen§r]" );
                        message2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/declineres " + p.getName()) );
                        message1.addExtra(message2);
                        rec.spigot().sendMessage(message1);
                    } else {
                        p.sendMessage("§cDer Spieler hat bereits eine Welt/ Ist bereits Bewohner einer Insel.");
                    }



                } else {
                    p.sendMessage("§cDieser Spieler ist nicht online!");
                }
            } else if(args[0].equalsIgnoreCase("removeresident")) {
                if(Bukkit.getPlayer(args[1]) != null) {
                    Config.removeResident(Config.getWorldOfPlayer(p.getUniqueId().toString()), Bukkit.getPlayer(args[1]).getUniqueId().toString());
                    Bukkit.getPlayer(args[1]).teleport(Config.getSpawn());
                    Bukkit.getPlayer(args[1]).sendMessage("§cDu wurdest aus dem Team geworfen!");
                    Config.removeLimitedPlayer(Bukkit.getPlayer(args[1]).getUniqueId().toString());
                } else {
                    p.sendMessage("§cDieser Spieler ist nicht online!");
                }
            }
        }


        return true;
    }
}
