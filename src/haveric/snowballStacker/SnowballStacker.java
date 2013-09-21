package haveric.snowballStacker;

//import java.util.logging.Logger;

import haveric.snowballStacker.blockLogger.BlockLogger;
import haveric.snowballStacker.guard.Guard;

import java.util.logging.Logger;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.towny.Towny;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;

public class SnowballStacker extends JavaPlugin {
    public Logger log;

    private Commands commands;

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
        setupTowny(pm);
        // CoreProtect
        setupCoreProtect(pm);
        // LogBlock
        setupLogBlock(pm);

        Config.setup();

        getCommand(commands.getMain()).setExecutor(commands);
    }

    @Override
    public void onDisable() {

    }

    private void setupWorldGuard(PluginManager pm) {
        Plugin worldGuard = pm.getPlugin("WorldGuard");
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            log.info("WorldGuard not found.");
        } else {
            Guard.setWorldGuard((WorldGuardPlugin) worldGuard);
        }
    }

    private void setupTowny(PluginManager pm) {
        Plugin towny = pm.getPlugin("Towny");
        if (towny ==  null || !(towny instanceof Towny)) {
            log.info("Towny not found.");
        } else {
            Guard.setTowny((Towny) towny);
        }
    }

    private void setupCoreProtect(PluginManager pm) {
        Plugin coreProtect = pm.getPlugin("CoreProtect");
        if (coreProtect == null || !(coreProtect instanceof CoreProtect)) {
            // CoreProtect not found
        } else {
            CoreProtectAPI coreProtectAPI = ((CoreProtect) coreProtect).getAPI();
            BlockLogger.setCoreProtectAPI(coreProtectAPI);
        }
    }

    private void setupLogBlock(PluginManager pm) {
        Plugin logBlock = pm.getPlugin("LogBlock");
        if (logBlock == null || !(logBlock instanceof LogBlock)) {
            // LogBlock not found
        } else {
            Consumer logBlockConsumer = ((LogBlock) logBlock).getConsumer();
            BlockLogger.setLogBlockConsumer(logBlockConsumer);
        }
    }
}
