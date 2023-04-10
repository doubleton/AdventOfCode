package aoc2022.day23;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
//        List<String> input = Utils.readTestInput("day23");
        List<String> input = Utils.readInput("day23");
        Field field = Field.init(input);
        field.print();
        field.move(10);
        System.out.println("==========");
        field.print();
        System.out.println("Empty ground: " + field.countEmptyGround());
    }

}
