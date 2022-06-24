package nl.rubixstudios.gangsturfs.games.turf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TurfData {

    private @Setter String turfName;

    private @Setter Location turfRegion;

    private @Setter double turfWinning;
    private @Setter int turfDuration;

    private @Setter boolean turfStarted;

    private @Setter long turfStartedTime;
    private @Setter long turfEndedTime;

    private @Setter long lastMessageTime;

    private final List<UUID> playersInTurf;

    public TurfData() {
        this.turfStarted = false;
        this.playersInTurf = new ArrayList<>();
    }

}
