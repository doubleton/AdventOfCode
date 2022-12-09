package aoc2022.day9;

import java.util.HashSet;
import java.util.Set;

public class ShortRope implements Rope {

    private final Position head;
    private final Position tail;

    private final Set<String> tailPositions;

    public ShortRope() {
        this(new Position(0, 0), new Position(0, 0));
    }

    public ShortRope(Position head) {
        this(head, new Position(0, 0));
    }

    public ShortRope(Position head, Position tail) {
        this.head = head;
        this.tail = tail;
        this.tailPositions = new HashSet<>();

        tailPositions.add(tail.toString());
    }

    public Position getHead() {
        return head;
    }

    public Position getTail() {
        return tail;
    }

    public Set<String> getTailPositions() {
        return tailPositions;
    }

    public void perform(String action) {
        switch (action) {
            case "U" -> up();
            case "D" -> down();
            case "R" -> right();
            case "L" -> left();
            default -> throw new RuntimeException("Unknown action " + action);
        };
    }

    private void up() {
        head.i++;
        moveTail();
    }

    private void down() {
        head.i--;
        moveTail();
    }

    private void right() {
        head.j++;
        moveTail();
    }

    private void left() {
        head.j--;
        moveTail();
    }

    public void moveTail() {
        if (Math.abs(head.i - tail.i) <= 1 && Math.abs(head.j - tail.j) <= 1) {
            return; // tail already touches the head
        }

        tail.i += Integer.compare(head.i, tail.i); // move tail by -1/0/1 position vertically towards the head
        tail.j += Integer.compare(head.j, tail.j); // move tail by -1/0/1 position horizontally towards the head

        tailPositions.add(tail.toString());
    }

    public static class Position {
        private int i;
        private int j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return i + "," + j;
        }
    }
}
