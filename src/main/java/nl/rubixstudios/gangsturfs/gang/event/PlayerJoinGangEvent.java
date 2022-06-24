package nl.rubixstudios.gangsturfs.gang.event;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;

@Getter
public class PlayerJoinGangEvent extends GangEvent implements Cancellable {

    private final GangPlayer gangPlayer;
    private final PlayerGang gang;
    @Setter
    private boolean cancelled;

    public PlayerJoinGangEvent(GangPlayer gangPlayer, PlayerGang gang) {
        this.gangPlayer = gangPlayer;
        this.gang = gang;

        Bukkit.getPluginManager().callEvent(this);
    }
}
