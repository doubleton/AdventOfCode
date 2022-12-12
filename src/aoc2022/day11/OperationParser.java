package aoc2022.day11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationParser {

    private static final String OLD_ARG = "old";
    private static final Pattern OPERATION_PATTERN = Pattern.compile("\s*Operation: (new = old ([*+]) (\\d+|old))");

    public static Operation parse(String input) {
        Matcher matcher = OPERATION_PATTERN.matcher(input);
        if (matcher.find()) {
            String sign = matcher.group(2);
            String argument = matcher.group(3);

            switch (sign) {
                case "*" -> {
                    return OLD_ARG.equals(argument) ? new Operation.Square() :
                            new Operation.MultiplyN(Integer.parseInt(argument));
                }
                case "+" -> {
                    return new Operation.AddN(Integer.parseInt(argument));
                }
            }
        }
        throw new RuntimeException("Operation can't be parsed for the input: " + input);
    }

}
