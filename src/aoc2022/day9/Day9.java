package aoc2022.day9;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws IOException {
        List<String> commands = Utils.readLines(args[2]);

        System.out.println(countTailPositions(commands, new ShortRope()));
        System.out.println(countTailPositions(commands, new LongRope(10)));
    }

    private static int countTailPositions(List<String> commands, Rope rope) {
        for (String command : commands) {
            String[] parts = command.split(" ");
            String action = parts[0];
            int count = Integer.parseInt(parts[1]);
            for (int i = 0; i < count; i++) {
                rope.perform(action);
            }
        }

        return rope.getTailPositions().size();
    }

}
