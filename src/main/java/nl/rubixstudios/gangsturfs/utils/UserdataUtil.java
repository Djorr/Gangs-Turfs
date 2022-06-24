package nl.rubixstudios.gangsturfs.utils;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.userdata.Userdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserdataUtil {

    public static Userdata loadUserdata(final File playerFile) {
        final String content = FileUtils.readWholeFile(playerFile);
        if (content == null) return null;

        return GangsTurfs.getInstance().getGson().fromJson(content, Userdata.class);
    }

    public static List<Userdata> loadUserdatas(final File[] playerFiles) {
        final List<Userdata> userdatas = new ArrayList<>();

        for (File file : playerFiles) {
            final String content = FileUtils.readWholeFile(file);
            if (content == null) return null;

            userdatas.add(GangsTurfs.getInstance().getGson().fromJson(content, Userdata.class));
        }

        return userdatas;
    }

    public static Userdata getUserdataByUuid(final UUID uuid, final List<Userdata> userdatas) {
        for (Userdata userdata : userdatas) {
            if (userdata.getUuid().equals(uuid)) return userdata;
        }
        return null;
    }
}