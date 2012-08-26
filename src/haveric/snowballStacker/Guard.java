package haveric.snowballStacker;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Guard {

    private static WorldGuardPlugin worldGuard = null;
    //private static Towny towny = null;

    public static void setWorldGuard(WorldGuardPlugin newWorldGuard) {
        worldGuard = newWorldGuard;
    }

    public static boolean worldGuardEnabled() {
        return (worldGuard != null);
    }
/*
    public static void setTowny(Towny newTowny) {
        towny = newTowny;
    }

    private static boolean townyEnabled() {
        return (towny != null);
    }
*/
    public static boolean canPlace(Player player, Location location) {
        boolean canPlace = true;

        if (worldGuardEnabled()) {
            canPlace = worldGuard.canBuild(player, location);
        }

        /* TODO: Handle Towny properly and completely
        if (canPlace && townyEnabled()) {
            TownBlock tb = TownyUniverse.getTownBlock(location);
            if (tb != null) {
                try {
                    String name = tb.getResident().getName();

                    player.sendMessage("Resident: " + name);
                    // if a player owns this land, only they can build on it.
                    if (player.getName().equals(name)) {
                        //player.sendMessage("You own this plot");
                        canPlace = true;
                    } else {
                        //player.sendMessage("You do not own this plot");
                        canPlace = false;
                    }
                } catch (NotRegisteredException e) {
                    // Town Block is not registered as a resident so anyone can build here.
                    try {
                        if (tb.getTown().getResidents().contains(TownyUniverse.getDataSource().getResident(player.getName()))) {
                            //player.sendMessage("You belong to this town");
                            canPlace = true;
                        } else {
                            //player.sendMessage("You do not belong to this town");
                            canPlace = false;
                        }
                    } catch (NotRegisteredException e1) {
                        canPlace = false;
                    }
                }
            }
        }
        */

        return canPlace;
    }
}
