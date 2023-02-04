package aoc2022.day18;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day18 {

    public static void main(String[] args) throws IOException {
//        printSurfaceArea(Utils.readTestInput("day18"));
        printSurfaceArea(Utils.readInput("day18"));

    }

    private static void printSurfaceArea(List<String> input) {
        Lava lava = new Lava();
        int totalSurfaceArea = lava.calculateSurfaceArea(input);
        System.out.println("Surface area is: " + totalSurfaceArea);
        int outerArea = lava.calculateOuterSurface();
        System.out.println("Outer area is: " + outerArea);
    }
}
