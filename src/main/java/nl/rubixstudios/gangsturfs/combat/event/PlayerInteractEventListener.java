package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.data.Config;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Djorr
 * @created 26/06/2022 - 21:10
 * @project Gangs&Turfs
 */
public class PlayerInteractEventListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerInteractEventListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        final boolean isPlayerInCombat = this.combatTagController.isInCombat(player);
        final boolean playerTriesToOpenEc = clickedBlock.getType() == Material.ENDER_CHEST;

        if (!Config.COMBAT_TAG_DISABLE_ENDERCHESTS && isPlayerInCombat && playerTriesToOpenEc) {
            event.setCancelled(true);
        }

        final boolean playerTriesToOpenBackpack = Config.COMBAT_TAG_DISABLED_ITEMS.stream().anyMatch(item -> player.getItemInHand().getType() == Material.getMaterial(item));

        if (!Config.COMBAT_TAG_DISABLE_ENDERCHESTS && isPlayerInCombat && playerTriesToOpenBackpack) {
            event.setCancelled(true);
        }
    }
}
