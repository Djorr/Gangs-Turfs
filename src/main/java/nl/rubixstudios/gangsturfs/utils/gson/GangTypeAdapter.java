package nl.rubixstudios.gangsturfs.utils.gson;

import com.google.gson.*;
import nl.rubixstudios.gangsturfs.gang.Gang;
import nl.rubixstudios.gangsturfs.utils.GsonUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GangTypeAdapter implements JsonSerializer<Map<UUID, Gang>>, JsonDeserializer<Map<UUID, Gang>> {

    @Override
    public JsonElement serialize(Map<UUID, Gang> map, Type type, JsonSerializationContext context) {
        final JsonArray array = new JsonArray();

        JsonObject factionObject;

        for(Gang faction : map.values()) {
            factionObject = new JsonObject();

            factionObject.addProperty("type", GsonUtils.getFactionType(faction.getClass()));
            factionObject.add("data", context.serialize(faction, faction.getClass()));

            array.add(factionObject);
        }

        return array;
    }

    @Override
    public Map<UUID, Gang> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        final JsonArray array = json.getAsJsonArray();
        final Map<UUID, Gang> factionMap = new HashMap<>();

        Gang faction;
        Class<?> clazz;

        for(JsonElement element : array) {
            clazz = GsonUtils.getFactionClass(element.getAsJsonObject().get("type").getAsString());
            faction = context.deserialize(element.getAsJsonObject().get("data"), clazz);

            factionMap.put(faction.getId(), faction);
        }

        return factionMap;
    }
}
