package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnEventListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerRespawnEventListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        if (!this.combatTagController.isInCombat(player)) return;

       this.combatTagController.getCombatTagManager().deleteCombatlogger(player);
    }

}