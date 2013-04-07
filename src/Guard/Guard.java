package Guard;

import haveric.snowballStacker.SnowballStacker;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockOwner;
import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Guard {

    private static WorldGuardPlugin worldGuard = null;
    private static Towny towny = null;

    private static final String permTownyBuildOwnTown = "towny.claimed.owntown.build";
    //private static final String permTownyDestroyOwnTown = "towny.claimed.owntown.destroy";
    //private static final String permTownySwitchOwnTown = "towny.claimed.owntown.switch";
    //private static final String permTownyItemUseOwnTown = "towny.claimed.owntown.item_use";
    private static final String permTownyBuildAllTown = "towny.claimed.alltown.build";
    //private static final String permTownyDestroyAllTown = "towny.claimed.alltown.destroy";
    //private static final String permTownySwitchAllTown = "towny.claimed.alltown.switch";
    //private static final String permTownyItemUseAllTown = "towny.claimed.alltown.item_use";

    private static final String permTownyBuildWild = "towny.wild.build";
    //private static final String permTownyDestroyWild = "towny.wild.destroy";
    //private static final String permTownySwitchWild = "towny.wild.switch";
    //private static final String permTownyItemUseWild = "towny.wild.item_use";

    private static SnowballStacker plugin;
    public static void init(SnowballStacker ss) {
        plugin = ss;
    }

    public static void setWorldGuard(WorldGuardPlugin newWorldGuard) {
        worldGuard = newWorldGuard;
    }

    public static boolean worldGuardEnabled() {
        return (worldGuard != null);
    }

    public static void setTowny(Towny newTowny) {
        towny = newTowny;
    }

    private static boolean townyEnabled() {
        return (towny != null);
    }

    public static boolean canPlace(Player player, Location location) {
        boolean canPlace = true;

        if (worldGuardEnabled()) {
            canPlace = worldGuard.canBuild(player, location);
        }

        ChatColor buildColor = ChatColor.GREEN;
        ChatColor blockedColor = ChatColor.RED;
        ChatColor hlColor = ChatColor.YELLOW;

        String playerName = player.getName();
        if (canPlace && townyEnabled()) {
            plugin.log.info("Player: " + playerName);
            plugin.log.info("  OwnTownBuildPerms: " + canTownyBuildOwnTown(player));
            plugin.log.info("  AllTownBuildPerms: " +  canTownyBuildAllTown(player));
            plugin.log.info("  WildBuildPerms: " +  canTownyBuildWild(player));
            plugin.log.info("  Is Mayor: " + isTownyMayor(playerName, location));
            plugin.log.info("  Is Assistant: " + isTownyAssistant(playerName, location));
            plugin.log.info("  Is Resident: " + isTownyResident(playerName, location));
            plugin.log.info("  Is TownPlot Owner: " + isTownyPlotOwner(playerName, location));
            plugin.log.info("  In Wild? : " + inTownyWilderness(location));
            plugin.log.info("  Is Ally? : " + isAlly(playerName, location));
            plugin.log.info("  Can Ally Build: " + canAllyBuild(playerName, location));

            if (inTownyWilderness(location) && canTownyBuildWild(player)) {
                player.sendMessage(buildColor + "In wild and can build.");
            } else if (canTownyBuildAllTown(player)) {
                player.sendMessage(buildColor + "In any town and can build.");
            } else if (canTownyBuildOwnTown(player) && isTownyResident(playerName, location)) {
                player.sendMessage(buildColor + "In your town and can build.");
            } else if (isTownyMayor(playerName, location)) {
                player.sendMessage(buildColor + "Mayor of this town and can build.");
            } else if (isTownyAssistant(playerName, location)) {
                player.sendMessage(buildColor + "Assistant of this town and can build.");
            } else if (isTownyPlotOwner(playerName, location)) {
                player.sendMessage(buildColor + "In your own plot and can build.");
            } else if (canAllyBuild(player.getName(), location) && isAlly(playerName, location)) {
                player.sendMessage(buildColor + "In your allies plot and can build.");
            } else {
                player.sendMessage(blockedColor + "No build perms found");
                canPlace = false;
            }
        }

        return canPlace;
    }

    private static boolean canTownyBuildOwnTown(Player player) {
        boolean canBuild = false;

        if (townyEnabled()) {
            canBuild = player.hasPermission(permTownyBuildOwnTown);
        }
        return canBuild;
    }

    private static boolean canTownyBuildAllTown(Player player) {
        boolean canBuild = false;

        if (townyEnabled()) {
            canBuild = player.hasPermission(permTownyBuildAllTown);
        }
        return canBuild;
    }

    private static boolean canTownyBuildWild(Player player) {
        boolean canBuild = false;

        if (townyEnabled()) {
            canBuild = player.hasPermission(permTownyBuildWild);
        }
        return canBuild;
    }

    private static boolean isTownyResident(String playerName, Location location) {
        boolean isResident = false;

        if (townyEnabled()) {
            try {
                TownBlock townBlock = TownyUniverse.getTownBlock(location);
                if (townBlock != null) {
                    Town town = townBlock.getTown();
                    if (town != null) {
                        isResident = town.hasResident(playerName);
                    }
                }

            } catch (NotRegisteredException e) {
                //e.printStackTrace();
            }
        }

        return isResident;
    }

    private static boolean isTownyMayor(String playerName, Location location) {
        boolean isMayor = false;

        if (townyEnabled()) {
            TownBlock townBlock = TownyUniverse.getTownBlock(location);

            if (townBlock != null) {
                try {
                    Town town = townBlock.getTown();
                    if (town != null) {
                        Resident mayor = town.getMayor();
                        Resident player = TownyUniverse.getDataSource().getResident(playerName);

                        if (mayor != null && player != null && mayor.equals(player)) {
                            isMayor = true;
                        }

                    }
                } catch (NotRegisteredException e) {
                    //e.printStackTrace();
                }
            }
        }

        return isMayor;
    }

    private static boolean isTownyAssistant(String playerName, Location location) {
        boolean isAssistant = false;

        if (townyEnabled()) {
            TownBlock townBlock = TownyUniverse.getTownBlock(location);
            if (townBlock != null) {
                try {
                    Town town = townBlock.getTown();
                    if (town != null) {
                        List<Resident> assistants = town.getAssistants();
                        Resident player = TownyUniverse.getDataSource().getResident(playerName);
                        if (player != null && assistants.contains(player)) {
                            isAssistant = true;
                        }
                    }
                } catch (NotRegisteredException e) {
                    //e.printStackTrace();
                }
            }
        }

        return isAssistant;
    }

    private static boolean isTownyPlotOwner(String playerName, Location location) {
        boolean isPlotOwner = false;

        if (townyEnabled()) {
            try {
                TownBlockOwner owner = TownyUniverse.getDataSource().getResident(playerName);

                if (owner != null) {
                    TownBlock townBlock = TownyUniverse.getTownBlock(location);
                    if (townBlock != null) {
                        isPlotOwner = townBlock.isOwner(owner);
                    }
                }
            } catch (NotRegisteredException e) {
                //e.printStackTrace();
            }
        }

        return isPlotOwner;
    }

    private static boolean canAllyBuild(String allyName, Location location) {
        boolean allyCanBuild = false;

        if (townyEnabled()) {
            TownBlock townBlock = TownyUniverse.getTownBlock(location);
            if (townBlock != null) {
                allyCanBuild = townBlock.getPermissions().getAllyPerm(ActionType.BUILD);
            }
        }

        return allyCanBuild;
    }

    private static boolean isAlly(String playerName, Location location) {
        boolean isAlly = false;

        if (townyEnabled()) {
            TownBlock townBlock = TownyUniverse.getTownBlock(location);
            if (townBlock != null) {
                try {
                    Resident resident = townBlock.getResident();
                    Resident ally = TownyUniverse.getDataSource().getResident(playerName);

                    if (resident != null && ally != null) {
                        isAlly = resident.hasFriend(ally);
                    }
                } catch (NotRegisteredException e) {
                    //e.printStackTrace();
                }
            }
        }

        return isAlly;
    }

    private static boolean inTownyWilderness(Location location) {
        boolean inWilderness = false;

        if (townyEnabled()) {
            inWilderness = TownyUniverse.isWilderness(location.getBlock());
        }

        return inWilderness;
    }
}
