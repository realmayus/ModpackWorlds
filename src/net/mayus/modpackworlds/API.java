package net.mayus.modpackworlds;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class API {

    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }


    public static void unloadWorld(World world) {
        Bukkit.getServer().unloadWorld(world, true);
    }


}
