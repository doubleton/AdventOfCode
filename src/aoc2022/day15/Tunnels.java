package aoc2022.day15;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tunnels {

    private final static Pattern INPUT_PATTERN = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    private final Map<Pos, Integer> distanceBySensors = new HashMap<>();
    private final Set<Pos> beacons = new HashSet<>();

    public void init(List<String> input) {
        for (String line : input) {
            Matcher matcher = INPUT_PATTERN.matcher(line);
            if (matcher.find()) {
                int sx = Integer.parseInt(matcher.group(1));
                int sy = Integer.parseInt(matcher.group(2));
                int bx = Integer.parseInt(matcher.group(3));
                int by = Integer.parseInt(matcher.group(4));
                Pos ps = new Pos(sx, sy);
                Pos pb = new Pos(bx, by);

                int distance = distance(ps, pb);
                distanceBySensors.put(ps, distance);
                beacons.add(pb);
            }
        }
    }

    private static int distance(Pos p1, Pos p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public int countNbsAtRow(int row) {
        Set<Integer> nbs = new HashSet<>();

        for (Map.Entry<Pos, Integer> entry : distanceBySensors.entrySet()) {
            Pos pos = entry.getKey();
            int distance = entry.getValue();

            int dy = Math.abs(row - pos.y);
            if (dy < distance) {
                int dx = distance - dy;

                for (int i = pos.x - dx; i <= pos.x + dx; i++) {
                    nbs.add(i);
                }
            }
        }

        removePositions(nbs, row, beacons);
        removePositions(nbs, row, distanceBySensors.keySet());

        return nbs.size();
    }

    private static void removePositions(Set<Integer> set, int row, Collection<Pos> positions) {
        set.removeAll(positions.stream().filter(pos -> pos.y == row).mapToInt(Pos::getX).boxed().collect(Collectors.toSet()));
    }

    public long findTuningFrequency(int max) {
        for (int row = 0; row <= max; row++) {
            BitSet bits = new BitSet(max + 1);

            for (Map.Entry<Pos, Integer> entry : distanceBySensors.entrySet()) {
                Pos pos = entry.getKey();
                int distance = entry.getValue();
                int dy = Math.abs(row - pos.y);
                if (dy < distance) {
                    int dx = distance - dy;
                    int minX = Math.max(pos.x - dx, 0);
                    int maxX = Math.min(pos.x + dx, max);
                    bits.set(minX, maxX + 1);
                }
            }

            if (bits.cardinality() != max + 1) {
                int x = bits.nextClearBit(0);
                return x * 4_000_000L + row;
            }

            System.out.println("Row " + row);
        }

        return -1;
    }

}
