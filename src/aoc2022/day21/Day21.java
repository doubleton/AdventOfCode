package aoc2022.day21;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day21 {

    public static void main(String[] args) throws IOException {
//        computeRoot(Utils.readTestInput("day21"));
//        computeRoot(Utils.readInput("day21"));
//        calculateHumn(Utils.readTestInput("day21"));
        calculateHumn(Utils.readInput("day21"));
    }

    private static void computeRoot(List<String> input) {
        Tree tree = Tree.init(input);
        System.out.println("Root: " + tree.compute());
    }

    private static void calculateHumn(List<String> input) {
        Tree tree = Tree.init(input);
        System.out.println("Humn: " + tree.calculateHumn());
    }

}
