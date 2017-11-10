package growthcraft.core;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class GrowthcraftConfiguration extends Configuration {

    private Configuration configuration;

    public static String logLevel;

    public GrowthcraftConfiguration(File configDirectory, String name) {
        configuration = new Configuration(new File(configDirectory.getPath(), "growthcraft/" + name + ".cfg"));
    }

}
