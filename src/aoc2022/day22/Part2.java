package aoc2022.day22;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
//        List<String> input = Utils.readTestInput("day22");
        List<String> input = Utils.readInput("day22");

        FieldMap fieldMap = FieldMap.init(input.subList(0, input.size() - 2));
        Path path = Path.init(input.get(input.size() - 1));

        System.out.println(Solver2.solve(fieldMap, path));
    }

}
