package nl.rubixstudios.gangsturfs.combat;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class CombatTagObject {

    private @Setter UUID playerId;
    private @Setter long timeInCombat;

    private @Setter boolean serverQuitInCombat;
    private @Setter long timeLeftInCombat;

    private @Setter boolean diedInCombat;

    public CombatTagObject() {
        this.timeInCombat = System.currentTimeMillis();
        this.serverQuitInCombat = false;
    }

    public boolean isInCombat() {
        return this.serverQuitInCombat;
    }
}
