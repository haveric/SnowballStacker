package haveric.snowballStacker;

//import java.util.logging.Logger;

import haveric.snowballStacker.mcstats.Metrics;
import haveric.snowballStacker.mcstats.Metrics.Graph;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class SnowballStacker extends JavaPlugin {
	private static Logger log;

    private final SBPlayerInteract playerInteract = new SBPlayerInteract(this);
    private Commands commands = new Commands(this);

    private Metrics metrics;

    @Override
    public void onEnable() {
    	log = getLogger();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerInteract, this);

        Config.init(this);

        // WorldGuard
        setupWorldGuard(pm);
        // Towny
        //setupTowny(pm);

        Config.setup();

        getCommand(Commands.getMain()).setExecutor(commands);

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

            // Custom data
            Graph javaGraph = metrics.createGraph("Java Version");
            String javaVersion = System.getProperty("java.version");
            javaGraph.addPlotter(new Metrics.Plotter(javaVersion) {
                @Override
                public int getValue() {
                    return 1;
                }
            });
            metrics.addGraph(javaGraph);
            // End Custom data

            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
