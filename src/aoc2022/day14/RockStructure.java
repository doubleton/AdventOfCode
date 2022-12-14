package aoc2022.day14;

import java.util.ArrayList;
import java.util.List;

public class RockStructure {

    final List<Pos> rocks = new ArrayList<>();

    int minJ;
    int maxJ;
    int maxI;

    public RockStructure(String input) {
        minJ = Integer.MAX_VALUE;
        maxJ = Integer.MIN_VALUE;

        maxI = Integer.MIN_VALUE;

        String[] parts = input.split(" -> ");
        for (String part : parts) {
            String[] pos = part.split(",");
            int i = Integer.parseInt(pos[1]);
            int j = Integer.parseInt(pos[0]);
            rocks.add(new Pos(i, j));
            if (j < minJ) {
                minJ = j;
            }
            if (j > maxJ) {
                maxJ = j;
            }
            if (i > maxI) {
                maxI = i;
            }
        }
    }

    public void normalize(int minJ) {
        rocks.forEach(rock -> rock.normalize(minJ));
    }
}
