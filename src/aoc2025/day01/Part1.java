package aoc2025.day01;

import aoc2025.common.InputUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Part1 {
    private static final int DIAL_SIZE = 100;
    private static final int START_POSITION = 50;

    public static void main(String[] args) throws IOException {
        List<String> rotations = args.length > 0
                ? InputUtils.readLines(Path.of(args[0]))
                : InputUtils.readInputLines("day01", "input1.txt");

        int result = calculatePassword(rotations);
        System.out.println(result);
    }

    public static int calculatePassword(List<String> rotations) {
        int position = START_POSITION;
        int hitsOnZero = 0;

        for (String rawRotation : rotations) {
            String rotation = rawRotation.trim();
            if (rotation.isEmpty()) {
                continue;
            }

            char direction = rotation.charAt(0);
            int distance = Integer.parseInt(rotation.substring(1));

            position = rotate(position, direction, distance);
            if (position == 0) {
                hitsOnZero++;
            }
        }

        return hitsOnZero;
    }

    private static int rotate(int position, char direction, int distance) {
        int delta;
        if (direction == 'L' || direction == 'l') {
            delta = -distance;
        } else if (direction == 'R' || direction == 'r') {
            delta = distance;
        } else {
            throw new IllegalArgumentException("Unknown direction: " + direction);
        }

        int next = (position + delta) % DIAL_SIZE;
        return next < 0 ? next + DIAL_SIZE : next;
    }
}
