package nl.rubixstudios.gangsturfs.npc;

import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.npc.listener.GNPCClickEventListener;
import nl.rubixstudios.gangsturfs.npc.type.NPCType;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NPCController implements Listener, ManagerEnabler {

    private static @Getter NPCController instance;
    private final @Getter NPCManager npcManager;

    public NPCController() {
        instance = this;
        this.npcManager = NPCManager.getInstance();

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new GNPCClickEventListener(), GangsTurfs.getInstance());
    }

    public void disable() {
        this.npcManager.disable();
    }

    // Create or Remove NPC

    public void createNPC(CommandSender sender, String name) {
        final Player player = (Player) sender;

        final NPC customNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, name);
        final NPCData npcData = new NPCData();

        if (name.contains("gang")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(GangsTurfs.getInstance(), () -> {
                customNpc.spawn(player.getLocation());
                customNpc.setName(NPCType.GANG.getNpcName());
            });
            npcData.setNpcType(NPCType.GANG);
        } else if (name.contains("turf")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(GangsTurfs.getInstance(), () -> {
                customNpc.spawn(player.getLocation());
                customNpc.setName(NPCType.TURF.getNpcName());
            });
            npcData.setNpcType(NPCType.TURF);
        }
        npcData.setNpcId(customNpc.getId());
        npcData.setNpcName(customNpc.getName());
        npcData.setNpcUuid(customNpc.getUniqueId());
        npcData.setNpcLocation(player.getLocation());

        this.npcManager.createNpc(npcData);
    }

    public void deleteNpc(CommandSender sender, String name) {
        final NPCData npcData = this.npcManager.getNpc(name);
        if (npcData == null) return;


        this.despawnNpc(npcData, true);
        this.npcManager.removeNpc(npcData);
    }

    // Spawn or Despawn NPC

    public void spawnNpc(NPCData npcData) {
        final NPC customNpc = CitizensAPI.getNPCRegistry().getByUniqueId(npcData.getNpcUuid());
        if (customNpc == null) return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(GangsTurfs.getInstance(), () -> customNpc.spawn(npcData.getNpcLocation()));
    }

    public void despawnNpc(NPCData npcData, boolean destroy) {
        final NPC customNpc = CitizensAPI.getNPCRegistry().getByUniqueId(npcData.getNpcUuid());
        if (customNpc == null) return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(GangsTurfs.getInstance(), customNpc::despawn);
        if (destroy) CitizensAPI.getNPCRegistry().deregister(customNpc);
    }
}
