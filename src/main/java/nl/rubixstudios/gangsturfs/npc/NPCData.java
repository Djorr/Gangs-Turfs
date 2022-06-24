package nl.rubixstudios.gangsturfs.npc;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.npc.type.NPCType;
import org.bukkit.Location;

import java.util.UUID;

@Getter
public class NPCData {

    private @Setter String npcName;

    private @Setter int npcId;
    private @Setter UUID npcUuid;

    private @Setter Location npcLocation;

    private @Setter NPCType npcType;
}
