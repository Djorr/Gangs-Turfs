package nl.rubixstudios.gangsturfs.gang.event;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;

@Getter
public class PlayerLeaveGangEvent extends GangEvent implements Cancellable {

    private final GangPlayer gangPlayer;
    private final PlayerGang gang;
    private final LeaveReason reason;
    @Setter
    private boolean cancelled;

    public PlayerLeaveGangEvent(GangPlayer gangPlayer, PlayerGang gang, LeaveReason reason) {
        this.gangPlayer = gangPlayer;
        this.gang = gang;
        this.reason = reason;

        Bukkit.getPluginManager().callEvent(this);
    }

    public enum LeaveReason {
        KICK, LEAVE, DISBAND
    }
}