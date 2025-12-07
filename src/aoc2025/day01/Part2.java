package aoc2025.day01;

import aoc2025.common.InputUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Part2 {
    private static final int DIAL_SIZE = 100;
    private static final int START_POSITION = 50;

    public static void main(String[] args) throws IOException {
        List<String> rotations = args.length > 0
                ? InputUtils.readLines(Path.of(args[0]))
                : InputUtils.readInputLines("day01", "input2.txt");

        long result = calculatePassword(rotations);
        System.out.println(result);
    }

    public static long calculatePassword(List<String> rotations) {
        int position = START_POSITION;
        long hitsOnZero = 0;

        for (String rawRotation : rotations) {
            String rotation = rawRotation.trim();
            if (rotation.isEmpty()) {
                continue;
            }

            char direction = rotation.charAt(0);
            long distance = Long.parseLong(rotation.substring(1));
            int sign = directionSign(direction);

            long stepsToFirstZero = stepsUntilZero(position, sign);
            if (stepsToFirstZero <= distance) {
                // First hit, then every full dial revolution.
                hitsOnZero += 1 + (distance - stepsToFirstZero) / DIAL_SIZE;
            }

            position = rotate(position, sign, distance);
        }

        return hitsOnZero;
    }

    private static int directionSign(char direction) {
        if (direction == 'L' || direction == 'l') {
            return -1;
        } else if (direction == 'R' || direction == 'r') {
            return 1;
        }
        throw new IllegalArgumentException("Unknown direction: " + direction);
    }

    // Steps from current position to the next time the dial points at 0 (exclusive of current position).
    private static long stepsUntilZero(int position, int sign) {
        if (sign > 0) {
            long steps = (DIAL_SIZE - position) % DIAL_SIZE;
            return steps == 0 ? DIAL_SIZE : steps;
        } else {
            long steps = position % DIAL_SIZE;
            return steps == 0 ? DIAL_SIZE : steps;
        }
    }

    private static int rotate(int position, int sign, long distance) {
        long next = (position + sign * distance) % DIAL_SIZE;
        return (int) (next < 0 ? next + DIAL_SIZE : next);
    }
}
