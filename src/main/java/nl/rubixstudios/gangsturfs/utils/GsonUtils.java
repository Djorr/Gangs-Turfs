package nl.rubixstudios.gangsturfs.utils;

import com.google.gson.reflect.TypeToken;
import nl.rubixstudios.gangsturfs.combat.CombatTagObject;
import nl.rubixstudios.gangsturfs.games.turf.TurfData;
import nl.rubixstudios.gangsturfs.gang.Gang;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.npc.NPCData;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GsonUtils {

    public static final Type GANG_TYPE = new TypeToken<Map<UUID, Gang>>(){}.getType();
    public static final Type PLAYER_TYPE = new TypeToken<Map<UUID, GangPlayer>>(){}.getType();
    public static final Type TURF_TYPE = new TypeToken<List<TurfData>>(){}.getType();
    public static final Type NPC_TYPE = new TypeToken<List<NPCData>>(){}.getType();
    public static final Type COMBAT_TYPE = new TypeToken<List<CombatTagObject>>(){}.getType();

    public static Class<?> getFactionClass(String name) {
        if ("PlayerGang".equals(name)) {
            return PlayerGang.class;
        }

        throw new RuntimeException("Invalid gang type");
    }

    public static String getFactionType(Class<?> clazz) {
        if (clazz == PlayerGang.class) {
            return "PlayerGang";
        }

        throw new RuntimeException("Invalid gang class type");
    }
}
