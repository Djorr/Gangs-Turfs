package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinEventListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerJoinEventListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.combatTagController.getCombatTagManager().getCombatLogger(player) == null) return;
        if (!this.combatTagController.getCombatTagManager().getCombatLogger(player).isServerQuitInCombat()) return;

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        this.combatTagController.getCombatTagManager().deleteCombatlogger(player);
    }
}
