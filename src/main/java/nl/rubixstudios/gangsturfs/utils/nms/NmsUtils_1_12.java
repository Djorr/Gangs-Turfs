package nl.rubixstudios.gangsturfs.utils.nms;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.Packet;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.utils.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.scheduler.CraftScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

public class NmsUtils_1_12 extends NmsUtils implements Listener {


    public NmsUtils_1_12() {

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
        Tasks.asyncLater(() -> Bukkit.getOnlinePlayers().forEach(this::injectPacketInterceptor), 20L);
    }

    private void fetchBukkitExecutor() {
        try {
            Field executorField = CraftScheduler.class.getDeclaredField("executor");
            executorField.setAccessible(true);

            this.bukkitExecutor = (Executor) executorField.get(Bukkit.getScheduler());
        } catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disable() {
        Bukkit.getOnlinePlayers().forEach(this::deinjectPacketInterceptor);
    }

    @Override
    public CommandMap getCommandMap() {
        try {
            CraftServer craftServer = ((CraftServer) Bukkit.getServer());

            Field commandMapField = craftServer.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            return (CommandMap) commandMapField.get(craftServer);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void injectPacketInterceptor(Player player) {
        if(!player.isOnline()) return;

        final CraftPlayer cplayer = (CraftPlayer) player;

        final Channel channel = this.getChannel(cplayer);
        if(channel == null) return;

        final ChannelDuplexHandler handler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
                super.channelRead(context, packet);
            }
        };

        if(channel.pipeline().get(LISTENER_NAME) == null) {
            try {
                channel.pipeline().addBefore(HANDLER_NAME, LISTENER_NAME, handler);
            } catch(NoSuchElementException ignored) { }
        }
    }

    private Channel getChannel(CraftPlayer cplayer) {
        NetworkManager networkManager = cplayer.getHandle().playerConnection.networkManager;

        try {
            Field channelField = networkManager.getClass().getDeclaredField("m");
            channelField.setAccessible(true);

            return (Channel) channelField.get(networkManager);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deinjectPacketInterceptor(Player player) {
        final CraftPlayer cplayer = (CraftPlayer) player;

        Channel channel = this.getChannel(cplayer);
        if(channel == null) return;

        if(channel.pipeline().get(LISTENER_NAME) != null) {
            channel.pipeline().remove(LISTENER_NAME);
        }
    }


    @Override
    public void sendPacket(Player player, Object packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet) packet);
    }

    @Override
    public void readPacket(Player player, net.minecraft.server.v1_12_R1.Packet<?> packet, int npcId, Inventory inventory) {
        if (!packet.getClass().getSimpleName().equals("PacketPlayInUseEntity")) return;

        int id = (Integer)getValue(packet, "a");

        if (npcId != id) return;
        if (!getValue(packet, "action").toString().equals("INTERACT")) return;

        player.openInventory(inventory);
    }

    @Override
    public void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue(Object obj, String name) {
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
