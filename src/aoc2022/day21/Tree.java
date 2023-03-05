package aoc2022.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree {

    private final static Pattern EXPRESSION_PATTERN = Pattern.compile("(\\w+) ([+\\-*/]) (\\w+)");

    private final static String HUMN = "humn";

    private final Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public static Tree init(List<String> input) {
        Map<String, Node> nodes = new HashMap<>();
        for (String string : input) {
            String[] parts = string.split(": ");

            String nodeName = parts[0];
            Node node = nodes.computeIfAbsent(nodeName, Node::new);

            String rightPart = parts[1];
            try {
                node.value = Long.parseLong(rightPart);
            } catch (NumberFormatException e) {
                Matcher matcher = EXPRESSION_PATTERN.matcher(rightPart);
                if (matcher.find()) {
                    node.left = nodes.computeIfAbsent(matcher.group(1), Node::new);
                    node.operation = Operation.parse(matcher.group(2));
                    node.right = nodes.computeIfAbsent(matcher.group(3), Node::new);
                } else {
                    System.out.println("Ouch...");
                }
            }
        }
        return new Tree(nodes.get("root"));
    }

    public long compute() {
        return Objects.requireNonNullElseGet(root.value, () -> compute(root));
    }

    private long compute(Node node) {
        return Objects.requireNonNullElseGet(node.value,
                () -> node.operation.apply(compute(node.left), compute(node.right)));
    }

    public long calculateHumn() {
        if (hasHumn(root.left)) {
            return calculateHumn(root.left, compute(root.right));
        } else {
            return calculateHumn(root.right, compute(root.left));
        }
    }

    private long calculateHumn(Node node, long targetValue) {
        if (node.name.equals(HUMN)) {
            return targetValue;
        }

        if (hasHumn(node.left)) {
            long right = compute(node.right);
            return calculateHumn(node.left, node.operation.reverseLeft(right, targetValue));
        } else {
            long left = compute(node.left);
            return calculateHumn(node.right, node.operation.reverseRight(left, targetValue));
        }
    }

    private boolean hasHumn(Node node) {
        return node != null && (node.name.equals(HUMN) || hasHumn(node.left) || hasHumn(node.right));
    }

}
