package nl.rubixstudios.gangsturfs.combat;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.utils.FileUtils;
import nl.rubixstudios.gangsturfs.utils.GsonUtils;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class CombatTagManager implements Listener, ManagerEnabler {

    @Getter private static CombatTagManager instance;
    private List<CombatTagObject> combatLoggers;

    private final File timersFile;

    public CombatTagManager() {
        instance = this;

        this.timersFile = FileUtils.getOrCreateFile(Config.UTILITIES_DIR, "combatloggers.json");
        this.loadCombatLoggers();

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
    }

    public void disable() {
        this.saveCombatLoggers();
    }

    private void loadCombatLoggers() {
        final String content = FileUtils.readWholeFile(this.timersFile);

        if (content == null) {
            this.combatLoggers = new ArrayList<>();
            return;
        }

        FileUtils.writeString(this.timersFile, GangsTurfs.getInstance().getGson()
                .toJson(this.combatLoggers, GsonUtils.COMBAT_TYPE));
    }

    private void saveCombatLoggers() {
        if(this.combatLoggers == null) return;

        FileUtils.writeString(this.timersFile, GangsTurfs.getInstance().getGson()
                .toJson(this.combatLoggers, GsonUtils.TURF_TYPE));

        this.combatLoggers.clear();
    }

    // Create/Delete CombatLogger

    public void createNewCombatLogger(Player player) {
        if (isCombatLogger(player)) return;

        final CombatTagObject combatTagObject = new CombatTagObject();
        combatTagObject.setPlayerId(player.getUniqueId());
        combatTagObject.setServerQuitInCombat(false);

        this.getCombatLoggers().add(combatTagObject);
    }

    public void deleteCombatlogger(Player player) {
        final CombatTagObject combatTagObject = this.getCombatLogger(player);
        if (combatTagObject == null) return;

        this.getCombatLoggers().remove(combatTagObject);
    }

    // Check isCombatLogger true of false

    public boolean isCombatLogger(Player player) {
        return this.isCombatLogger(player.getUniqueId());
    }

    private boolean isCombatLogger(UUID playerUuid) {
        return this.getCombatLoggers().stream().anyMatch(combatTagObject -> combatTagObject.getPlayerId().equals(playerUuid));
    }

    // Get CombatLogger

    public CombatTagObject getCombatLogger(Player player) {
        return this.getCombatLogger(player.getUniqueId());
    }

    private CombatTagObject getCombatLogger(UUID playerUuid) {
        return this.getCombatLoggers().stream().filter(combatTagObject -> combatTagObject.getPlayerId().equals(playerUuid)).findFirst().orElse(null);
    }
}
