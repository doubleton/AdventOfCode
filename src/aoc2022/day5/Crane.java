package aoc2022.day5;

import java.util.List;

public class Crane {

    private final Stack[] stacks;

    public Crane(List<String> input) {
        String lastLine = input.remove(input.size() - 1);
        String[] crateIds = lastLine.split("\s+");
        int cratesCount = Integer.parseInt(crateIds[crateIds.length - 1]);

        this.stacks = new Stack[cratesCount];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new Stack();
        }

        for (String line : input) {
            for (int i = 0; i < cratesCount; i++) {
                if (line.length() >= i*4 + 2) {
                    String crateId = line.substring(i*4 + 1, i*4 + 2).trim();
                    if (!crateId.isEmpty()) {
                        stacks[i].addLast(crateId);
                    }
                }
            }
        }
    }

    public void doCrateMover9000(Move move) {
        Stack from = stacks[move.from - 1];
        Stack to = stacks[move.to - 1];
        for (int i = 0; i < move.count; i++) {
            String crate = from.pickTop();
            to.putTop(crate);
        }
    }

    public void doCrateMover9001(Move move) {
        Stack from = stacks[move.from - 1];
        Stack to = stacks[move.to - 1];
        Stack temp = new Stack();
        for (int i = 0; i < move.count; i++) {
            String crate = from.pickTop();
            temp.addLast(crate);
        }
        to.putTopStack(temp);
    }

    public void print() {
        for (int i = 0; i < stacks.length; i++) {
            Stack stack = stacks[i];
            System.out.print(i + 1);
            System.out.print(": ");
            System.out.println(stack.toString());
        }
    }

    public void printTop() {
        for (Stack stack : stacks) {
            System.out.print(stack.topCrate());
        }
    }
}
