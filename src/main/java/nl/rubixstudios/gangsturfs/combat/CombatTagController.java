package nl.rubixstudios.gangsturfs.combat;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.event.*;
import nl.rubixstudios.gangsturfs.combat.task.CombatTask;
import nl.rubixstudios.gangsturfs.combat.task.CombatTaskImpl;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CombatTagController implements Listener, ManagerEnabler {

    @Getter private static CombatTagController instance;
    @Getter private final CombatTagManager combatTagManager;

    @Getter private final int tagCooldown;
    private final CombatTask combatTask;

    public CombatTagController() {
        instance = this;
        this.combatTagManager = CombatTagManager.getInstance();

        this.tagCooldown = Config.COMBAT_TAG_DURATION;

        this.combatTask = new CombatTaskImpl(GangsTurfs.getInstance(), this);

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerEntityDamageByEntityEvent(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnEventListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerForceFieldListener(), GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerClckListener(), GangsTurfs.getInstance());
    }

    public void addToCombatTags(Player damager, Player victim) {
        if (!this.isInCombat(damager)) {
            this.sendCombatMessage(damager);
            this.getCombatTagManager().createNewCombatLogger(damager);
        }

        if (!this.isInCombat(victim)) {
            this.sendCombatMessage(victim);
            this.getCombatTagManager().createNewCombatLogger(victim);
        }
    }

    public void updateCombatTags(Player damager, Player victim) {
        if (this.isInCombat(damager)) {
            this.getCombatTagManager().getCombatLogger(damager).setTimeInCombat(System.currentTimeMillis());
        }

        if (this.isInCombat(victim)) {
            this.getCombatTagManager().getCombatLogger(victim).setTimeInCombat(System.currentTimeMillis());
        }
    }

    public void removeFromCombatTags(Player player) {
        this.sendCombatCancelMessage(player);
        this.getCombatTagManager().deleteCombatlogger(player);
    }

    public boolean isInCombat(Player player) {
        return this.getCombatTagManager().getCombatLoggers().stream().anyMatch(combatTagObject -> combatTagObject.getPlayerId().equals(player.getUniqueId()));
    }

    private void sendCombatMessage(Player player) {
        player.sendMessage(Color.translate("&cCombat &8» &cYou have been combat-tagged for <cooldown> seconds"
                .replace("<cooldown>", String.valueOf(getTagCooldown())).replace("<player>", player.getName())));
    }

    private void sendCombatCancelMessage(Player player) {
        player.sendMessage(Color.translate("&cCombat &8» &cYour are out of combat now, you log out safely!"));
    }

}
