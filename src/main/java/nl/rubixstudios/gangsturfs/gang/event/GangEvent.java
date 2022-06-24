package nl.rubixstudios.gangsturfs.gang.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GangEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    GangEvent() { }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }
}
