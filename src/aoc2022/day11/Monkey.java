package aoc2022.day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Monkey {

    private final int index;
    private final LinkedList<Long> items;
    private final Operation inspectOperation;
    private final long testDivisor;
    private final int trueTestMonkeyId;
    private final int falseTestMonkeyId;

    private Operation reliefOperation;
    private long business = 0;

    public Monkey(int index,
                  Long[] initialItems,
                  Operation inspectOperation,
                  long testDivisor,
                  int trueTestMonkeyId,
                  int falseTestMonkeyId) {
        this.index = index;
        this.inspectOperation = inspectOperation;
        this.items = new LinkedList<>(Arrays.asList(initialItems));
        this.testDivisor = testDivisor;
        this.trueTestMonkeyId = trueTestMonkeyId;
        this.falseTestMonkeyId = falseTestMonkeyId;
    }

    public int getIndex() {
        return index;
    }

    public long getTestDivisor() {
        return testDivisor;
    }

    public void setReliefOperation(Operation reliefOperation) {
        this.reliefOperation = reliefOperation;
    }

    public long getBusiness() {
        return business;
    }

    public void round(Map<Integer, Monkey> monkeys) {
        items.stream()
                .map(inspectOperation::inspect)
                .map(this::relief)
                .forEachOrdered(item -> {
                    if (item % testDivisor == 0) {
                        monkeys.get(trueTestMonkeyId).addItem(item);
                    } else {
                        monkeys.get(falseTestMonkeyId).addItem(item);
                    }
                    business++;
                });
        items.clear();
    }

    private long relief(long item) {
        if (reliefOperation != null) {
            return reliefOperation.inspect(item);
        }
        return item;
    }

    public void addItem(long item) {
        items.addLast(item);
    }
}
