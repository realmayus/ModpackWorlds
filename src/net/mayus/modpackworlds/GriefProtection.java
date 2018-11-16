package net.mayus.modpackworlds;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class GriefProtection implements Listener {

     @EventHandler
    public void onGrief(PlayerInteractEvent e) {
         Player p = e.getPlayer();
         if(!Config.isResident(p.getLocation().getWorld().getName(), p.getUniqueId().toString()) || p.getUniqueId().toString().equalsIgnoreCase(Config.getOwnerOfWorld(p.getWorld().getName()))) {
             e.setCancelled(true);
         }
     }



}
