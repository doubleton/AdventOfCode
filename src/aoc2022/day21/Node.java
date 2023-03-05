package aoc2022.day21;

public class Node {
    final String name;
    Long value;
    Operation operation;
    Node left;
    Node right;

    public Node(String name) {
        this.name = name;
    }
}
