package nl.rubixstudios.gangsturfs.utils;

import nl.rubixstudios.gangsturfs.GangsTurfs;

public enum Datastore{

    JSON, MONGO, MYSQL;

    public static Datastore DATASTORE;

    static {
        String value = GangsTurfs.getInstance().getConfig().getString("DATASTORE");

        try {
            DATASTORE = Datastore.valueOf(value);
        } catch(IllegalArgumentException e) {
            DATASTORE = JSON;
        }
    }
}
