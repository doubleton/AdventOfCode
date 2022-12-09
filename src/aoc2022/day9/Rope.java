package aoc2022.day9;

import java.util.Set;

public interface Rope {

    void perform(String action);

    Set<String> getTailPositions();
}
