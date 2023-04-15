package aoc2022.day23;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Field {

    final Set<Position> elfPositions;
    final LinkedList<Direction> directions = new LinkedList<>(Arrays.stream(Direction.values()).toList());

    public Field(Set<Position> elfPositions) {
        this.elfPositions = elfPositions;
    }

    public static Field init(List<String> input) {
        Set<Position> elfPositions = new HashSet<>();
        for (int row = 0; row < input.size(); row++) {
            String string = input.get(row);
            for (int col = 0; col < string.length(); col++) {
                char ch = string.charAt(col);
                if (ch == '#') {
                    elfPositions.add(new Position(row, col));
                }
            }
        }

        return new Field(elfPositions);
    }

    public void move(int count) {
        for (int i = 0; i < count; i++) {
            move();
        }
    }

    public boolean move() {
        Map<Position, Position> nextPositions = new HashMap<>();

        for (Position elfPosition : elfPositions) {
            Position nextPosition = findNextPosition(elfPosition);
            nextPositions.put(elfPosition, nextPosition);
        }

        boolean moved = false;

        Collection<Position> newPositions = nextPositions.values();
        for (Map.Entry<Position, Position> entry : nextPositions.entrySet()) {
            Position curPosition = entry.getKey();
            Position nextPosition = entry.getValue();
            if (nextPosition != null && !curPosition.equals(nextPosition) && Collections.frequency(newPositions, nextPosition) == 1) {
                elfPositions.remove(curPosition);
                elfPositions.add(nextPosition);
                moved = true;
            }
        }

        rotateDirections();

        return moved;
    }

    private Position findNextPosition(Position position) {
        if (!elfPositions.contains(position.nw()) &&
                !elfPositions.contains(position.n()) &&
                !elfPositions.contains(position.ne()) &&
                !elfPositions.contains(position.sw()) &&
                !elfPositions.contains(position.s()) &&
                !elfPositions.contains(position.se()) &&
                !elfPositions.contains(position.w()) &&
                !elfPositions.contains(position.e())) {
            return null;
        }

        for (Direction direction : directions) {
            switch (direction) {
                case N -> {
                    if (!elfPositions.contains(position.nw()) &&
                        !elfPositions.contains(position.n()) &&
                        !elfPositions.contains(position.ne())) {
                        return position.n();
                    }
                }
                case S -> {
                    if (!elfPositions.contains(position.sw()) &&
                        !elfPositions.contains(position.s()) &&
                        !elfPositions.contains(position.se())) {
                        return position.s();
                    }
                }
                case W -> {
                    if (!elfPositions.contains(position.nw()) &&
                        !elfPositions.contains(position.w()) &&
                        !elfPositions.contains(position.sw())) {
                        return position.w();
                    }
                }
                case E -> {
                    if (!elfPositions.contains(position.ne()) &&
                        !elfPositions.contains(position.e()) &&
                        !elfPositions.contains(position.se())) {
                        return position.e();
                    }
                }
            }
        }
        return null;
    }

    private void rotateDirections() {
        directions.addLast(directions.pollFirst());
    }

    private int[] findBoundaries() {
        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;

        for (Position pos : elfPositions) {
            if (pos.row() < minRow) {
                minRow = pos.row();
            }
            if (pos.row() > maxRow) {
                maxRow = pos.row();
            }
            if (pos.col() < minCol) {
                minCol = pos.col();
            }
            if (pos.col() > maxCol) {
                maxCol = pos.col();
            }
        }
        return new int[] { minRow, maxRow, minCol, maxCol };
    }

    public void print() {
        int[] boundaries = findBoundaries();
        for (int row = boundaries[0]; row <= boundaries[1]; row++) {
            for (int col = boundaries[2]; col <= boundaries[3]; col++) {
                if (elfPositions.contains(new Position(row, col))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public int countEmptyGround() {
        int[] boundaries = findBoundaries();
        int square = (boundaries[1] - boundaries[0] + 1) * (boundaries[3] - boundaries[2] + 1);
        return square - elfPositions.size();
    }
}
