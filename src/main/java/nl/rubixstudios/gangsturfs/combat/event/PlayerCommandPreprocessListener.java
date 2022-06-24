package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.data.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class PlayerCommandPreprocessListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerCommandPreprocessListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (combatTagController.isInCombat(player)) {

            final List<String> disabledCommands = Config.COMBAT_TAG_DISABLED_COMMANDS;
            if (!disabledCommands.contains(event.getMessage())) return;

            event.setCancelled(true);
        }
    }


}
