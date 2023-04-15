package aoc2022.day23;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
//        List<String> input = Utils.readTestInput("day23");
        List<String> input = Utils.readInput("day23");
        Field field = Field.init(input);
        field.print();
        int round = 1;
        while (field.move()) {
            round++;
        }
//        System.out.println("==========");
//        field.print();
        System.out.println("Rounds till no elf moved: " + round);
    }

}
