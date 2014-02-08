package haveric.snowballStacker;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private static SnowballStacker plugin;

    private static String cfgFreezeWater = "Freeze_Water";
    private static String cfgOnlySnowBiomes = "Only_Works_In_Snow_Biomes";
    private static FileConfiguration config;
    private static File configFile;

    // Defaults
    private static final boolean FREEZE_WATER_DEFAULT = true;
    private static final boolean ONLY_SNOW_BIOMES_DEFAULT = true;

    /**
     * Initializes the config file
     * @param ss The main class used to
     */
    public static void init(SnowballStacker ss) {
        plugin = ss;
        configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Sets up the default variables if they don't exist yet.
     *
     */
    public static void setup() {
        boolean freeze = config.getBoolean(cfgFreezeWater, FREEZE_WATER_DEFAULT);
        config.set(cfgFreezeWater, freeze);

        boolean onlySnow = config.getBoolean(cfgOnlySnowBiomes, ONLY_SNOW_BIOMES_DEFAULT);
        config.set(cfgOnlySnowBiomes, onlySnow);

        saveConfig();
    }

    /**
     * Saves the configuration to the file.
     */
    private static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether freezing water is enabled or not
     * @return true if freezing water is enabled<br>
     *         false if disabled
     */
    public static boolean canFreezeWater() {
        return config.getBoolean(cfgFreezeWater);
    }

    /**
     * Turns freezing water on or off
     * @param freeze : true to turn on freezing, false to turn it off
     */
    public static void setFreezeWater(boolean freeze) {
        config.set(cfgFreezeWater, freeze);
        saveConfig();
    }

    /**
     * Gets whether you can stack snow only in snow biomes or all
     * @return true if snow stacking only works in snow biomes<br>
     *         false if snow stacking works in all biomes
     */
    public static boolean isOnlySnowBiomes() {
        return config.getBoolean(cfgOnlySnowBiomes);
    }

    public static void setOnlySnowBiomes(boolean newOnlySnowBiomes) {
        config.set(cfgOnlySnowBiomes, newOnlySnowBiomes);
        saveConfig();
    }
}
