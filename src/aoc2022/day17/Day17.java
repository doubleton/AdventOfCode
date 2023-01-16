package aoc2022.day17;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day17 {

    public static void main(String[] args) throws IOException {
//        printWellHeights(Utils.readTestInput("day17"), 1000000000000L);
//        printWellHeights(Utils.readInput("day17"), 2022);
        printWellHeights(Utils.readInput("day17"), 1000000000000L);
    }

    private static void printWellHeights(List<String> input, long rocks) {
        Chamber chamber = new Chamber();
        chamber.init(input.get(0));
        chamber.simulate(rocks);
    }
}
