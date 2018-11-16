package net.mayus.modpackworlds.tpa;


import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SendTpa {

    public static HashMap<String, String> tpaMap = new HashMap<>();


    public static void sendTpaToPlayer(Player s, Player p) {

        //s = sender, p = requested Player

        if(p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
            s.sendMessage("§aYou sent §6" + p.getName() + " §aa teleport request successfully!");
            p.sendMessage("§aYou just received a teleport request from §6" + s.getName() + "§a!");


            //Accept & Decline Buttons for receiver
            TextComponent message1 = new TextComponent( "[§aACCEPT§r] " );
            message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpaccept " + s.getName()) );
            TextComponent message2 = new TextComponent( "[§cDECLINE§r]" );
            message2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpdecline " + s.getName()) );
            message1.addExtra(message2);
            p.spigot().sendMessage(message1);

            tpaMap.put(s.getName(), p.getName());
        } else {
            if(s.getGameMode() == GameMode.SURVIVAL) {
                s.sendMessage("§cWarning: This teleport would not be safe!");
            } else {
                s.sendMessage("§aYou sent §6" + p.getName() + " §aa teleport request successfully!");
                p.sendMessage("§aYou just received a teleport request from §6" + s.getName() + "§a!");


                //Accept & Decline Buttons for receiver
                TextComponent message1 = new TextComponent( "[§aACCEPT§r] " );
                message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpaccept " + s.getName()) );
                TextComponent message2 = new TextComponent( "[§cDECLINE§r]" );
                message2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpdecline " + s.getName()) );
                message1.addExtra(message2);
                p.spigot().sendMessage(message1);

                tpaMap.put(s.getName(), p.getName());
            }

        }




    }


}
