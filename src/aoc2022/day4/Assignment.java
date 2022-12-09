package aoc2022.day4;

public class Assignment {
    final int from;
    final int to;

    public Assignment(String input) {
        String[] parts = input.split("-");
        this.from = Integer.parseInt(parts[0]);
        this.to = Integer.parseInt(parts[1]);
    }

    public boolean includes(Assignment other) {
        return from <= other.from && to >= other.to;
    }

    public boolean overlap(Assignment other) {
        return from <= other.to && to >= other.from;
    }

    public static boolean fillyContain(Assignment a1, Assignment a2) {
        return a1.includes(a2) || a2.includes(a1);
    }
}
