package nl.rubixstudios.gangsturfs.npc;

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
import java.util.UUID;

@Getter
public class NPCManager implements Listener, ManagerEnabler {

    private static @Getter NPCManager instance;
    private final File npcsFile;

    private List<NPCData> npcs;

    public NPCManager() {
        instance = this;

        this.npcsFile = FileUtils.getOrCreateFile(Config.NPC_DIR, "npcs.json");

        this.loadNpcs();
    }

    public void disable() {
        this.saveNpcs(true, true);

    }

    // Load & Save npcs

    private void loadNpcs() {
        final String content = FileUtils.readWholeFile(this.npcsFile);

        if (content == null) {
            this.npcs = new ArrayList<>();
            return;
        }

        this.npcs = GangsTurfs.getInstance().getGson().fromJson(content, GsonUtils.NPC_TYPE);

        GangsTurfs.getInstance().log("- &7Loaded &e" + this.npcs.size() + " &7npcs.");
    }

    public void saveNpcs(boolean onDisable, boolean log) {
        if(this.npcs == null) return;

        FileUtils.writeString(this.npcsFile, GangsTurfs.getInstance().getGson()
            .toJson(this.npcs, GsonUtils.NPC_TYPE));

        if(log) {
            GangsTurfs.getInstance().log("- &7Saved &e" + this.npcs.size() + " &7npcs.");
        }

        if (onDisable) {
            this.npcs.clear();
        }
    }

    public NPCData getNpc(String name) {
        return this.npcs.stream().filter(npcData -> npcData.getNpcName().equals(name)).findFirst().orElse(null);
    }

    public void createNpc(NPCData npcData) {
        this.npcs.add(npcData);
    }

    public void removeNpc(NPCData npcData) {
        this.npcs.remove(npcData);
    }

    public NPCData getCitizensNPC(UUID uuid) {
        return this.npcs.stream().filter(npcData -> npcData.getNpcUuid().equals(uuid)).findFirst().orElse(null);
    }
}
