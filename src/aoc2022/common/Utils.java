package aoc2022.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public static List<String> readLines(String... args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("Please provide the input file name as argument");
        }

        return Files.readAllLines(Path.of(args[0]));
    }

    public static List<String> readInput(String day) throws IOException {
        return readFile(day, "input.txt");
    }

    public static List<String> readTestInput(String day) throws IOException {
        return readFile(day, "test_input.txt");
    }

    public static List<String> readFile(String day, String filename) throws IOException {
        Path currentRelativePath = Paths.get("");
        Path path = Paths.get(currentRelativePath.toAbsolutePath().toString(), "src/aoc2022", day, filename);
        return Files.readAllLines(path);
    }

}
