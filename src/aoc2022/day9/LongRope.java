package aoc2022.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LongRope implements Rope {

    private final ShortRope head;
    private final List<ShortRope> parts;

    public LongRope(int length) {
        this.parts = new ArrayList<>(length - 1);

        head = new ShortRope();

        ShortRope current = head;
        for (int i = 0; i < length - 2; i++) {
            ShortRope part = new ShortRope(current.getTail());
            parts.add(part);
            current = part;
        }
    }

    public void perform(String action) {
        head.perform(action);
        for (ShortRope part : parts) {
            part.moveTail();
        }
    }

    public Set<String> getTailPositions() {
        return parts.get(parts.size() - 1).getTailPositions();
    }
}
