package aoc2022.day21;

public enum Operation {
    PLUS, MINUS, MULT, DIV;

    public static Operation parse(String s) {
        return switch (s) {
            case "+" -> PLUS;
            case "-" -> MINUS;
            case "*" -> MULT;
            case "/" -> DIV;
            default -> null;
        };
    }

    public long apply(long left, long right) {
        return switch (this) {
            case PLUS -> left + right;
            case MINUS -> left - right;
            case MULT -> left * right;
            case DIV -> left / right;
        };
    }

    // reverse operation if right argument and target value are given, e.g.:
    // for A + B = C: reverse for known B is: A = C - B
    // for A - B = C: reverse for known B is: A = C + B
    // for A * B = C: reverse for known B is: A = C / B
    // for A / B = C: reverse for known B is: A = C * B
    public long reverseLeft(long right, long targetValue) {
        return switch (this) {
            case PLUS -> targetValue - right;
            case MINUS -> targetValue + right;
            case MULT -> targetValue / right;
            case DIV -> targetValue * right;
        };
    }

    // reverse operation if left argument and target value are given, e.g.:
    // for A + B = C: reverse for known A is: B = C - A
    // for A - B = C: reverse for known A is: B = A - C
    // for A * B = C: reverse for known A is: B = C / A
    // for A / B = C: reverse for known A is: B = A / C
    public long reverseRight(long left, long targetValue) {
        return switch (this) {
            case PLUS -> targetValue - left;
            case MINUS -> left - targetValue;
            case MULT -> targetValue / left;
            case DIV -> left / targetValue;
        };
    }
}
