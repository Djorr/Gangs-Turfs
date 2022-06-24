package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerQuitEventListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerQuitEventListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (!this.combatTagController.isInCombat(player)) return;
        this.combatTagController.getCombatTagManager().getCombatLogger(player).setServerQuitInCombat(true);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR))
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        }

        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack != null && !itemStack.getType().equals(Material.AIR))
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        }

        player.getInventory().setArmorContents(null);
        player.getInventory().clear();

        this.combatTagController.getCombatTagManager().deleteCombatlogger(player);
    }
}
