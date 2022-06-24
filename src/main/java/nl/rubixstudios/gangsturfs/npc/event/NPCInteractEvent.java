package nl.rubixstudios.gangsturfs.npc.event;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.npc.entity.CustomNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
public class NPCInteractEvent extends NPCEvent implements Cancellable {

    private final Player player;
    private final int entityID;
    private final CustomNPC customNPC;

    public NPCInteractEvent(final Player player, final int id, final CustomNPC customNPC) {
        this.player = player;
        this.entityID = id;
        this.customNPC = customNPC;
    }

    public Player getWhoClicked() {
        return this.player;
    }

    public int getEntityID() {
        return this.entityID;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
