package io.github.olszynblack.ExtraSheeps;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class main extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public static int tnt_power = 0;
    public static int tnt_cooldown = 0;
    public static List colors;
    public static boolean same = true;
    public static double same_chance = 0.0;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new listener(), this);
        createCustomConfig();
        read_config();
        //getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {
        //getLogger().info("onDisable is called!");
    }


    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void read_config(){
        tnt_power = getCustomConfig().getInt("tnt.power");
        tnt_cooldown = getCustomConfig().getInt("tnt.cooldown");

        same = getCustomConfig().getBoolean("chance.same");
        same_chance = getCustomConfig().getDouble("chance.same_chance");
        colors = getCustomConfig().getDoubleList("chance.colors");
    }


    public static void sheep(PlayerShearEntityEvent event, Sheep sheep) {
        double get_chance = 0;

        if (same) {
            get_chance = same_chance;
        } else if (!same) {
            switch (sheep.getColor().toString().toLowerCase()) {
                case "white":
                    get_chance = ((double) colors.get(0));
                    break;
                case "orange":
                    get_chance = ((double) colors.get(1));
                    break;
                case "magenta":
                    get_chance = ((double) colors.get(2));
                    break;
                case "light_blue":
                    get_chance = ((double) colors.get(3));
                    break;

                case "yellow":
                    get_chance = ((double) colors.get(4));
                    break;
                case "lime":
                    get_chance = ((double) colors.get(5));
                    break;
                case "pink":
                    get_chance = ((double) colors.get(6));
                    break;
                case "gray":
                    get_chance = ((double) colors.get(7));
                    break;

                case "light_gray":
                    get_chance = ((double) colors.get(8));
                    break;
                case "cyan":
                    get_chance = ((double) colors.get(9));
                    break;
                case "purple":
                    get_chance = ((double) colors.get(10));
                    break;
                case "blue":
                    get_chance = ((double) colors.get(11));
                    break;

                case "brown":
                    get_chance = ((double) colors.get(12));
                    break;
                case "green":
                    get_chance = ((double) colors.get(13));
                    break;
                case "red":
                    get_chance = ((double) colors.get(14));
                    break;
                case "black":
                    get_chance = ((double) colors.get(15));
                    break;

                default:
                    break;
            }
        }

        if (get_chance != 0) {
            double probability = 1 / get_chance;
            if((int)(Math.random() * (int) probability + 1) == 1) sheep_boom_boom(event, sheep);
        }
    }

    public static void sheep_boom_boom(PlayerShearEntityEvent event, Sheep sheep) {
        event.setCancelled(true);
        for (int i = 0; i < tnt_power; i++) {
            TNTPrimed tnt = sheep.getWorld().spawn(sheep.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(tnt_cooldown);
            tnt.setSource(((Entity) sheep));
        }
    }
}
