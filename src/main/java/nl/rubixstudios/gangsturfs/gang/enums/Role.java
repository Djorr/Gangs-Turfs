package nl.rubixstudios.gangsturfs.gang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rubixstudios.gangsturfs.utils.StringUtils;

@AllArgsConstructor
public enum Role {

    MEMBER(""),
    CAPTAIN("*"),
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
        if (this == Role.MEMBER) {
            return CAPTAIN;
        }
        return null;
    }

    public Role getDemote() {
        if (this == Role.CAPTAIN) {
            return MEMBER;
        }
        return null;
    }
}
