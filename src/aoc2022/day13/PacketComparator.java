package aoc2022.day13;

import org.json.simple.JSONArray;

import java.util.Comparator;

public class PacketComparator implements Comparator<JSONArray> {

    @Override
    public int compare(JSONArray left, JSONArray right) {
        if (left.isEmpty() || right.isEmpty()) {
            return compareEmpty(left, right);
        }

        Object leftEl = left.get(0);
        JSONArray leftTail = getTail(left);
        Object rightEl = right.get(0);
        JSONArray rightTail = getTail(right);

        if (leftEl instanceof Number && rightEl instanceof Number) {
            int res = compareNumbers((Number) leftEl, (Number) rightEl);
            return res == 0 ? compare(leftTail, rightTail) : res;
        }

        if (leftEl instanceof Number) {
            leftEl = wrapArray(leftEl);
        }
        if (rightEl instanceof Number) {
            rightEl = wrapArray(rightEl);
        }

        int res = compare((JSONArray) leftEl, (JSONArray) rightEl);
        return res == 0 ? compare(leftTail, rightTail) : res;
    }

    private int compareEmpty(JSONArray left, JSONArray right) {
        if (left.isEmpty() && right.isEmpty()) {
            return 0;
        } else if (left.isEmpty()) {
            return -1;
        } else {
            return 1;
        }
    }

    private int compareNumbers(Number left, Number right) {
        return left.intValue() - right.intValue();
    }

    private static JSONArray wrapArray(Object value) {
        JSONArray arr = new JSONArray();
        arr.add(value);
        return arr;
    }

    private static JSONArray getTail(JSONArray array) {
        JSONArray tail = new JSONArray();
        tail.addAll(array.subList(1, array.size()));
        return tail;
    }
}
