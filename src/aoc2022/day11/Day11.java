package aoc2022.day11;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day11 {


    public static void main(String[] args) throws IOException {
        List<String> commands = Utils.readLines(args[1]);

        playWithSimpleRelief(commands, 20);
        System.out.println("---------------");
        playWithSpecialRelief(commands, 10_000);
    }

    private static void playWithSimpleRelief(List<String> commands, int rounds) {
        Monkeys monkeys = new Monkeys();
        monkeys.init(commands);
        monkeys.setReliefOperation(new Operation.DivideN(3));
        monkeys.playRounds(rounds);
        monkeys.printMonkeysBusinessLevel();
    }

    private static void playWithSpecialRelief(List<String> commands, int rounds) {
        Monkeys monkeys = new Monkeys();
        monkeys.init(commands);
        long commonDivisor = monkeys.getMonkeys().values().stream().mapToLong(Monkey::getTestDivisor).reduce(1, (a, b) -> a * b);
        monkeys.setReliefOperation(new Operation.Mod(commonDivisor));
        monkeys.playRounds(rounds);
        monkeys.printMonkeysBusinessLevel();
    }

}
