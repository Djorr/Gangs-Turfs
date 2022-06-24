package nl.rubixstudios.gangsturfs.npc.entity;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.npc.type.NPCType;
import org.bukkit.Location;

import java.util.UUID;

@Getter
public class CustomNPC {

    private final String npcConfName; // The cached name
    private final String npcName; // The Name above the NPC

    private final UUID uuid;
    private final Location loc;

    private @Setter boolean shown;

    @Setter
    private int entityId;
    @Setter private NPCType npcType;
    @Setter private UUID npcSkinUUID;

    public CustomNPC(String npcConfName, String npcName, UUID uuid, Location loc) {
        this.npcConfName = npcConfName;
        this.npcName = npcName;

        this.uuid = uuid;
        this.loc = loc;

        this.npcType = null;
    }
}