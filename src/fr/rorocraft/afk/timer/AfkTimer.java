package fr.rorocraft.afk.timer;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.rorocraft.afk.AfkPlugin;
import fr.rorocraft.afk.manager.AfkManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AfkTimer extends BukkitRunnable {
    AfkPlugin plugin;
    Player player;
    Location position;
    int minutes = -1;

    public AfkTimer(AfkPlugin plugin, Player player, Location firstPosition){
        this.plugin = plugin;
        this.player =player;
        this.position = firstPosition;
    }


    @Override
    public void run() {
        if(player == null) this.cancel();

        AfkManager afkManager = plugin.getAfkManager();


        if(afkManager.isAfk(player.getUniqueId())) return;

        if(position.distance(player.getLocation()) < 1) {
            minutes ++;
        } else {
            this.position = player.getLocation();
            minutes = 0;

        }

        if(minutes == 3){
            afkManager.addPlayerAfk(player.getUniqueId());

            String title = plugin.getConfig().getString("messages.afk_enabled.title").replace("&", "§");
            String subtitle = plugin.getConfig().getString("messages.afk_enabled.subtitle").replace("&", "§");

            TitleAPI.sendTitle(player, title, subtitle, 10, 3 * 20, 10 );

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60 * 120, 2));

            this.position = player.getLocation();
            minutes = 0;
        }




    }
}
