package haveric.snowballStacker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.*;

public class SSUtil {

    public static boolean canHoldSnow(Block block) {
        boolean holdsSnow = true;

        BlockData blockData = block.getBlockData();

        String blockType = block.getType().toString().toUpperCase();
        if (blockData instanceof Stairs) {
            Stairs stairsData = (Stairs) blockData;
            if (stairsData.getHalf() == Stairs.Half.BOTTOM) {
                holdsSnow = false;
            }
        } else if (blockData instanceof Slab) {
            Slab slabData = (Slab) blockData;
            if (slabData.getType() == Slab.Type.BOTTOM) {
                holdsSnow = false;
            }
        } else if (blockData instanceof Piston) {
            Piston pistonData = (Piston) blockData;
            if (pistonData.isExtended()) {
                holdsSnow = false;
            }
        } else if (blockData instanceof AmethystCluster) {
            holdsSnow = false;
        } else if (blockData instanceof Bed) {
            holdsSnow = false;
        } else if (blockData instanceof Campfire) {
            holdsSnow = false;
        } else if (blockData instanceof Candle) {
            holdsSnow = false;
        } else if (blockData instanceof Door) {
            holdsSnow = false;
        } else if (blockData instanceof Fire) {
            holdsSnow = false;
        } else if (blockData instanceof Fence) {
            holdsSnow = false;
        } else if (blockData instanceof Gate) {
            holdsSnow = false;
        } else if (blockData instanceof GlassPane) {
            holdsSnow = false;
        } else if (blockData instanceof Sapling) {
            holdsSnow = false;
        } else if (blockData instanceof Sign) {
            holdsSnow = false;
        } else if (blockData instanceof Switch) { // buttons
            holdsSnow = false;
        } else if (blockData instanceof TrapDoor) {
            holdsSnow = false;
        } else if (blockData instanceof Wall) {
            holdsSnow = false;
        } else if (blockData instanceof WallSign) {
            holdsSnow = false;
        } else if (blockType.contains("PRESSURE_PLATE")) {
            holdsSnow = false;
        } else if (blockType.contains("_CARPET")) {
            holdsSnow = false;
        } else if (blockType.contains("POTTED_")) {
            holdsSnow = false;
        } else if (blockType.contains("_BANNER")) {
            holdsSnow = false;
        } else if (blockType.contains("_CORAL")) {
            holdsSnow = false;
        } else {
            holdsSnow = canHoldSnow(block.getType());
        }

        return holdsSnow;
    }

    private static boolean canHoldSnow(Material mat) {
        boolean holdsSnow = true;

        switch(mat) {
            case ACTIVATOR_RAIL:
            case AIR:
            case ANVIL:
            case BREWING_STAND:
            case BROWN_MUSHROOM:
            case CAULDRON:
            case CHEST:
            case DAYLIGHT_DETECTOR:
            case DEAD_BUSH:
            case DETECTOR_RAIL:
            case DRAGON_EGG:
            case ENDER_CHEST:
            case FLOWER_POT:
            case HOPPER:
            case ICE:
            case LADDER:
            case LAVA:
            case LEVER:
            case PACKED_ICE:
            case POWERED_RAIL:
            case PUMPKIN_STEM:
            case REDSTONE_WIRE:
            case RED_MUSHROOM:
            case SNOW:
            case TORCH:
            case TRAPPED_CHEST:
            case TRIPWIRE:
            case TRIPWIRE_HOOK:
            case VINE:
            case WATER:

            // 1.8
            case ARMOR_STAND:

            // 1.14
            case CAKE:

            case CACTUS:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case NETHER_WART:
            case SUGAR_CANE:
            case SWEET_BERRIES:
            case SWEET_BERRY_BUSH:
            case WHEAT:

            case REPEATER:

            case ENCHANTING_TABLE:
            case END_PORTAL_FRAME:
            case END_PORTAL:
            case NETHER_PORTAL:

            case SUNFLOWER:
            case LILAC:
            case ROSE_BUSH:
            case PEONY:
            case TALL_GRASS:
            case LARGE_FERN:
            case TALL_SEAGRASS:

            case PISTON_HEAD:
            case MOVING_PISTON:
            case RAIL:
            case COMPARATOR:
            case REDSTONE_TORCH:
            case REDSTONE_WALL_TORCH:

            case SHORT_GRASS:
            case FERN:
            case SEAGRASS:
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case RED_TULIP:
            case ORANGE_TULIP:
            case WHITE_TULIP:
            case PINK_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:

            case SKELETON_SKULL:
            case WITHER_SKELETON_SKULL:
            case CREEPER_HEAD:
            case DRAGON_HEAD:
            case PLAYER_HEAD:
            case ZOMBIE_HEAD:
            case SKELETON_WALL_SKULL:
            case WITHER_SKELETON_WALL_SKULL:
            case CREEPER_WALL_HEAD:
            case DRAGON_WALL_HEAD:
            case PLAYER_WALL_HEAD:
            case ZOMBIE_WALL_HEAD:

            case BLACK_STAINED_GLASS_PANE:
            case BLUE_STAINED_GLASS_PANE:
            case PINK_STAINED_GLASS_PANE:
            case PURPLE_STAINED_GLASS_PANE:
            case BROWN_STAINED_GLASS_PANE:
            case CYAN_STAINED_GLASS_PANE:
            case GLASS_PANE:
            case GRAY_STAINED_GLASS_PANE:
            case GREEN_STAINED_GLASS_PANE:
            case LIGHT_BLUE_STAINED_GLASS_PANE:
            case LIGHT_GRAY_STAINED_GLASS_PANE:
            case LIME_STAINED_GLASS_PANE:
            case MAGENTA_STAINED_GLASS_PANE:
            case ORANGE_STAINED_GLASS_PANE:
            case RED_STAINED_GLASS_PANE:
            case WHITE_STAINED_GLASS_PANE:
            case YELLOW_STAINED_GLASS_PANE:

            case LILY_PAD:
            case COBWEB:

            case END_ROD:
            case FARMLAND:
            case CHORUS_PLANT:

            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:

            case WALL_TORCH:
            case PAINTING:
            case LECTERN:
            case CONDUIT:
            case COMPOSTER:

            case MELON_STEM:
            case ATTACHED_MELON_STEM:
            case ATTACHED_PUMPKIN_STEM:

            case GRINDSTONE:
            case STONECUTTER:
            case BELL:
            case LANTERN:

            // 1.16
            case CHAIN:
            case CRIMSON_FUNGUS:
            case CRIMSON_ROOTS:
            case NETHER_SPROUTS:
            case SOUL_LANTERN:
            case SOUL_TORCH:
            case TWISTING_VINES:
            case TWISTING_VINES_PLANT:
            case WARPED_FUNGUS:
            case WARPED_ROOTS:
            case WEEPING_VINES:
            case WEEPING_VINES_PLANT:

            // 1.17
            case BIG_DRIPLEAF:
            case BIG_DRIPLEAF_STEM:
            case CAVE_VINES:
            case CAVE_VINES_PLANT:
            case GLOW_LICHEN:
            case HANGING_ROOTS:
            case LIGHT:
            case LIGHTNING_ROD:
            case POINTED_DRIPSTONE:
            case POWDER_SNOW:
            case SCULK_SENSOR:
            case SMALL_DRIPLEAF:
            case SPORE_BLOSSOM:
                holdsSnow = false;
                break;
            default:
                break;
        }

        return holdsSnow;
    }
}
