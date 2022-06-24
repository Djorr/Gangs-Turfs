package nl.rubixstudios.gangsturfs.combat.task;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.combat.CombatTagObject;
import nl.rubixstudios.gangsturfs.utils.Tasks;
import org.bukkit.Bukkit;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CombatTaskImpl implements CombatTask {

    private final GangsTurfs instance;
    private final CombatTagController controller;

    private ScheduledThreadPoolExecutor executor;
    private ScheduledFuture<?> task;

    public CombatTaskImpl(GangsTurfs instance, CombatTagController controller) {
        this.instance = instance;
        this.controller = controller;

        Tasks.syncLater(this::setupTasks, 10L);
    }

    private void setupTasks() {
        this.executor = new ScheduledThreadPoolExecutor(2, Tasks.newThreadFactory("Combat Thread - %d"));
        this.executor.setRemoveOnCancelPolicy(true);

        this.task = this.executor.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
    }

    public void cancel() {
        if(this.task != null) this.task.cancel(true);
        if(this.executor != null) this.executor.shutdownNow();
    }

    @Override
    public void run() {
        if (this.controller.getCombatTagManager().getCombatLoggers().isEmpty()) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
            final CombatTagObject combatTagObject = this.controller.getCombatTagManager().getCombatLogger(player);
            if (combatTagObject == null) return;

            if (diff(combatTagObject) < CombatTagController.getInstance().getTagCooldown()) return;
            this.controller.removeFromCombatTags(player);
        });
    }

    private int diff(CombatTagObject combatTagObject) {
        final long timeInCombat = combatTagObject.getTimeInCombat();
        return (int) ((System.currentTimeMillis() - timeInCombat) / 1000L);
    }
}
