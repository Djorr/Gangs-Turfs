package nl.rubixstudios.gangsturfs.gang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.rubixstudios.gangsturfs.utils.Color;

@AllArgsConstructor
public enum Relation {

    MEMBER(Color.translate("&e")),
    ENEMY(Color.translate("&c"));

    @Getter
    private final String color;
}
