package aoc2022.day22;

import java.util.ArrayList;
import java.util.List;

public class Path {

    final List<PathItem> items;

    public Path(List<PathItem> items) {
        this.items = items;
    }

    // 10R5L5R10L4R5L5
    public static Path init(String input) {
        List<PathItem> items = new ArrayList<>();
        int move = 0;
        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                move = move * 10 + ch - '0';
                continue;
            }

            items.add(new PathItem(PathItem.PathItemKind.MOVE, move));
            move = 0;

            if (ch == 'R') {
                items.add(new PathItem(PathItem.PathItemKind.RIGHT));
            } else if (ch == 'L') {
                items.add(new PathItem(PathItem.PathItemKind.LEFT));
            }
        }
        if (move != 0) {
            items.add(new PathItem(PathItem.PathItemKind.MOVE, move));
        }

        return new Path(items);
    }

    public void print() {
        System.out.println(items);
    }
}
