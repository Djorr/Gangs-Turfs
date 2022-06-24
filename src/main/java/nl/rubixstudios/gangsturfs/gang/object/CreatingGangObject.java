package nl.rubixstudios.gangsturfs.gang.object;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CreatingGangObject {

    private UUID playerId;
    private long startedTime;

}
