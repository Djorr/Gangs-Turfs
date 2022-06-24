package nl.rubixstudios.gangsturfs.gang.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

@Getter
public class GangCreateEvent extends GangEvent implements Cancellable {

    private final String gangName;
    private final CommandSender sender;
    private final GangType gangType;
    @Setter
    private boolean cancelled;

    public GangCreateEvent(String gangName, CommandSender sender, GangType gangType) {
        this.gangName = gangName;
        this.sender = sender;
        this.gangType = gangType;

        Bukkit.getPluginManager().callEvent(this);
    }

    public enum GangType {
        PLAYER_GANG, SYSTEM_GANG
    }
}