package nl.rubixstudios.gangsturfs.gang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rubixstudios.gangsturfs.utils.StringUtils;

@AllArgsConstructor
public enum Role {

    THUG(""),
    SUPPLIER(""),
    HITMAN(""),
    ADVISOR("*"),
    LEADER("**");

    @Getter
    private final String prefix;

    public boolean isAtLeast(Role role) {
        return this.ordinal() >= role.ordinal();
    }

    public static String getName(Role role) {
        return StringUtils.capitalize(role.name().toLowerCase());
    }

    public String getName() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }

    public Role getPromote() {
        if (this == Role.THUG) {
            return SUPPLIER;
        } else if (this == Role.SUPPLIER) {
            return HITMAN;
        } else if (this == Role.HITMAN) {
            return ADVISOR;
        }
        return null;
    }

    public Role getDemote() {
        if (this == Role.ADVISOR) {
            return HITMAN;
        } else if (this == Role.HITMAN) {
            return SUPPLIER;
        } else if (this == Role.SUPPLIER) {
            return THUG;
        }
        return null;
    }
}
