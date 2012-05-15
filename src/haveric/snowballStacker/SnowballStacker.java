package haveric.snowballStacker;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SnowballStacker extends JavaPlugin{
    final Logger log = Logger.getLogger("Minecraft");
    private final SBPlayerInteract playerInteract = new SBPlayerInteract(this);
    private Commands commands = new Commands(this);
    
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerInteract, this);
		
		Config.init(this);
		Config.setup();
		
		getCommand(Commands.getMain()).setExecutor(commands);
	}

	@Override
	public void onDisable() {

	}

}
