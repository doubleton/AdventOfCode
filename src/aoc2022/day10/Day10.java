package aoc2022.day10;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<String> commands = Utils.readLines(args[1]);
        System.out.println(signalStrength(commands));
    }

    private static int signalStrength(List<String> commands) {
        Cpu cpu = new Cpu();
        for (String command : commands) {
            cpu.execute(command);
        }
        return cpu.getSignalStrength();
    }
}
