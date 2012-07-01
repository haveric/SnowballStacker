package haveric.snowballStacker;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private static SnowballStacker plugin;

    private static String cfgFreezeWater = "Freeze Water";
    private static FileConfiguration config;
    private static File configFile;

    // Defaults
    private static final boolean FREEZE_WATER_DEFAULT = true;

    /**
     * Initializes the config file
     * @param ss The main class used to
     */
    public static void init(SnowballStacker ss) {
        plugin = ss;
        configFile = new File(plugin.getDataFolder() + "/config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Sets up the default variables if they don't exist yet.
     *
     */
    public static void setup() {
        boolean freeze = config.getBoolean(cfgFreezeWater, FREEZE_WATER_DEFAULT);
        config.set(cfgFreezeWater, freeze);

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
     *            false if disabled
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
}
