package aoc2022.day11;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Monkeys {

    private final Map<Integer, Monkey> monkeys;

    public Monkeys() {
        this.monkeys = new LinkedHashMap<>();
    }

    public void init(List<String> input) {
        int index = 0;
        while (index < input.size()) {
            List<String> monkeyDefinition = input.subList(index, index + 6);
            Monkey monkey = MonkeyParser.parse(monkeyDefinition);
            monkeys.put(monkey.getIndex(), monkey);
            index += 7;
        }
    }

    public Map<Integer, Monkey> getMonkeys() {
        return monkeys;
    }

    public void setReliefOperation(Operation reliefOperation) {
        monkeys.values().forEach(monkey -> monkey.setReliefOperation(reliefOperation));
    }

    public void playRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys.values()) {
                monkey.round(monkeys);
            }
        }
        System.out.println("== After round " + rounds + " ==");
    }

    public void printMonkeysBusinessLevel() {
        for (Monkey monkey : monkeys.values()) {
            System.out.println("Monkey " + monkey.getIndex() + " inspected items " + monkey.getBusiness() + " times.");
        }

        long businessLevel = monkeys.values()
                .stream()
                .mapToLong(Monkey::getBusiness)
                .sorted()
                .skip(monkeys.size() - 2)
                .reduce(1, (a, b) -> a * b);
        System.out.println("Total business level: " + businessLevel);
    }
}
