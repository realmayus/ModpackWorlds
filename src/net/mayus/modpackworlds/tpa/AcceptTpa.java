package net.mayus.modpackworlds.tpa;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AcceptTpa {

    public static void acceptTpaByPlayer(Player p, String s) {

        //Player p = Player who sends /tpaccept
        //String s = args[0] of /tpaccept


        if(SendTpa.tpaMap.containsKey(s) && SendTpa.tpaMap.get(s).equalsIgnoreCase(p.getName())) {
            SendTpa.tpaMap.remove(s, p.getName());
            Bukkit.getPlayer(s).sendMessage("§6" + p.getName() + " §aaccepted your teleport request!");
            Bukkit.getPlayer(s).teleport(p);
            Bukkit.getPlayer(s).sendMessage("§aYou were teleported to §6" + p.getName());
        } else {
            p.sendMessage("§cYou have not received a teleport request from this player.");
        }


    }



}
