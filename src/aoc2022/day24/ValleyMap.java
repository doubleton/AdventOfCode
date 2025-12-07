package aoc2022.day24;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ValleyMap {

    private final Position entrance;
    private final Position exit;
    private final List<Blizzard> blizzards;
    private final int maxRow;
    private final int maxCol;
    private final int cycleLength;
    private final List<boolean[][]> blizzardStates;

    private ValleyMap(Position entrance, Position exit, List<Blizzard> blizzards, int maxRow, int maxCol) {
        this.entrance = entrance;
        this.exit = exit;
        this.blizzards = blizzards;
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.cycleLength = lcm(maxRow, maxCol);
        this.blizzardStates = precomputeBlizzardStates();
    }

    public static ValleyMap init(List<String> input) {
        List<Blizzard> blizzards = new ArrayList<>();
        Position entrance = null, exit = null;
        int maxRow = input.size() - 2, maxCol = input.getFirst().length() - 2;

        for (int row = 0; row < input.size(); row++) {
            char[] chars = input.get(row).toCharArray();
            for (int col = 0; col < chars.length; col++) {
                char value = chars[col];

                if (row == 0 && value == '.') {
                    entrance = new Position(row, col);
                    continue;
                }

                if (row == input.size() - 1 && value == '.') {
                    exit = new Position(row, col);
                    continue;
                }

                if (row >= 1 && row <= maxRow && col >= 1 && col <= maxCol) {
                    Direction direction = switch (value) {
                        case '^' -> Direction.UP;
                        case 'v' -> Direction.DOWN;
                        case '>' -> Direction.RIGHT;
                        case '<' -> Direction.LEFT;
                        default -> null;
                    };
                    if (direction != null) {
                        blizzards.add(new Blizzard(row, col, direction));
                    }
                }
            }
        }

        return new ValleyMap(entrance, exit, blizzards, maxRow, maxCol);
    }

    public int solve() {
        return travel(entrance, exit, 0);
    }

    public int solvePart2() {
        int toExit = travel(entrance, exit, 0);
        int backToStart = travel(exit, entrance, toExit);
        return travel(entrance, exit, backToStart);
    }

    private int travel(Position start, Position target, int startMinute) {
        if (start.equals(target)) {
            return startMinute;
        }

        LinkedList<QueueKey> queue = new LinkedList<>();
        queue.add(new QueueKey(startMinute, start));
        Set<SeenKey> seen = new HashSet<>();
        seen.add(new SeenKey(start, startMinute % cycleLength));

        int[][] directions = new int[][] {
                {0, 0},    // wait
                {-1, 0},   // up
                {1, 0},    // down
                {0, -1},   // left
                {0, 1}     // right
        };

        while (!queue.isEmpty()) {
            QueueKey queueKey = queue.pollFirst();
            Position curPos = queueKey.position;
            int nextMinute = queueKey.minute + 1;
            int blizzardIndex = nextMinute % cycleLength;
            boolean[][] blizzardsAtNextMinute = blizzardStates.get(blizzardIndex);

            for (int[] dir : directions) {
                int newRow = curPos.row() + dir[0];
                int newCol = curPos.col() + dir[1];
                Position newPos = new Position(newRow, newCol);

                if (newPos.equals(target)) {
                    return nextMinute;
                }

                if (!isValidMove(newPos)) {
                    continue;
                }

                if (hasBlizzard(newPos, blizzardsAtNextMinute)) {
                    continue;
                }

                SeenKey seenKey = new SeenKey(newPos, blizzardIndex);
                if (seen.add(seenKey)) {
                    queue.add(new QueueKey(nextMinute, newPos));
                }
            }
        }
        throw new IllegalStateException("No path found");
    }

    private boolean isValidMove(Position pos) {
        return isInside(pos) || pos.equals(entrance) || pos.equals(exit);
    }

    private boolean hasBlizzard(Position pos, boolean[][] blizzardsAtMinute) {
        if (pos.row() < 1 || pos.row() > maxRow || pos.col() < 1 || pos.col() > maxCol) {
            return false;
        }
        return blizzardsAtMinute[pos.row()][pos.col()];
    }

    private boolean isInside(Position pos) {
        return pos.row() >= 1 && pos.row() <= maxRow && pos.col() >= 1 && pos.col() <= maxCol;
    }

    private List<boolean[][]> precomputeBlizzardStates() {
        List<boolean[][]> states = new ArrayList<>(cycleLength);

        for (int minute = 0; minute < cycleLength; minute++) {
            boolean[][] grid = new boolean[maxRow + 1][maxCol + 1];
            for (Blizzard blizzard : blizzards) {
                int row = blizzard.row();
                int col = blizzard.col();
                Direction direction = blizzard.direction();

                int targetRow = row;
                int targetCol = col;

                switch (direction) {
                    case UP -> targetRow = 1 + mod(row - 1 - minute, maxRow);
                    case DOWN -> targetRow = 1 + mod(row - 1 + minute, maxRow);
                    case LEFT -> targetCol = 1 + mod(col - 1 - minute, maxCol);
                    case RIGHT -> targetCol = 1 + mod(col - 1 + minute, maxCol);
                    case WAIT -> {
                    }
                }

                grid[targetRow][targetCol] = true;
            }
            states.add(grid);
        }

        return states;
    }

    private int mod(int value, int limit) {
        int result = value % limit;
        return result < 0 ? result + limit : result;
    }

    private static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private record Blizzard(int row, int col, Direction direction) {}
    private record QueueKey(int minute, Position position) {}
    private record SeenKey(Position position, int blizzardHash) {}
}
