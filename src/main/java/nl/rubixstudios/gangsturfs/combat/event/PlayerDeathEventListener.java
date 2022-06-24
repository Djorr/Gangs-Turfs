package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathEventListener implements Listener {

    private final CombatTagController combatTagController;

    public PlayerDeathEventListener() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        if (!this.combatTagController.isInCombat(player)) return;
        event.setKeepInventory(false);

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

        event.setKeepInventory(true);

        this.combatTagController.getCombatTagManager().deleteCombatlogger(player);
    }
}
