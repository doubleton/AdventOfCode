package aoc2022.day22;

import java.util.Map;

public class Solver {

    public static int solve(FieldMap map, Path path) {
        Point position = map.start;
        Facing facing = Facing.RIGHT;

        for (PathItem item : path.items) {
            if (item.kind == PathItem.PathItemKind.MOVE) {
                for (int i = 0; i < item.count; i++) {
                    Point nextPoint = nextPoint(map, facing, position);
                    if (map.grid.get(nextPoint) == FieldItem.WALL) {
                        break;
                    } else {
                        position = nextPoint;
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

    private static Point nextPoint(FieldMap map, Facing facing, Point position) {
        Map<Point, FieldItem> grid = map.grid;
        int row = position.row();
        int col = position.col();

        Point nextPoint = null;

        switch (facing) {
            case RIGHT -> {
                do {
                    if (col++ > map.cols) {
                        col = 1;
                    }
                    nextPoint = new Point(row, col);
                } while (!grid.containsKey(nextPoint));
            }
            case DOWN -> {
                do {
                    if (row++ > map.rows) {
                        row = 1;
                    }
                    nextPoint = new Point(row, col);
                } while (!grid.containsKey(nextPoint));
            }
            case LEFT -> {
                do {
                    if (col-- < 1) {
                        col = map.cols;
                    }
                    nextPoint = new Point(row, col);
                } while (!grid.containsKey(nextPoint));
            }
            case UP -> {
                do {
                    if (row-- < 1) {
                        row = map.rows;
                    }
                    nextPoint = new Point(row, col);
                } while (!grid.containsKey(nextPoint));
            }
        }

        return nextPoint;
    }

}
