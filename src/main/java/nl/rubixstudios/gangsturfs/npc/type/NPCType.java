package nl.rubixstudios.gangsturfs.npc.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rubixstudios.gangsturfs.utils.Color;

import java.util.Map;
import java.util.TreeMap;

@Getter
@AllArgsConstructor
public enum NPCType {

    TURF("turf", Color.translate("&e&lSTART TURF"), "Turf", false),
    GANG("gang", Color.translate("&c&lMAAK GANG"), "Gang", false);

    private final String npcIdentityName;
    private final String npcName;
    private final String npcDesc;
    private final boolean statistic;

    private static final Map<String, NPCType> BY_NAME;

    public static NPCType getByName(String name) { return BY_NAME.get(name); }

    static {
        BY_NAME = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (NPCType npcType : values()) {
            BY_NAME.put(npcType.npcIdentityName, npcType);
        }
    }
}
