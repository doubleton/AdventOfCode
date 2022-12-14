package aoc2022.day14;

import aoc2022.common.Utils;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class Day14 {

    public static void main(String[] args) throws IOException, ParseException {
        List<String> input = Utils.readLines(args[1]);

        System.out.println("Total sand without floor: " + countSand(input, false));
        System.out.println("Total sand with floor: " + countSand(input, true));
    }

    private static int countSand(List<String> input, boolean addFloor) {
        Cave cave = new Cave();
        cave.init(input, addFloor);
        cave.print();

        System.out.println("\n---------------\n");

        cave.simulateSand();
        cave.print();

        return cave.countSand();
    }
}
