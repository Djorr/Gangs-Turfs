package nl.rubixstudios.gangsturfs.data;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigFile extends YamlConfiguration {

    @Getter private final File file;

    public ConfigFile(String name) throws RuntimeException {
        this.file = new File(GangsTurfs.getInstance().getDataFolder(), name);

        if(!this.file.exists()) {
            GangsTurfs.getInstance().saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch(IOException | InvalidConfigurationException e) {
            GangsTurfs.getInstance().log("");
            GangsTurfs.getInstance().log("&2===&e=============================================&2===");
            GangsTurfs.getInstance().log(StringUtils.center("&cError occurred while loading " + name + ".", 51));
            GangsTurfs.getInstance().log("");

            Stream.of(e.getMessage().split("\n")).forEach(line -> GangsTurfs.getInstance().log(line));

            GangsTurfs.getInstance().log("&2===&e=============================================&2===");
            throw new RuntimeException();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationSection getSection(String name) {
        return super.getConfigurationSection(name);
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    @Override
    public String getString(String path) {
        return Color.translate(super.getString(path, ""));
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(Color::translate).collect(Collectors.toList());
    }
}
