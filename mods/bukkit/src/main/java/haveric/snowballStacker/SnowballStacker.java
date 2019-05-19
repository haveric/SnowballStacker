package haveric.snowballStacker;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SnowballStacker extends JavaPlugin {
    public Logger log;

    private Commands commands;

    @Override
    public void onEnable() {
        log = getLogger();
        commands = new Commands(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SBPlayerInteract(this), this);

        Config.init(this);

        Config.setup();

        getCommand(commands.getMain()).setExecutor(commands);
    }

    @Override
    public void onDisable() {

    }
}
