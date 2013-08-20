package haveric.snowballStacker;

import haveric.snowballStacker.blockLogger.BlockLogger;
import haveric.snowballStacker.guard.Guard;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;


public class SBPlayerInteract implements Listener {

    public SBPlayerInteract() { }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Snowball) {
            Snowball snowball = (Snowball) entity;

            Location location = snowball.getLocation();
            World world = snowball.getWorld();
            Block block = world.getBlockAt(location);
            Material type = block.getType();

            boolean canPlace = true;

            if (Config.isOnlySnowBiomes()) {
                Biome biome = block.getBiome();
                if (biome == Biome.FROZEN_OCEAN || biome == Biome.FROZEN_RIVER || biome == Biome.ICE_MOUNTAINS || biome == Biome.ICE_PLAINS || biome == Biome.TAIGA || biome == Biome.TAIGA_HILLS) {
                    canPlace = true;
                } else {
                    canPlace = false;
                }
            }

            LivingEntity livingEntity = snowball.getShooter();

            Player player = null;
            if (canPlace && livingEntity instanceof Player) {
                player = (Player) livingEntity;
                canPlace = Guard.canPlace(player, location);
            }

            if (canPlace) {
                if (Config.canFreezeWater() && type == Material.STATIONARY_WATER) {
                    if (player == null || Perms.canFreeze(player)) {
                        freezeWater(player, block);
                    }
                } else {
                    if (player == null || Perms.canStack(player)) {
                        addSnowToBlock(player, block);
                    }
                }
            }
        }
    }

    private void addSnowToBlock(Player player, Block b) {
        Block downBlock = b.getRelative(BlockFace.DOWN);
        Material downType = downBlock.getType();
        Material type = b.getType();

        if (type == Material.AIR && (downType == Material.AIR || downType == Material.SNOW)) {
            addSnowToBlock(player, downBlock);
        } else {
            BlockState oldState = b.getState();

            int data = b.getData();
            if (type == Material.AIR && canHoldSnow(downBlock)) {
                b.setTypeIdAndData(Material.SNOW.getId(), (byte) 0, true);
            } else if (type == Material.SNOW) {
                if (data >= 6) {
                    b.setType(Material.SNOW_BLOCK);
                } else {
                    b.setTypeIdAndData(Material.SNOW.getId(), (byte) (data + 1), true);
                }
            }

            BlockState newState = b.getState();
            if (player != null) {
                BlockLogger.logBlock(player.getName(), oldState, newState);
            }
        }
    }

    private void freezeWater(Player player, Block b) {
        if (b.getRelative(BlockFace.UP).getType() != Material.STATIONARY_WATER) {
            BlockState oldState = b.getState();

            b.setType(Material.ICE);

            BlockState newState = b.getState();
            if (player != null) {
                BlockLogger.logBlock(player.getName(), oldState, newState);
            }
        }
    }

    private boolean canHoldSnow(Block b) {
        boolean holdsSnow = true;
        switch(b.getType()) {
            case ACTIVATOR_RAIL:
            case AIR:
            case ANVIL:
            case BEACON:
            case BED:
            case BED_BLOCK:
            case BIRCH_WOOD_STAIRS:
            case BREWING_STAND:
            case BRICK_STAIRS:
            case BROWN_MUSHROOM:
            case CACTUS:
            case CAKE_BLOCK:
            case CARPET:
            case CAULDRON:
            case CHEST:
            case COBBLE_WALL:
            case COBBLESTONE_STAIRS:
            case CROPS:
            case DAYLIGHT_DETECTOR:
            case DEAD_BUSH:
            case DETECTOR_RAIL:
            case DRAGON_EGG:
            case DIODE_BLOCK_OFF:
            case DIODE_BLOCK_ON:
            case ENCHANTMENT_TABLE:
            case ENDER_CHEST:
            case FENCE:
            case FENCE_GATE:
            case FIRE:
            case FLOWER_POT:
            case GLASS:
            case GLOWSTONE:
            case GOLD_PLATE:
            case ICE:
            case IRON_FENCE:
            case IRON_PLATE:
            case JUNGLE_WOOD_STAIRS:
            case LADDER:
            case LAVA:
            case LEAVES:
            case LEVER:
            case LOCKED_CHEST:
            case LONG_GRASS:
            case NETHER_BRICK_STAIRS:
            case NETHER_FENCE:
            case NETHER_STALK:
            case NETHER_WARTS:
            case PISTON_BASE:
            case PISTON_EXTENSION:
            case PISTON_MOVING_PIECE:
            case PISTON_STICKY_BASE:
            case POWERED_RAIL:
            case PUMPKIN_STEM:
            case QUARTZ_STAIRS:
            case RAILS:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case REDSTONE_TORCH_OFF:
            case REDSTONE_TORCH_ON:
            case REDSTONE_WIRE:
            case RED_MUSHROOM:
            case SANDSTONE_STAIRS:
            case SAPLING:
            case SIGN:
            case SIGN_POST:
            case SKULL:
            case SMOOTH_STAIRS:
            case SNOW:
            case SPRUCE_WOOD_STAIRS:
            case STATIONARY_LAVA:
            case STATIONARY_WATER:
            case STEP:
            case STONE_BUTTON:
            case STONE_PLATE:
            case SUGAR_CANE_BLOCK:
            case THIN_GLASS:
            case TNT:
            case TORCH:
            case TRAPPED_CHEST:
            case TRAP_DOOR:
            case TRIPWIRE:
            case TRIPWIRE_HOOK:
            case VINE:
            case WATER:
            case WATER_LILY:
            case WEB:
            case WHEAT:
            case WOOD_BUTTON:
            case WOOD_PLATE:
            case WOOD_STAIRS:
            case WOOD_STEP:
            case YELLOW_FLOWER:
                holdsSnow = false;
                break;
        default:
            break;
        }
        return holdsSnow;
    }
}
