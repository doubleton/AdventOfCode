package aoc2022.day5;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day5 {

    public static void main(String[] args) throws IOException {
        List<String> crateInput = Utils.readLines(args[0]);
        List<String> movesInput = Utils.readLines(args[1]);

        Crane crane = new Crane(crateInput);
        System.out.println("Initial state:");
        crane.print();

        for (String moveInput : movesInput) {
//            crane.doCrateMover9000(new Move(moveInput));
            crane.doCrateMover9001(new Move(moveInput));
        }

        System.out.println("------------");
        crane.print();
        System.out.println("------------");
        crane.printTop();
    }
}
