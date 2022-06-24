package nl.rubixstudios.gangsturfs.npc.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ViewList {

    private final List<Integer> viewList;

    public ViewList() {
        this.viewList = new ArrayList<>();
    }

}
