package aoc2022.day16;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day16 {

    public static void main(String[] args) throws IOException {
//        printMaxPressure(Utils.readTestInput("day16"));
//        printMaxPressureWithElephant(Utils.readTestInput("day16"));

//        printMaxPressure(Utils.readInput("day16"));
        printMaxPressureWithElephant(Utils.readInput("day16"));
    }

    private static void printMaxPressure(List<String> input) {
        Volcano volcano = new Volcano();
        volcano.init(input);
        System.out.println(volcano.findMaxPressure());
    }

    private static void printMaxPressureWithElephant(List<String> input) {
        Volcano volcano = new Volcano();
        volcano.init(input);
        System.out.println(volcano.findMaxPressureWithElephant());
    }

}
