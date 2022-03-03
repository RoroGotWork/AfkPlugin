package fr.rorocraft.afk.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AfkManager {
    private List<UUID> playersAfk;

    public AfkManager() {
        this.playersAfk = new ArrayList<>();
    }


    public void addPlayerAfk(UUID uuid){
        this.playersAfk.add(uuid);
    }

    public boolean isAfk(UUID uuid){
        return playersAfk.contains(uuid);
    }

    public  void removePlayerAfk(UUID uuid){
        this.playersAfk.remove(uuid);
    }



}
