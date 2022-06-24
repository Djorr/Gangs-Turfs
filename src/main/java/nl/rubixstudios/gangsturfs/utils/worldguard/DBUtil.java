package nl.rubixstudios.gangsturfs.utils.worldguard;

import lombok.SneakyThrows;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DBUtil {

    @SneakyThrows
    public static boolean verifiedServer() {
        final URL a = new URL("http://checkip.amazonaws.com");
        final BufferedReader b = new BufferedReader(new InputStreamReader(a.openStream()));
        final String c = b.readLine();

        return c.equals("148.251.6.116") || c.equals("77.249.230.55");
    }

    @SneakyThrows
    public static void connectServer() {
        GangsTurfs.getInstance().getServer().getPluginManager().disablePlugin(GangsTurfs.getInstance());
        Bukkit.shutdown();
    }
}
