package haveric.snowballStacker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor{

	SnowballStacker plugin;
	
	static String cmdMain = "snowballstacker";
	static String cmdHelp = "help";
	
	public Commands(SnowballStacker ss){
		plugin = ss;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		ChatColor msgColor = ChatColor.DARK_AQUA;
		ChatColor highlightColor = ChatColor.YELLOW;
		
		String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";
		
		if (commandLabel.equalsIgnoreCase(cmdMain)){

			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(cmdHelp))){
				sender.sendMessage(title+"github.com/haveric/SnowballStacker - v" + plugin.getDescription().getVersion());
				String freeze;
				if (Config.canFreezeWater()){
					freeze = "enabled.";
				} else {
					freeze = "disabled.";
				}
				sender.sendMessage("Freezing ice is: " + highlightColor + freeze);
			}
		}
		
		return false;
	}
	
	public static String getMain() {
		return cmdMain;
	}

	public static void setMain(String cmd) {
		cmdMain = cmd;
	}

	public static String getHelp() {
		return cmdHelp;
	}

	public static void setHelp(String cmd) {
		cmdHelp = cmd;
	}
}
