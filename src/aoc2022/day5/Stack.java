package aoc2022.day5;

import java.util.LinkedList;

public class Stack {

    final LinkedList<String> crates = new LinkedList<>();

    public String topCrate() {
        return crates.getFirst();
    }

    public String toString() {
        return crates.toString();
    }

    public void addLast(String crateId) {
        crates.addLast(crateId);
    }

    public String pickTop() {
        return crates.removeFirst();
    }

    public void putTop(String crate) {
        crates.addFirst(crate);
    }

    public void putTopStack(Stack other) {
        while (!other.crates.isEmpty()) {
            String crate = other.crates.removeLast();
            crates.addFirst(crate);
        }
    }
}
