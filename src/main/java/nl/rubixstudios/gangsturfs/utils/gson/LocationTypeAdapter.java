package nl.rubixstudios.gangsturfs.utils.gson;

import com.google.gson.*;
import nl.rubixstudios.gangsturfs.utils.LocationUtils;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(LocationUtils.locationToString(location));
    }

    @Override
    public Location deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return LocationUtils.stringToLocation(json.getAsString());
    }
}