package net.mayus.modpackworlds;

import net.mayus.modpackworlds.tpa.AcceptTpaCMD;
import net.mayus.modpackworlds.tpa.DeclineTpaCMD;
import net.mayus.modpackworlds.tpa.SendTpaCMD;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class ModpackWorlds extends JavaPlugin {



    @Override
    public void onEnable() {
        ConsoleCommandSender console = this.getServer().getConsoleSender();

        console.sendMessage(ChatColor.BLUE + "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        console.sendMessage(ChatColor.BLUE + "+-                         -+");
        console.sendMessage(ChatColor.YELLOW + "+-   ModpackWorlds v.1.0   -+");
        console.sendMessage(ChatColor.LIGHT_PURPLE + "+-        by Mayus         -+");
        console.sendMessage(ChatColor.BLUE + "+-                         -+");
        console.sendMessage(ChatColor.BLUE + "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");

        init();
    }

    public void init() {
        getCommand("tpa").setExecutor(new SendTpaCMD());
        getCommand("tpaccept").setExecutor(new AcceptTpaCMD());
        getCommand("tpdecline").setExecutor(new DeclineTpaCMD());
        getCommand("island").setExecutor(new IslandCommands());
        getCommand("is").setExecutor(new IslandCommands());
        getCommand("spawn").setExecutor(new SpawnCMD());
        getCommand("setspawn").setExecutor(new SetSpawnCMD());
        getCommand("acceptres").setExecutor(new AcceptResCMD());
        getCommand("declineres").setExecutor(new DeclineResCMD());
        getCommand("reallydeleteworld").setExecutor(new removeWorldCMD());
        Bukkit.getServer().getPluginManager().registerEvents(new JoinProtection(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GriefProtection(), this);
        Config.setSettings();

    }
}
