package aoc2022.day24;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
//        List<String> input = Utils.readTestInput("day24");
        List<String> input = Utils.readInput("day24");
        ValleyMap valleyMap = ValleyMap.init(input);
        int moves = valleyMap.solve();

        System.out.println("Min moves to exit is: " + moves);
    }

}
