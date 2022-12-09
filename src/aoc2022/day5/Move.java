package aoc2022.day5;

public class Move {
    int count;
    int from;
    int to;

    public Move(String input) {
        String[] parts = input.split(" ");
        count = Integer.parseInt(parts[1]);
        from = Integer.parseInt(parts[3]);
        to = Integer.parseInt(parts[5]);
    }

    public String toString() {
        return "move " + count +
                " from " + from +
                " to " + to;
    }
}
