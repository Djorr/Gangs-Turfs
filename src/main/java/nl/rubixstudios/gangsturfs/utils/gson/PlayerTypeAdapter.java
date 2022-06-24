package nl.rubixstudios.gangsturfs.utils.gson;

import com.google.gson.*;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTypeAdapter implements JsonSerializer<Map<UUID, GangPlayer>>, JsonDeserializer<Map<UUID, GangPlayer>> {

    @Override
    public JsonElement serialize(Map<UUID, GangPlayer> map, Type type, JsonSerializationContext context) {
        final JsonArray array = new JsonArray();

        map.values().forEach(player -> array.add(context.serialize(player, player.getClass())));

        return array;
    }

    @Override
    public Map<UUID, GangPlayer> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        final JsonArray array = json.getAsJsonArray();
        final Map<UUID, GangPlayer> playerMap = new HashMap<>();

        GangPlayer fplayer;

        for(JsonElement element : array) {
            fplayer = context.deserialize(element.getAsJsonObject(), GangPlayer.class);
            playerMap.put(fplayer.getUuid(), fplayer);
        }

        return playerMap;
    }
}
