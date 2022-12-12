package aoc2022.day11;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyParser {

    private static final Pattern INDEX_PATTERN = Pattern.compile("Monkey (\\d+):");
    private static final Pattern ITEMS_PATTERN = Pattern.compile("\s*Starting items: ([\\d,\s]+)");
    private static final Pattern TEST_PATTERN = Pattern.compile("\s*Test: divisible by (\\d+)");
    private static final Pattern NEXT_MONKEY_PATTERN = Pattern.compile("\s*If (true|false): throw to monkey (\\d+)");

    public static Monkey parse(List<String> monkeyDefinition) {
        Iterator<String> it = monkeyDefinition.iterator();
        int index = Integer.parseInt(getGroup(it.next(), INDEX_PATTERN));
        Long[] items = Arrays.stream(
                    getGroup(it.next(), ITEMS_PATTERN).split(", ")
                ).mapToLong(Long::parseLong).boxed().toArray(Long[]::new);
        Operation inspectOperation = OperationParser.parse(it.next());
        int testDivisor = Integer.parseInt(getGroup(it.next(), TEST_PATTERN));
        int trueTestMonkeyId = Integer.parseInt(getGroup(it.next(), NEXT_MONKEY_PATTERN, 2));
        int falseTestMonkeyId = Integer.parseInt(getGroup(it.next(), NEXT_MONKEY_PATTERN, 2));

        return new Monkey(index,
                items,
                inspectOperation,
                testDivisor,
                trueTestMonkeyId,
                falseTestMonkeyId
        );
    }

    private static String getGroup(String input, Pattern pattern) {
        return getGroup(input, pattern, 1);
    }

    private static String getGroup(String input, Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(group);
        }
        throw new RuntimeException("Pattern wasn't found in the input: " + input);
    }

}
