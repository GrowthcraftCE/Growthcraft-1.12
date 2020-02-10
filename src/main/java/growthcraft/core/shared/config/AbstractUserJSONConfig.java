package growthcraft.core.shared.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import net.minecraftforge.common.config.Configuration.UnicodeInputStreamReader;

import java.io.*;

public abstract class AbstractUserJSONConfig {
    // INITIALIZE
    // OPEN

    public static final String DEFAULT_ENCODING = "UTF-8";
    protected final Gson gson = new GsonBuilder().
            setPrettyPrinting().
            serializeNulls().
            create();
    private File targetConfigFile;
    private File targetDefaultConfigFile;

    /**
     * @return a default json configuration string
     */
    protected abstract String getDefault();

    private void writeDefaultConfigTo(File file) {
        try {
            GrowthcraftLogger.getLogger(Reference.MODID).debug("Creating default json-config %s", file);
            if (file.getParentFile() != null)
                file.getParentFile().mkdirs();

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    GrowthcraftLogger.getLogger(Reference.MODID).error("Could not create default config %s", file);
                    return;
                }
            }

            if (file.canWrite()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(getDefault());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the config file contents from the buffer
     *
     * @param buff - the buffer to read from
     */
    protected abstract void loadFromBuffer(BufferedReader buff) throws IllegalStateException;

    public AbstractUserJSONConfig setConfigFile(File dir, String filename) {
        this.targetConfigFile = new File(dir, filename);
        this.targetDefaultConfigFile = new File(dir, filename + ".default");
        GrowthcraftLogger.getLogger(Reference.MODID).debug("Config file `%s` was set for `%s`", targetConfigFile, this);
        GrowthcraftLogger.getLogger(Reference.MODID).debug("DEFAULT Config file `%s` was set for `%s`", targetDefaultConfigFile, this);
        return this;
    }

    private void prepareUserConfig() throws IOException {
        if (!targetConfigFile.exists()) {
            if (targetConfigFile.getParentFile() != null)
                targetConfigFile.getParentFile().mkdirs();

            if (!targetConfigFile.createNewFile()) {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Could not create config file `%s`", targetConfigFile);
                return;
            }

            if (targetDefaultConfigFile.exists()) {
                Files.copy(targetDefaultConfigFile, targetConfigFile);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Could not copy default config file `%s` to `%s`", targetDefaultConfigFile, targetConfigFile);
            }
        }
    }

    private void readUserConfigFile(File file) {
        BufferedReader buffer = null;
        UnicodeInputStreamReader input = null;

        try {
            GrowthcraftLogger.getLogger(Reference.MODID).debug("Loading json-config %s", file);

            prepareUserConfig();
            if (file.canRead()) {
                input = new UnicodeInputStreamReader(new FileInputStream(file), DEFAULT_ENCODING);
                buffer = new BufferedReader(input);
                loadFromBuffer(buffer);
            } else {
                GrowthcraftLogger.getLogger(Reference.MODID).error("Could not read config file %s", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void loadUserConfig() {
        writeDefaultConfigTo(targetDefaultConfigFile);
        try {
            readUserConfigFile(targetConfigFile);
            return;
        }
        //catch (IllegalStateException e)
        catch (Exception e) {
            GrowthcraftLogger.getLogger(Reference.MODID).error("JSON Config '%s' contains errors", targetConfigFile);
            e.printStackTrace();
        }
        GrowthcraftLogger.getLogger(Reference.MODID).warn("Falling back to default config file");
        readUserConfigFile(targetDefaultConfigFile);
    }

    public void preInit() {
    }

    public void register() {
    }

    public void init() {
    }

    public void postInit() {
    }
}
