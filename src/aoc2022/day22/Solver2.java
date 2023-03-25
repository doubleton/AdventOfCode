package aoc2022.day22;

import java.util.Map;

public class Solver2 {

    public static int solve(FieldMap map, Path path) {
        Point position = map.start;
        Facing facing = Facing.RIGHT;

        for (PathItem item : path.items) {
            if (item.kind == PathItem.PathItemKind.MOVE) {
                for (int i = 0; i < item.count; i++) {
                    PointWithFacing nextPoint = nextPoint(map, facing, position);
                    if (map.grid.get(nextPoint.point()) == FieldItem.WALL) {
                        break;
                    } else {
                        position = nextPoint.point();
                        facing = nextPoint.facing();
                    }
                }
            } else {
                facing = facing.turn(item);
            }
        }

        System.out.println("Final position: " + position);
        System.out.println("Final facing: " + facing);

        return position.row() * 1000 + position.col() * 4 + facing.cost;
    }

    private static PointWithFacing nextPoint(FieldMap map, Facing facing, Point position) {
        Map<Point, FieldItem> grid = map.grid;
        int row = position.row();
        int col = position.col();

        if (row == 1 && col >= 51 && col <= 100 && facing == Facing.UP) { // 1-5
            return new PointWithFacing(new Point(col + 100, 1), Facing.RIGHT);
        } else if (row >= 151 && row <= 200 && col == 1 && facing == Facing.LEFT) { // 1-5
            return new PointWithFacing(new Point(1, row - 100), Facing.DOWN);
        } else if (row == 1 && col >= 101 && col <= 150 && facing == Facing.UP) { // 5-7
            return new PointWithFacing(new Point(200, col - 100), Facing.UP);
        } else if (row == 200 && col >= 1 && col <= 50 && facing == Facing.DOWN) { // 5-7
            return new PointWithFacing(new Point(1, col + 100), Facing.DOWN);
        } else if (row >= 1 && row <= 50 && col == 150 && facing == Facing.RIGHT) { // 7-8
            return new PointWithFacing(new Point(151 - row, 100), Facing.LEFT);
        } else if (row >= 101 && row <= 150 && col == 100 && facing == Facing.RIGHT) { // 7-8
            return new PointWithFacing(new Point(151 - row, 150), Facing.LEFT);
        } else if (row == 50 && col >= 101 && col <= 150 && facing == Facing.DOWN) { // 2-8
            return new PointWithFacing(new Point(col - 50, 100), Facing.LEFT);
        } else if (row >= 51 && row <= 100 && col == 100 && facing == Facing.RIGHT) { // 2-8
            return new PointWithFacing(new Point(50, row + 50), Facing.UP);
        } else if (row == 150 && col >= 51 && col <= 100 && facing == Facing.DOWN) { // 3-7
            return new PointWithFacing(new Point(col + 100, 50), Facing.LEFT);
        } else if (row >= 151 && row <= 200 && col == 50 && facing == Facing.RIGHT) { // 3-7
            return new PointWithFacing(new Point(150, row - 100), Facing.UP);
        } else if (row >= 101 && row <= 150 && col == 1 && facing == Facing.LEFT) { // 1-4
            return new PointWithFacing(new Point(151 - row, 51), Facing.RIGHT);
        } else if (row >= 1 && row <= 50 && col == 51 && facing == Facing.LEFT) { // 1-4
            return new PointWithFacing(new Point(151 - row, 1), Facing.RIGHT);
        } else if (row == 101 && col >= 1 && col <= 50 && facing == Facing.UP) { // 4-6
            return new PointWithFacing(new Point(col + 50, 51), Facing.RIGHT);
        } else if (row >= 51 && row <= 100 && col == 51 && facing == Facing.LEFT) { // 4-6
            return new PointWithFacing(new Point(101, row - 50), Facing.DOWN);
        }

        Point nextPoint = null;

        switch (facing) {
            case RIGHT -> {
                nextPoint = new Point(row, ++col);
                if (col > map.cols || !grid.containsKey(nextPoint)) {
                    throw new RuntimeException("Incorrect operation: row = " + row + ", col = " + col + ", facing = " + facing);
                }
            }
            case DOWN -> {
                nextPoint = new Point(++row, col);
                if (row > map.rows || !grid.containsKey(nextPoint)) {
                    throw new RuntimeException("Incorrect operation: row = " + row + ", col = " + col + ", facing = " + facing);
                }
            }
            case LEFT -> {
                nextPoint = new Point(row, --col);
                if (col < 1 || !grid.containsKey(nextPoint)) {
                    throw new RuntimeException("Incorrect operation: row = " + row + ", col = " + col + ", facing = " + facing);
                }
            }
            case UP -> {
                nextPoint = new Point(--row, col);
                if (row < 1 || !grid.containsKey(nextPoint)) {
                    throw new RuntimeException("Incorrect operation: row = " + row + ", col = " + col + ", facing = " + facing);
                }
            }
        }

        return new PointWithFacing(nextPoint, facing);
    }

    public record PointWithFacing(Point point, Facing facing) {}
}
