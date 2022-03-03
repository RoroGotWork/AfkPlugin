package fr.rorocraft.afk.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import fr.rorocraft.afk.AfkPlugin;
import fr.rorocraft.afk.manager.AfkManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class AfkCommand implements CommandExecutor {
    private AfkPlugin plugin;

    public AfkCommand(AfkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] label) {
        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;
        UUID playerUUID = player.getUniqueId();
        AfkManager afkManager = plugin.getAfkManager();

        if(afkManager.isAfk(playerUUID)){
            afkManager.removePlayerAfk(playerUUID);

            String title = plugin.getConfig().getString("messages.afk_disabled.title").replace("&", "§");
            String subtitle = plugin.getConfig().getString("messages.afk_disabled.subtitle").replace("&", "§");

            TitleAPI.sendTitle(player, title, subtitle, 10, 3 * 20, 10 );

            player.removePotionEffect(PotionEffectType.BLINDNESS);

        } else {
            afkManager.addPlayerAfk(playerUUID);

            String title = plugin.getConfig().getString("messages.afk_enabled.title").replace("&", "§");
            String subtitle = plugin.getConfig().getString("messages.afk_enabled.subtitle").replace("&", "§");

            TitleAPI.sendTitle(player, title, subtitle, 10, 3 * 20, 10 );

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60 * 120, 2));

        }


        return false;
    }
}
