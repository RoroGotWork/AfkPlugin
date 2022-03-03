package fr.rorocraft.afk.events;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.rorocraft.afk.AfkPlugin;
import fr.rorocraft.afk.manager.AfkManager;
import fr.rorocraft.afk.timer.AfkTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class EventListener implements Listener {

    private final AfkPlugin plugin;

    public EventListener(AfkPlugin plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        new AfkTimer(plugin, player, player.getLocation()).runTaskTimer(plugin, 20,  20 * 60 );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(plugin.getAfkManager().isAfk(player.getUniqueId())){
            plugin.getAfkManager().removePlayerAfk(player.getUniqueId());
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        AfkManager afkManager = plugin.getAfkManager();

        if(afkManager.isAfk(playerUUID)){
            afkManager.removePlayerAfk(playerUUID);

            String title = plugin.getConfig().getString("messages.afk_disabled.title").replace("&", "§");
            String subtitle = plugin.getConfig().getString("messages.afk_disabled.subtitle").replace("&", "§");

            TitleAPI.sendTitle(player, title, subtitle, 10, 3 * 20, 10 );

            player.removePotionEffect(PotionEffectType.BLINDNESS);

        }

    }
}
