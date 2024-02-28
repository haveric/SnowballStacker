package haveric.snowballStacker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private SnowballStacker plugin;

    private static final String cmdMain = "snowballstacker";
    private static final String cmdMainAlt = "ss";

    private static final String cmdHelp = "help";
    private static final String cmdSetFreeze = "setfreeze";
    private static final String cmdSetSnowBiome = "setsnow";
    private static final String cmdSetGolemStack = "setgolem";
    private static final String cmdPerms = "perms";
    private static final String cmdPermsAlt = "perm";

    private static final ChatColor defColor = ChatColor.WHITE;
    private static final ChatColor highlightColor = ChatColor.GOLD;

    public Commands(SnowballStacker ss) {
        plugin = ss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ChatColor msgColor = ChatColor.DARK_AQUA;

        String title = msgColor + "[" + ChatColor.GRAY + plugin.getDescription().getName() + msgColor + "] ";

        boolean op = sender.isOp();
        boolean hasAdminPerm = false;
        if (sender instanceof Player) {
            hasAdminPerm = Perms.hasAdmin((Player) sender);
        }

        if (commandLabel.equalsIgnoreCase(cmdMain) || commandLabel.equalsIgnoreCase(cmdMainAlt)) {
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

                if (op || hasAdminPerm) {
                    sender.sendMessage("/" + cmdMain + " " + cmdSetFreeze + getTFString(Config.canFreezeWater()) + " - " + msgColor + "Turn freezing on/off");
                    sender.sendMessage("/" + cmdMain + " " + cmdSetSnowBiome + getTFString(Config.isOnlySnowBiomes()) + " - " + msgColor + "True=Only snow biomes, False=All Biomes");
                    sender.sendMessage("/" + cmdMain + " " + cmdSetGolemStack + getTFString(Config.canSnowGolemsStack()) + " - " + msgColor + "Whether snow golems can stack snow");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase(cmdSetFreeze)) {
                if (op || hasAdminPerm) {
                    if (args[1].equalsIgnoreCase("true")) {
                        Config.setFreezeWater(true);
                        sender.sendMessage(title + "Freezing set to " + highlightColor + "enabled.");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        Config.setFreezeWater(false);
                        sender.sendMessage(title + "Freezing set to " + highlightColor + "disabled.");
                    }
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase(cmdSetSnowBiome)) {
                if (op || hasAdminPerm) {
                    if (args[1].equalsIgnoreCase("true")) {
                        Config.setOnlySnowBiomes(true);
                        sender.sendMessage(title + "Stacking enabled for " + highlightColor + "only snow biomes.");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        Config.setOnlySnowBiomes(false);
                        sender.sendMessage(title + "Stacking enabled for " + highlightColor + "all biomes.");
                    }
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase(cmdSetGolemStack)) {
                if (op || hasAdminPerm) {
                    if (args[1].equalsIgnoreCase("true")) {
                        Config.setSnowGolemCanStack(true);
                        sender.sendMessage(title + "Snow golem stacking " + highlightColor + "enabled.");
                    } else if (args[1].equalsIgnoreCase("false")) {
                        Config.setSnowGolemCanStack(false);
                        sender.sendMessage(title + "Snow golem stacking " + highlightColor + "disabled.");
                    }
                }
            } else if (args.length == 1 && (args[0].equalsIgnoreCase(cmdPerms) || args[0].equalsIgnoreCase(cmdPermsAlt))) {
                if (op || hasAdminPerm) {
                    sender.sendMessage(title + "Permission Nodes:");
                    sender.sendMessage(Perms.getPermStack() + " - " + msgColor + "Allows stacking of snow by throwing snowballs.");
                    sender.sendMessage(Perms.getPermFreeze() + " - " + msgColor + "Allows freezing water by throwing snowballs.");
                    sender.sendMessage(Perms.getPermAdmin() + " - " + msgColor + "Allows use of admin commands.");
                } else {
                    sender.sendMessage(title + ChatColor.RED + "You must be an op or have admin perms to see permission nodes.");
                }
            }
        }
        return false;
    }

    public static String getTFString(boolean bool) {
        String msg;

        if (bool) {
            msg = " <" + highlightColor + "true" + defColor + ",false>";
        } else {
            msg = " <true," + highlightColor + "false" + defColor + ">";
        }

        return msg;
    }

    public String getMain() {
        return cmdMain;
    }
}
