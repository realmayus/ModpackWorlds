package net.mayus.modpackworlds.tpa;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeclineTpa {

    public static void declineTpaByPlayer(Player p, String s) {

        //Player p = Player who sends /tpdecline
        //String s = args[0] of /tpdecline


        if(SendTpa.tpaMap.containsKey(s) && SendTpa.tpaMap.get(s).equalsIgnoreCase(p.getName())) {
            SendTpa.tpaMap.remove(s, p.getName());
            p.sendMessage("You declined the request");
            Bukkit.getPlayer(s).sendMessage("§6" + p.getName() + " §adeclined your request!");

        } else {
            p.sendMessage("§cYou did not receive a teleport request from this player!");
        }


    }

}
