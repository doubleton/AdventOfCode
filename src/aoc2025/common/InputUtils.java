package aoc2025.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class InputUtils {
    private static final Path BASE_DIR = Paths.get("").toAbsolutePath().resolve("src").resolve("aoc2025");

    private InputUtils() {
        // Utility class
    }

    public static List<String> readInputLines(String day) throws IOException {
        return readLines(day, "input1.txt");
    }

    public static List<String> readInputLines(String day, String fileName) throws IOException {
        return readLines(day, fileName);
    }

    public static List<String> readExampleLines(String day) throws IOException {
        return readLines(day, "example1.txt");
    }

    public static List<String> readLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public static List<String> readLines(String day, String fileName) throws IOException {
        Path filePath = BASE_DIR.resolve(day).resolve(fileName);
        return Files.readAllLines(filePath);
    }
}
