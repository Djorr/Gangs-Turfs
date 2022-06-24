package nl.rubixstudios.gangsturfs.combat.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerClckListener implements Listener {

    public PlayerClckListener() { this.listenPackets(); }

    public void listenPackets() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(GangsTurfs.getInstance(), ListenerPriority.HIGH, PacketType.Play.Server.BLOCK_ACTION)
        {
            @Override
            public void onPacketSending(PacketEvent event)
            {
                Player player = event.getPlayer();
                if(!CombatTagController.getInstance().isInCombat(player)) return;
                if(event.getPacketType() != PacketType.Play.Server.BLOCK_ACTION) return;

                event.setCancelled(true);
            }
        });
    }
}
