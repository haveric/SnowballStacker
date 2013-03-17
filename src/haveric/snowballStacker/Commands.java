package haveric.snowballStacker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private SnowballStacker plugin;

    private static String cmdMain = "snowballstacker";
    private static String cmdHelp = "help";
    private static String cmdSetFreeze = "setfreeze";
    private static String cmdSetSnowBiome = "setsnow";

    public Commands(SnowballStacker ss) {
        plugin = ss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ChatColor msgColor = ChatColor.DARK_AQUA;
        ChatColor highlightColor = ChatColor.YELLOW;

        String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";

        boolean op = false;
        if (sender.isOp()) {
            op = true;
        }

        boolean canAdjust = false;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            canAdjust = Perms.canAdjust(player);
        }

        if (commandLabel.equalsIgnoreCase(cmdMain)) {

            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase(cmdHelp))) {
                sender.sendMessage(title + "github.com/haveric/SnowballStacker - v" + plugin.getDescription().getVersion());
                String freeze;
                if (Config.canFreezeWater()) {
                    freeze = "enabled.";
                } else {
                    freeze = "disabled.";
                }
                sender.sendMessage("Freezing ice is: " + highlightColor + freeze);

                String onlySnow;
                if (Config.isOnlySnowBiomes()) {
                    onlySnow = "only snow biomes.";
                } else {
                    onlySnow = "all biomes.";
                }
                sender.sendMessage("Stacking enabled for: " + highlightColor + onlySnow);

                if (op || canAdjust) {
                    sender.sendMessage("/" + cmdMain + " " + cmdSetFreeze + " [true/false] - " + msgColor + "Turn freezing on/off");
                    sender.sendMessage("/" + cmdMain + " " + cmdSetSnowBiome + " [true/false] - " + msgColor + "True=Only snow biomes, False=All Biomes");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase(cmdSetFreeze)) {
                if (op || canAdjust) {
                    if (args[1].equalsIgnoreCase("true")) {
                        Config.setFreezeWater(true);
                        sender.sendMessage(title + "Freezing set to " + highlightColor + "enabled.");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        Config.setFreezeWater(false);
                        sender.sendMessage(title + "Freezing set to " + highlightColor + "disabled.");
                    }
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase(cmdSetSnowBiome)) {
                if (op || canAdjust) {
                    if (args[1].equalsIgnoreCase("true")) {
                        Config.setOnlySnowBiomes(true);
                        sender.sendMessage(title + "Stacking enabled for " + highlightColor + "only snow biomes.");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        Config.setOnlySnowBiomes(false);
                        sender.sendMessage(title + "Stacking enabled for " + highlightColor + "all biomes.");
                    }
                }
            }
        }
        return false;
    }

    public String getMain() {
        return cmdMain;
    }
}
