package haveric.snowballStacker;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.projectiles.ProjectileSource;


public class SBPlayerInteract implements Listener {

    private SnowballStacker plugin;

    public SBPlayerInteract(SnowballStacker ss) {
        plugin = ss;
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Snowball) {
            Snowball snowball = (Snowball) entity;

            Location location = snowball.getLocation();
            World world = snowball.getWorld();
            Block block = world.getBlockAt(location);
            Material type = block.getType();

            double temperature = block.getTemperature();
            boolean canPlace = true;

            if (Config.isOnlySnowBiomes()) {
                // Arbitrary value based on snow biome temperatures: http://minecraft.gamepedia.com/Biome#Snow-covered_biomes
                if (temperature >= 0.35) {
                    canPlace = false;
                }
            }
            ProjectileSource projectileSource = snowball.getShooter();

            Player player = null;
            if (canPlace) {
                if (projectileSource instanceof Player) {
                    player = (Player) projectileSource;
                } else if (projectileSource instanceof Snowman) {
                    canPlace = Config.canSnowGolemsStack();
                }
            }

            if (canPlace) {
                if (Config.canFreezeWater() && type == Material.WATER) {
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

    private void addSnowToBlock(Player player, Block block) {
        if (block.getY() > 0) {
            Block downBlock = block.getRelative(BlockFace.DOWN);
            Material downType = downBlock.getType();
            Material type = block.getType();

            if (type == Material.AIR && (downType == Material.AIR || downType == Material.SNOW)) {
                addSnowToBlock(player, downBlock);
            } else {
                int newLayers = -1;
                Material newMat = Material.SNOW;
                if (type == Material.AIR && SSUtil.canHoldSnow(downBlock.getType())) {
                    newLayers = 1;
                } else if (type == Material.SNOW) {
                    Snow snowBlock = (Snow) block.getBlockData();
                    int snowLayers = snowBlock.getLayers();
                    if (snowLayers >= 7) {
                        newMat = Material.SNOW_BLOCK;
                        newLayers = 0;
                    } else {
                        newLayers = snowLayers + 1;
                    }
                }
                if (newLayers > -1) {
                    replaceBlock(player, block, newMat, newLayers);
                }
            }
        }
    }

    private void freezeWater(Player player, Block b) {
        if (b.getRelative(BlockFace.UP).getType() != Material.WATER) {
            if (player == null) {
                b.setType(Material.ICE);
            } else {
                replaceBlock(player, b, Material.ICE, 0);
            }
        }
    }

    private void replaceBlock(Player player, Block block, Material mat, int layers) {
        BlockState state = block.getState();

        block.setType(mat);

        if (mat == Material.SNOW) {
            Snow snowBlock = (Snow) block.getBlockData();
            snowBlock.setLayers(layers);
            block.setBlockData(snowBlock);
        }

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(state.getBlock(), state, block, player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
        plugin.getServer().getPluginManager().callEvent(placeEvent);

        if (placeEvent.isCancelled()) {
            state.update(true);
        }
    }
}
