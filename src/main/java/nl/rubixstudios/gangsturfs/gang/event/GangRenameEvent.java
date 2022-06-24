package nl.rubixstudios.gangsturfs.gang.event;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.Gang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

@Getter
public class GangRenameEvent extends GangEvent implements Cancellable {

    private final String newName;
    private final Gang gang;
    private final CommandSender sender;
    private final boolean forceRename;
    @Setter
    private boolean cancelled;

    public GangRenameEvent(String newName, Gang gang, CommandSender sender, boolean forceRename) {
        this.newName = newName;
        this.gang = gang;
        this.sender = sender;
        this.forceRename = forceRename;

        Bukkit.getPluginManager().callEvent(this);
    }
}
