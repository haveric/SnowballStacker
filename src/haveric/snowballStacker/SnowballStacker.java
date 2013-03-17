package haveric.snowballStacker;

//import java.util.logging.Logger;

import haveric.snowballStacker.mcstats.Metrics;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class SnowballStacker extends JavaPlugin {
	private static Logger log;

    private Commands commands;

    private Metrics metrics;

    @Override
    public void onEnable() {
        log = getLogger();
        commands = new Commands(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SBPlayerInteract(), this);

        Config.init(this);

        // WorldGuard
        setupWorldGuard(pm);
        // Towny
        //setupTowny(pm);

        Config.setup();

        getCommand(commands.getMain()).setExecutor(commands);

        setupMetrics();
    }

    @Override
    public void onDisable() {

    }

    private void setupWorldGuard(PluginManager pm) {
        Plugin worldGuard = pm.getPlugin("WorldGuard");
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            log.info(String.format("[%s] WorldGuard not found.", getDescription().getName()));
        } else {
            Guard.setWorldGuard((WorldGuardPlugin) worldGuard);
        }
    }
/*
    private void setupTowny(PluginManager pm) {
        Plugin towny = pm.getPlugin("Towny");
        if (towny ==  null || !(towny instanceof Towny)) {
            log.info(String.format("[%s] Towny not found.", getDescription().getName()));
        } else {
            Guard.setTowny((Towny) towny);
        }
    }
*/
    private void setupMetrics() {
        try {
            metrics = new Metrics(this);

            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
