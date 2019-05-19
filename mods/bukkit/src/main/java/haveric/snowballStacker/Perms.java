package haveric.snowballStacker;

import org.bukkit.entity.Player;

public class Perms {
    private static final String permStack = "snowballstacker.stack";
    private static final String permFreeze = "snowballstacker.freeze";
    private static final String permAdmin = "snowballstacker.admin";

    public static boolean canStack(Player player) {
        return player.hasPermission(permStack);
    }

    public static boolean canFreeze(Player player) {
        return player.hasPermission(permFreeze);
    }

    public static boolean hasAdmin(Player player) {
        return player.hasPermission(permAdmin);
    }

    public static String getPermStack() {
        return permStack;
    }

    public static String getPermFreeze() {
        return permFreeze;
    }

    public static String getPermAdmin() {
        return permAdmin;
    }
}
