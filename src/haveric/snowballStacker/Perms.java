package haveric.snowballStacker;

import org.bukkit.entity.Player;

public class Perms {
    private static final String permStack = "snowballstacker.stack";
    private static final String permFreeze = "snowballstacker.freeze";
    private static final String permAdjust = "snowballstacker.adjust";

    public static boolean canStack(Player player) {
        return player.hasPermission(permStack);
    }

    public static boolean canFreeze(Player player) {
        return player.hasPermission(permFreeze);
    }

    public static boolean canAdjust(Player player) {
        return player.hasPermission(permAdjust);
    }
}
