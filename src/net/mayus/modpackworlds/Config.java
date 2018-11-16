package net.mayus.modpackworlds;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class Config {


    public static File ConfigFile = new File("plugins/ModpackWorlds", "config.yml");
    public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);




    public static void save() throws IOException {
        Config.save(ConfigFile);

    }
    public static void reload() {
        Config = YamlConfiguration.loadConfiguration(ConfigFile);

    }


    public static void setSettings() {
        List<String> emptyList = new ArrayList<>();
        Config.options().header("ModpackWorlds Plugin made by Mayus exclusively for SnapeCraft.net in 2018 \nSettings: \n  JoinProtectionDuration: Sets the Join Protection to the given value (in Seconds). To disable it, choose the value -1");
        Config.options().copyHeader(true);
        Config.addDefault("settings.JoinProtectionDuration", 10);
        Config.addDefault("islands.init.owner", "900bf5ae-3f2f-4594-8250-1871d6aec064");
        Config.addDefault("islands.init.spawn", "blabla");
        Config.addDefault("islands.init.residents", emptyList);
        Config.options().copyDefaults(true);
        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Integer getJoinProtectionDuration() {
        return Config.getInt("settings.JoinProtectionDuration");
    }

    public static void setSpawn(Location loc) {
        Config.set("lobby.w", loc.getWorld().getName());
        Config.set("lobby.X", loc.getX());
        Config.set("lobby.Y", loc.getY());
        Config.set("lobby.Z", loc.getZ());
        Config.set("lobby.YAW", loc.getYaw());
        Config.set("lobby.PITCH", loc.getPitch());
        try{
            save();
            reload();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Location getSpawn() {
        Location loc = new Location(
                Bukkit.getWorld(Config.getString("lobby.w")),
                Double.parseDouble(Config.getString("lobby.X")),
                Double.parseDouble(Config.getString("lobby.Y")),
                Double.parseDouble(Config.getString("lobby.Z")),
                Float.parseFloat(Config.getString("lobby.YAW")),
                Float.parseFloat(Config.getString("lobby.PITCH"))
        );

        return loc;
    }


    public static void createIsland(Player owner) {

        //Island ID bekommen (sicherstellen, ob bereits benutzt)
        String ID = generateIslandID();
        while(true) {
            if(!islandIDalreadyInUse(ID)) {
                break;
            } else {
                ID = generateIslandID();
            }
        }


        //Welt erstellen, mit einem leeren WorldCreator
        WorldCreator wc = new WorldCreator(ID);

        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        wc.generator("2;0;1;");
        wc.createWorld();

        //Grasblock setzen
        Bukkit.getWorld(ID).getSpawnLocation().getBlock().setType(Material.GRASS);

        //Baum darauf pflanzen
        Bukkit.getWorld(ID).generateTree(Bukkit.getWorld(ID).getSpawnLocation().add(0D, 1D, 0D), TreeType.TREE);


        //Config-Werte setzen

        List<String> emptyList = new ArrayList<>();

        Config.set("islands." + ID + ".owner", owner.getUniqueId().toString());
        Config.set("islands." + ID + ".spawnX", Bukkit.getWorld(ID).getSpawnLocation().getX());
        Config.set("islands." + ID + ".spawnY", Bukkit.getWorld(ID).getSpawnLocation().getY());
        Config.set("islands." + ID + ".spawnZ", Bukkit.getWorld(ID).getSpawnLocation().getZ());
        Config.set("islands." + ID + ".residents", emptyList);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeIsland(String ID) {


        World deleteWorld = Bukkit.getWorld(ID);
        File deleteFolder = deleteWorld.getWorldFolder();


        //Chunks entladen
        Bukkit.getServer().unloadWorld(deleteWorld, true);

        //Welt-Ordner entfernen
        deleteWorld(deleteFolder);

        //Welt aus Config entfernen
        Config.set("islands." + ID, null);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteWorld(File path) {
        //Checken, ob der angegebene Pfad existiert
        if(path.exists()) {
            File files[] = path.listFiles();
            //Durch alle Dateien im Pfad iterieren
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    //Datei löschen
                    files[i].delete();
                }}}
        //Boolean zurückreichen, ob das Löschen erfolgreich war
        return(path.delete());
    }

    public static void addResident(String ID, String uid) {
        //Info bei getResidents(String) lesen!
        List<String> residents = getResidents(ID);
        residents.add(uid);
        Config.set("islands." + ID + ".residents", residents);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeResident(String ID, String uid) {
        List<String> residents = getResidents(ID);
        if(residents.contains(uid)) {
            residents.remove(uid);
        }
        Config.set("islands." + ID + ".residents", residents);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getResidents(String ID) {
        //Kein Null-Check erforderlich, da beim Erstellen von Inseln automatisch eine leere Residentliste gesetzt wird!
        Bukkit.broadcastMessage("Resident Liste: " + Config.getStringList("islands." + ID + ".residents"));
        return Config.getStringList("islands." + ID + ".residents");
    }

    public static Boolean isResident(String ID, String UID) {
        if(getResidents(ID).contains(UID)) {
            return true;
        } else {
            return false;
        }
    }



    public static String generateIslandID() {
        return RandomStringUtils.random(5, true, true);
        }

    public static Boolean islandIDalreadyInUse(String ID) {

        for(String allIDs : Config.getConfigurationSection("islands").getKeys(false)) {
            if(allIDs.equalsIgnoreCase(ID)) {
                //Already in Use
                return true;
            }
        }
        //Not in Use
        return false;

    }

    public static String getOwnerOfWorld(String ID) {
        return Config.getString("islands." + ID + ".owner");
    }

    public static void setLocationOfWorld(String ID, Location loc) {
        //Wieso kein getLocation? --> Spawn in der Config soll nur als Hilfe für die Admins dienen!
        Config.set("islands." + ID + ".spawn", loc);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> getUIDListOfLimitedPlayers() {
        //Was ist ein LimitedPlayer?
        //
        //> LimitedPlayer sind Spieler, die schon Mitglied einer anderen Insel sind
        // und somit keine eigenen Inseln erstellen dürfen

        return Config.getStringList("limitedPlayers.list");
    }

    public static void addLimitedPlayer(String UID) {
        List<String> limitedPlayers = getUIDListOfLimitedPlayers();
        limitedPlayers.add(UID);
        Config.set("limitedPlayers.list", limitedPlayers);
        try {
            save();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeLimitedPlayer(String UID) {
        List<String> limitedPlayers = getUIDListOfLimitedPlayers();
        if(limitedPlayers.contains(UID)) {
            limitedPlayers.remove(UID);
        }
        Config.set("limitedPlayers.list", limitedPlayers);
        try {
            save();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean alreadyResidentOfOtherIsland(String UID) {
        for(String island : Config.getConfigurationSection("islands").getKeys(false)) {
            if(getResidents(island).contains(UID)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean hasAlreadyAWorld(String UID) {
        for(String world : Config.getConfigurationSection("islands").getKeys(false)) {
            if(getOwnerOfWorld(world) != null) {
                if(getOwnerOfWorld(world).equalsIgnoreCase(UID)) {

                return true;
                 }
            } else { return false; }
        }
        return false;
    }


    public static String getWorldOfOwner(String UID) {
        for(String world : Config.getConfigurationSection("islands").getKeys(false)) {
            if(getOwnerOfWorld(world).equalsIgnoreCase(UID)) {
                Bukkit.broadcastMessage("Owner gleich");
                return world;
            }
        }
        return null;
    }

    public static String getWorldOfResident(String UID) {
        for(String world : Config.getConfigurationSection("islands").getKeys(false)) {
            if(getResidents(world).contains(UID)) {
                return world;
            }
        }
        return null;
    }

    public static String getWorldOfPlayer(String UID) {
        if(getWorldOfOwner(UID) != null) {
            return getWorldOfOwner(UID);
        } else if(getWorldOfResident(UID) != null) {
            return getWorldOfResident(UID);
        } else {
            return null;
        }
    }
}
