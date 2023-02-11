package aoc2022.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blueprint {

    private final static Pattern PATTERN = Pattern.compile("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

    int number;

    int oreRobotOreCost;

    int clayRobotOreCost;

    int obsidianRobotOreCost;

    int obsidianRobotClayCost;

    int geodeRobotOreCost;

    int geodeRobotObsidianCost;

    public static Blueprint parse(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new RuntimeException("Wrong pattern for line: " + input);
        }
        Blueprint blueprint = new Blueprint();
        blueprint.number = Integer.parseInt(matcher.group(1));
        blueprint.oreRobotOreCost = Integer.parseInt(matcher.group(2));
        blueprint.clayRobotOreCost = Integer.parseInt(matcher.group(3));
        blueprint.obsidianRobotOreCost = Integer.parseInt(matcher.group(4));
        blueprint.obsidianRobotClayCost = Integer.parseInt(matcher.group(5));
        blueprint.geodeRobotOreCost = Integer.parseInt(matcher.group(6));
        blueprint.geodeRobotObsidianCost = Integer.parseInt(matcher.group(7));
        return blueprint;
    }

    public void print() {
        System.out.print("Blueprint " + number + ": ");
        System.out.print("Each ore robot costs " + oreRobotOreCost + " ore. ");
        System.out.print("Each clay robot costs " + clayRobotOreCost + " ore. ");
        System.out.print("Each obsidian robot costs " + obsidianRobotOreCost + " ore and " + obsidianRobotClayCost + " clay. ");
        System.out.print("Each geode robot costs " + geodeRobotOreCost + " ore and " + geodeRobotObsidianCost + " obsidian.");
        System.out.println();
    }

}
