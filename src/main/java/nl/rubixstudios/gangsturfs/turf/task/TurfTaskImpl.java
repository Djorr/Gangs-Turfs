package nl.rubixstudios.gangsturfs.turf.task;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfData;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.Tasks;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TurfTaskImpl implements TurfTask {

    private final GangsTurfs gangsTurfs;
    private final TurfController turfController;

    private ScheduledThreadPoolExecutor executor;
    private ScheduledFuture<?> updater;

    public TurfTaskImpl(GangsTurfs gangsTurfs, TurfController turfController) {
        this.gangsTurfs = gangsTurfs;
        this.turfController = turfController;

        Tasks.syncLater(this::setupTasks, 10L);
    }

    private void setupTasks() {
        this.executor = new ScheduledThreadPoolExecutor(2, Tasks.newThreadFactory("Turf Thread - %d"));
        this.executor.setRemoveOnCancelPolicy(true);

        this.updater = this.executor.scheduleAtFixedRate(this, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public void cancel() {
        if(this.updater != null) this.updater.cancel(true);
        if(this.executor != null) this.executor.shutdownNow();
    }

    @Override
    public void run() {
        try {
            if (this.turfController.getTurfManager().getTurfs().isEmpty()) return;

            final TurfData turfData = this.turfController.getTurfManager().getTurfs().stream().filter(TurfData::isTurfStarted).findFirst().orElse(null);
            if (turfData == null) return;

            if (System.currentTimeMillis() - turfData.getTurfStartedTime() >= (Config.TURF_GAME_DURATION * 1000L)) {
                this.turfController.stopTurf(turfData);
                return;
            }

            int messageTurf = Config.TURF_TURF_ACTIVE_MESSAGE_COOLDOWN;
            if (System.currentTimeMillis() - turfData.getLastMessageTime() > (messageTurf * 1000L)) {

                Language.TURF_GAME_MESSAGES_TURF_BUSY_MESSAGE.forEach(message -> Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendMessage(message
                            .replace("<coords>", "x" + turfData.getTurfRegion().getBlockX() + " y" + turfData.getTurfRegion().getBlockY() + " z" + turfData.getTurfRegion().getBlockZ())
                            .replace("<x>", "x" + turfData.getTurfRegion().getBlockX())
                            .replace("<y>", "y" + turfData.getTurfRegion().getBlockY())
                            .replace("<z>", "z" + turfData.getTurfRegion().getBlockZ())
                            .replace("<city>", turfData.getTurfRegion().getWorld().getName())
                    );
                }));

                turfData.setLastMessageTime(System.currentTimeMillis());
            }

            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (WorldGuardUtil.isInsideTurfRegion(player.getLocation())) {
                        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                            return;
                        }

                        if (!turfData.getPlayersInTurf().contains(player.getUniqueId())) {
                            turfData.getPlayersInTurf().add(player.getUniqueId());
                        }
                    } else {
                        turfData.getPlayersInTurf().remove(player.getUniqueId());
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
