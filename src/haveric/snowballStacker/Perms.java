package haveric.snowballStacker;

import org.bukkit.entity.Player;

public class Perms {
	private static final String permStack = "snowballstacker.stack";
	private static final String permFreeze = "snowballstacker.freeze";
	
	public static boolean canStack(Player player) {
		return player.hasPermission(permStack);
	}
	
	public static boolean canFreeze(Player player) {
		return player.hasPermission(permFreeze);
	}
	
}
