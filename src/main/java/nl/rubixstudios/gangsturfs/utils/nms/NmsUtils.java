package nl.rubixstudios.gangsturfs.utils.nms;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.Executor;

public abstract class NmsUtils {

    @Getter private static NmsUtils instance;

    protected Executor bukkitExecutor;

    protected static final String HANDLER_NAME = "packet_handler";
    protected static final String LISTENER_NAME = "glass_listener";

    public static void init() {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        if (version.equalsIgnoreCase("v1_12_R1")) {
            instance = new NmsUtils_1_12();
        }
    }

    public abstract void disable();

    public abstract CommandMap getCommandMap();

    public abstract void injectPacketInterceptor(Player player);

    public abstract void deinjectPacketInterceptor(Player player);

    public abstract void sendPacket(Player player, Object packet);

    public abstract void readPacket(Player player, Packet<?> packet, int npcId, Inventory inventory);

    public abstract void setValue(Object obj, String name, Object value);

    public abstract Object getValue(Object obj, String name);

}
