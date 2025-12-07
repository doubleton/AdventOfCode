package aoc2022.day25;

import aoc2022.common.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {

    static void main(String[] args) throws IOException {
        List<String> input = loadInput(args);
        long sum = 0;
        for (String line : input) {
            sum += snafuToDecimal(line.trim());
        }

        String result = decimalToSnafu(sum);
        System.out.println("SNAFU sum: " + result);
    }

    private static List<String> loadInput(String[] args) throws IOException {
        if (args.length > 0) {
            if ("test".equalsIgnoreCase(args[0])) {
                return Utils.readFile("day25", "test_input1.txt");
            }
            return Files.readAllLines(Path.of(args[0]));
        }
        return Utils.readFile("day25", "input1.txt");
    }

    static long snafuToDecimal(String snafu) {
        long value = 0;
        for (char c : snafu.toCharArray()) {
            value = value * 5 + snafuDigitToValue(c);
        }
        return value;
    }

    private static int snafuDigitToValue(char c) {
        return switch (c) {
            case '2' -> 2;
            case '1' -> 1;
            case '0' -> 0;
            case '-' -> -1;
            case '=' -> -2;
            default -> throw new IllegalArgumentException("Unknown SNAFU digit: " + c);
        };
    }

    static String decimalToSnafu(long value) {
        if (value == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();
        long current = value;
        while (current != 0) {
            long remainder = current % 5;
            current /= 5;

            if (remainder <= 2) {
                builder.append((char) ('0' + remainder));
            } else if (remainder == 3) {
                builder.append('=');
                current += 1;
            } else { // remainder == 4
                builder.append('-');
                current += 1;
            }
        }

        return builder.reverse().toString();
    }
}
