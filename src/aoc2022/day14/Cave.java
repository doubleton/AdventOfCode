package aoc2022.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Cave {

    private static final Pos START_POS = new Pos(0, 500);

    private Point[][] grid;

    int width;
    int height;

    public void init(List<String> input, boolean addFloor) {
        int maxI = Integer.MIN_VALUE;
        int minJ = Integer.MAX_VALUE;
        int maxJ = Integer.MIN_VALUE;

        List<RockStructure> rockStructures = new ArrayList<>(input.size());
        for (String line : input) {
            RockStructure rs = new RockStructure(line);
            if (rs.minJ < minJ) {
                minJ = rs.minJ;
            }
            if (rs.maxJ > maxJ) {
                maxJ = rs.maxJ;
            }
            if (rs.maxI > maxI) {
                maxI = rs.maxI;
            }
            rockStructures.add(rs);
        }

        if (addFloor) {
            maxI += 2;
            minJ -= maxI;
            maxJ += maxI;

            rockStructures.add(new RockStructure(minJ + "," + maxI + " -> " + maxJ + "," + maxI)); // minJ,maxI -> maxJ,maxI
        }

        width = maxJ - minJ + 1;
        height = maxI + 1;

        grid = new Point[height][width];
        initAir(width);
        initStart(minJ);
        initRocks(rockStructures, minJ);
    }

    private void initRocks(List<RockStructure> rockStructures, int minJ) {
        rockStructures.forEach(rs -> {
            rs.normalize(minJ);
            addRocks(rs);
        });
    }

    private void initStart(int minJ) {
        START_POS.normalize(minJ);
        grid[START_POS.i][START_POS.j] = Point.START;
    }

    private void initAir(int width) {
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Point[width];
            Arrays.fill(grid[i], Point.AIR);
        }
    }

    private void addRocks(RockStructure rockStructure) {
        Iterator<Pos> it = rockStructure.rocks.iterator();

        Pos start = it.next();
        Pos end = start;

        while (it.hasNext()) {
            start = end;
            end = it.next();

            for (int i = Math.min(start.i, end.i); i <= Math.max(start.i, end.i); i++) {
                for (int j = Math.min(start.j, end.j); j <= Math.max(start.j, end.j); j++) {
                    grid[i][j] = Point.ROCK;
                }
            }
        }
    }

    public void simulateSand() {
        do {
            Pos sandPos = new Pos(START_POS.i, START_POS.j); // generate a new sand unit

            StepResult result;
            do {
                result = moveSand(sandPos); // move sand unit one step a time till it can be moved
            } while (result == StepResult.MOVED);

            if (result == StepResult.REST) {
                grid[sandPos.i][sandPos.j] = Point.SAND; // sand unit comes to rest

                if (sandPos.equals(START_POS)) {
                    result = StepResult.FAILED; // blocked the source
                }
            }

            if (result == StepResult.FAILED) {
                break; // no more sand unit can be added
            }
        } while (true);
    }

    private StepResult moveSand(Pos pos) {
        try {
            if (grid[pos.i + 1][pos.j] == Point.AIR) {
                pos.i++;
                return StepResult.MOVED; // fall one step down
            }

            if (grid[pos.i + 1][pos.j - 1] == Point.AIR) {
                pos.i++;
                pos.j--;
                return StepResult.MOVED; // fall one step down and to the left
            }

            if (grid[pos.i + 1][pos.j + 1] == Point.AIR) {
                pos.i++;
                pos.j++;
                return StepResult.MOVED; // fall one step down and to the right
            }

            return StepResult.REST;
        } catch (ArrayIndexOutOfBoundsException e) {
            return StepResult.FAILED;
        }
    }

    public void print() {
        for (Point[] points : grid) {
            for (Point point : points) {
                System.out.print(point);
            }
            System.out.println();
        }
    }

    public int countSand() {
        int total = 0;
        for (Point[] points : grid) {
            for (Point point : points) {
                if (point == Point.SAND) {
                    total++;
                }
            }
        }
        return total;
    }

    private enum StepResult {
        MOVED, REST, FAILED
    }

    public enum Point {
        AIR, ROCK, SAND, START;

        @Override
        public String toString() {
            return switch (this) {
                case AIR -> ".";
                case ROCK -> "#";
                case SAND -> "o";
                case START -> "+";
            };
        }
    }
}
