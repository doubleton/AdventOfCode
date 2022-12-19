package aoc2022.day17;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day17 {

    public static void main(String[] args) throws IOException {
//        rocks: 15, height: 25, step: 82
//        rocks: 50, height: 78, step: 282, difH: 53, difR: 35, difS: 200
//        rocks: 85, height: 131, step: 482, difH: 53, difR: 35, difS: 200
//        rocks: 120, height: 184, step: 682, difH: 53, difR: 35, difS: 200
//        ...
//        rocks: 120, height: 184, step: 682, difH: 53, difR: 35, difS: 200

        printWellHeights(Utils.readTestInput("day17"), 120);
//        printWellHeights(Utils.readInput("day17"), 2022);
//        printWellHeights(Utils.readInput("day17"), 1000000000000L);
    }

    private static void printWellHeights(List<String> input, long rocks) {
        Chamber chamber = new Chamber();
        chamber.init(input.get(0));
        chamber.simulate(rocks);
        chamber.print();
        System.out.println(chamber.getHeight());
    }
}
