package aoc2022.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Utils {

    public static List<String> readLines(String... args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("Please provide the input file name as argument");
        }

        return Files.readAllLines(Path.of(args[0]));
    }

}
