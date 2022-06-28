package nl.rubixstudios.gangsturfs.turf;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.utils.FileUtils;
import nl.rubixstudios.gangsturfs.utils.GsonUtils;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TurfManager implements Listener, ManagerEnabler {

    private static @Getter
    TurfManager instance;
    private final File turfsFile;

    private List<TurfData> turfs;


    public TurfManager() {
        instance = this;

        this.turfsFile = FileUtils.getOrCreateFile(Config.TURFS_DIR, "turfs.json");

        this.loadTurfGames();
    }

    public void disable() {
        this.saveTurfGames();
    }

    // Load & Save game

    private void loadTurfGames() {
        final String content = FileUtils.readWholeFile(this.turfsFile);

        if (content == null) {
            this.turfs = new ArrayList<>();
            return;
        }

        this.turfs = GangsTurfs.getInstance().getGson().fromJson(content, GsonUtils.TURF_TYPE);
    }

    private void saveTurfGames() {
        if(this.turfs == null) return;

        this.turfs.forEach(turfData -> {
            turfData.setTurfStarted(false);
            turfData.getPlayersInTurf().clear();
        });

        FileUtils.writeString(this.turfsFile, GangsTurfs.getInstance().getGson()
                .toJson(this.turfs, GsonUtils.TURF_TYPE));

        this.turfs.clear();
    }

    public TurfData getTurf(String name) {
        return this.turfs.stream().filter(turfData1 -> turfData1.getTurfName().equals(name)).findFirst().orElse(null);
    }

    public void createTurf(TurfData turfData) {
        this.turfs.add(turfData);
    }

    public void removeTurf(TurfData turfData) {
        this.turfs.remove(turfData);
    }

}
