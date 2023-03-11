package aoc2022.day22;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FieldMap {

    final Map<Point, FieldItem> grid;
    final int rows;
    final int cols;
    final Point start;

    public FieldMap(Map<Point, FieldItem> grid, int rows, int cols, Point start) {
        this.grid = grid;
        this.rows = rows;
        this.cols = cols;
        this.start = start;
    }

    public static FieldMap init(List<String> input) {
        Map<Point, FieldItem> grid = new LinkedHashMap<>();
        int maxCol = 0;

        int row = 0;
        for (String string : input) {
            row++;

            int col = 0;
            for (char ch : string.toCharArray()) {
                col++;
                if (ch == '.') {
                    grid.put(new Point(row, col), FieldItem.OPEN);
                } else if (ch == '#') {
                    grid.put(new Point(row, col), FieldItem.WALL);
                }
                if (col > maxCol) {
                    maxCol = col;
                }
            }
        }

        return new FieldMap(grid, row, maxCol, grid.keySet().iterator().next());
    }

    public void print() {
        System.out.println("grid: " + grid);
        System.out.println("rows: " + rows);
        System.out.println("cols: " + cols);
        System.out.println("start: " + start);
    }
}
