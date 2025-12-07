package aoc2022.day24;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValleyMap {

    private final Position entrance;
    private final Position exit;
    private final ArrayListMultimap<Position, Direction> blizzards;
    private final int maxRow;
    private final int maxCol;

    public ValleyMap(Position entrance, Position exit, ArrayListMultimap<Position, Direction> blizzards, int maxRow, int maxCol) {
        this.entrance = entrance;
        this.exit = exit;
        this.blizzards = blizzards;
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    public static ValleyMap init(List<String> input) {
        ArrayListMultimap<Position, Direction> blizzards = ArrayListMultimap.create();
        Position entrance = null, exit = null;
        int maxRow = input.size() - 2, maxCol = input.get(0).length() - 2;

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

                if (col >= 1 && col <= maxCol && value != '.') {
                    Direction direction = switch (value) {
                        case '^' -> Direction.UP;
                        case 'v' -> Direction.DOWN;
                        case '>' -> Direction.RIGHT;
                        case '<' -> Direction.LEFT;
                        default -> null;
                    };
                    blizzards.put(new Position(row, col), direction);
                }
            }
        }

        return new ValleyMap(entrance, exit, blizzards, maxRow, maxCol);
    }

    public int solve() {
        LinkedList<QueueKey> queue = new LinkedList<>();
        queue.add(new QueueKey(0, entrance));
        Set<SeenKey> seen = new HashSet<>();
        int lcd = maxRow * maxCol / gcd(maxRow, maxCol);

        while (!queue.isEmpty()) {
            QueueKey queueKey = queue.pollFirst();
            Position curPos = queueKey.position;
            int move = queueKey.move + 1;

            if (curPos.moveExpedition(Direction.DOWN, maxRow + 1, maxCol).equals(exit)) {
                return move;
            }

            ArrayListMultimap<Position, Direction> nextBlizzards = nextBlizzards(move);

            for (Direction direction : Direction.values()) {
                Position newPos = curPos.moveExpedition(direction, maxRow, maxCol);
                if (newPos != null && nextBlizzards.get(newPos).isEmpty()) {
                    SeenKey seenKey = new SeenKey(newPos, move % lcd);
                    if (!seen.contains(seenKey)) {
                        seen.add(seenKey);
                        queue.add(new QueueKey(move, newPos));
                    }
                }
            }
        }
        throw new RuntimeException("Ouch");
    }

    private ArrayListMultimap<Position, Direction> nextBlizzards(Multimap<Position, Direction> blizzards) {
        ArrayListMultimap<Position, Direction> nextBlizzards = ArrayListMultimap.create();
        for (Map.Entry<Position, Direction> entry : blizzards.entries()) {
            Position position = entry.getKey();
            Direction direction = entry.getValue();
            if (direction != null) {
                nextBlizzards.put(position.moveBlizzard(direction, maxRow, maxCol), direction);
            }
        }
        return nextBlizzards;
    }

    private ArrayListMultimap<Position, Direction> nextBlizzards(int moves) {
        ArrayListMultimap<Position, Direction> nextBlizzards = ArrayListMultimap.create();
        for (Map.Entry<Position, Direction> entry : blizzards.entries()) {
            Position position = entry.getKey();
            Direction direction = entry.getValue();
            if (direction != null) {
                Position newPosition = position;
                for (int i = 1; i <= moves; i++) {
                    newPosition = newPosition.moveBlizzard(direction, maxRow, maxCol);
                }
                nextBlizzards.put(newPosition, direction);
            }
        }
        return nextBlizzards;
    }

    public void print(Position curPos, ArrayListMultimap<Position, Direction> blizzards) {
        System.out.println(blizzards.entries());

        for (int row = 0; row <= maxRow + 1; row++) {
            for (int col = 0; col <= maxCol + 1; col++) {
                Position pos = new Position(row, col);
                if (pos.equals(curPos)) {
                    System.out.print('E');
                } else if (pos.equals(exit)) {
                    System.out.print('F');
                } else if (row == 0 || row == maxRow + 1 || col == 0 || col == maxCol + 1) {
                    System.out.print('#');
                } else {
                    List<Direction> blizzardsAtPos = blizzards.get(pos);
                    if (blizzardsAtPos.isEmpty()) {
                        System.out.print('.');
                    } else if (blizzardsAtPos.size() == 1) {
                        switch (blizzardsAtPos.get(0)) {
                            case UP -> {
                                System.out.print('^');
                            }
                            case DOWN -> {
                                System.out.print('v');
                            }
                            case LEFT -> {
                                System.out.print('<');
                            }
                            case RIGHT -> {
                                System.out.print('>');
                            }
                        }
                    } else {
                        System.out.print(blizzardsAtPos.size());
                    }
                }
            }
            System.out.println();
        }
    }

    private static int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    private record QueueKey(int move, Position position) {}
    private record SeenKey(Position position, int blizzardsHash) {}
}
