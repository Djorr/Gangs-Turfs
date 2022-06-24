package nl.rubixstudios.gangsturfs.combat.event;

import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerEntityDamageByEntityEvent implements Listener {

    private final CombatTagController combatTagController;

    public PlayerEntityDamageByEntityEvent() {
        this.combatTagController = CombatTagController.getInstance();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();

            if (event.getDamager() instanceof Player) {
                final Player damager = (Player) event.getDamager();
                if (damager == player) return;

                if (this.combatTagController.isInCombat(damager)) {
                    this.combatTagController.updateCombatTags(damager, player);
                } else {
                    this.combatTagController.addToCombatTags(damager, player);
                }
            } else if (event.getDamager() instanceof Projectile) {
                final Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getShooter() instanceof Player) {
                    final Player damager = (Player) projectile.getShooter();
                    if (damager == player) return;

                    if (this.combatTagController.isInCombat(damager)) {
                        this.combatTagController.updateCombatTags(damager, player);
                    } else {
                        this.combatTagController.addToCombatTags(damager, player);
                    }
                }
            }
        }
    }
}
