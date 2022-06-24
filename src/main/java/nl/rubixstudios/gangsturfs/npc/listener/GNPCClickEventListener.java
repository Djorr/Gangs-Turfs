package nl.rubixstudios.gangsturfs.npc.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import nl.rubixstudios.gangsturfs.games.turf.TurfController;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.npc.NPCData;
import nl.rubixstudios.gangsturfs.npc.NPCManager;
import nl.rubixstudios.gangsturfs.npc.type.NPCType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GNPCClickEventListener implements Listener {

    @EventHandler
    public void onClick(NPCRightClickEvent event) {
        final Player player = event.getClicker();
        final NPC npc = event.getNPC();

        final NPCData npcData = NPCManager.getInstance().getCitizensNPC(npc.getUniqueId());

        if (npc.getUniqueId().equals(npcData.getNpcUuid()) && npcData.getNpcType() == NPCType.TURF) {
            TurfController.getInstance().openTurfGameMenu(player, npcData.getNpcName());
        } else if (npc.getUniqueId().equals(npcData.getNpcUuid()) && npcData.getNpcType() == NPCType.GANG) {
            GangController.getInstance().openGangMenu(player);
        }
    }
}
