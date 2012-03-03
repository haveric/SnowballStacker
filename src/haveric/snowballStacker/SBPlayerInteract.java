package haveric.snowballStacker;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SBPlayerInteract implements Listener{

	SnowballStacker plugin;
	public SBPlayerInteract(SnowballStacker snowballStacker) {
		plugin = snowballStacker;
	}
	
	@EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Snowball){ 
            Location location = entity.getLocation();
            World world = entity.getWorld();
            Block block = world.getBlockAt(location);
            Material type = block.getType();
            
            if (Config.canFreezeWater() && type == Material.STATIONARY_WATER){
            	freezeWater(block);
            } else {
            	addSnowToBlock(block);
            }
        }
	}
	
	private void addSnowToBlock(Block b){
		Block downBlock = b.getRelative(BlockFace.DOWN);
		Material downType = downBlock.getType();
		Material type = b.getType();

		if(type == Material.AIR && (downType == Material.AIR || downType == Material.SNOW)){
			addSnowToBlock(downBlock);
		} else {
			int data = b.getData();
			if (type == Material.AIR && canHoldSnow(downBlock)){
				b.setTypeIdAndData(Material.SNOW.getId(), (byte)0, true);
			} else if (type == Material.SNOW){
				if (data >= 6){
					b.setType(Material.SNOW_BLOCK);
				} else {
					b.setTypeIdAndData(Material.SNOW.getId(), (byte)(data+1), true);
				}
			}
		}
	}

	private void freezeWater(Block b){
		if (b.getRelative(BlockFace.UP).getType() != Material.STATIONARY_WATER){
			b.setType(Material.ICE);
		}
	}
	
	private boolean canHoldSnow(Block b){
		boolean holdsSnow = true;
		switch(b.getType()){
			case AIR:
			case SAPLING:
			case WATER:
			case STATIONARY_WATER:
			case LAVA:
			case STATIONARY_LAVA:
			case BED:
			case BED_BLOCK:
			case POWERED_RAIL:
			case DETECTOR_RAIL:
			case WEB:
			case LONG_GRASS:
			case DEAD_BUSH:
			case YELLOW_FLOWER:
			case RED_ROSE:
			case BROWN_MUSHROOM:
			case RED_MUSHROOM:
			case TORCH:
			case REDSTONE_WIRE:
			case WOOD_STAIRS:
			case SIGN_POST:
			case WOODEN_DOOR:
			case LADDER:
			case RAILS:
			case SIGN:
			case LEVER:
			case STONE_PLATE:
			case IRON_DOOR:
			case WOOD_PLATE:
			case REDSTONE_TORCH_ON:
			case REDSTONE_TORCH_OFF:
			case STONE_BUTTON:
			case SUGAR_CANE:
			case FENCE:
			case PORTAL:
			case CAKE:
			case CAKE_BLOCK:
			case DIODE_BLOCK_OFF:
			case DIODE_BLOCK_ON:
			case CHEST:
			case TRAP_DOOR:
			case IRON_FENCE:
			case THIN_GLASS:
			case PUMPKIN_STEM:
			case MELON_STEM:
			case VINE:
			case FENCE_GATE:
			case BRICK_STAIRS:
			case SMOOTH_STAIRS:
			case WATER_LILY:
			case NETHER_FENCE:
			case NETHER_BRICK_STAIRS:
			case NETHER_WARTS:
			case NETHER_STALK:
			case ENCHANTMENT_TABLE:
			case BREWING_STAND:
			case CAULDRON:
			case ENDER_PORTAL:
			case ENDER_PORTAL_FRAME:
			case DRAGON_EGG:
				holdsSnow = false;
				break;
		default:
			break;
		}
		return holdsSnow;
	}
}
