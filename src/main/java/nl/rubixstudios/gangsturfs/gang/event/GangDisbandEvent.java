package nl.rubixstudios.gangsturfs.gang.event;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.Gang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

@Getter
public class GangDisbandEvent extends GangEvent implements Cancellable {

    private final Gang gang;
    private final CommandSender sender;
    @Setter
    private boolean cancelled;

    public GangDisbandEvent(Gang gang, CommandSender sender) {
        this.gang = gang;
        this.sender = sender;

        Bukkit.getPluginManager().callEvent(this);
    }
}