package fr.rorocraft.afk;

import fr.rorocraft.afk.commands.AfkCommand;
import fr.rorocraft.afk.events.EventListener;
import fr.rorocraft.afk.manager.AfkManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AfkPlugin extends JavaPlugin {
    private AfkManager afkManager;

    @Override
    public void onEnable() {

        // Create a config file
        this.saveDefaultConfig();

        // Handle list of afk players
        afkManager = new AfkManager();

        registerCommands();
        registerEvents();


        System.out.println("[AfkPlugin] Plugin enabled");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCommands() {
        this.getCommand("afk").setExecutor(new AfkCommand(this));
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }


    public AfkManager getAfkManager() {
        return afkManager;
    }
}
