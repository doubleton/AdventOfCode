package aoc2022.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotFactory {

    private static final int INITIAL_ORE_ROBOTS = 1;

    private final Blueprint blueprint;

    private final Map<Key, Integer> cache = new HashMap<>();

    private int maxGeodes = 0;

    public RobotFactory(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public int calcQualityLevel(int time) {
        return blueprint.number * calcMaxGeodes(time);
    }

    public int calcMaxGeodes(int time) {
        System.out.println("Blueprint " + blueprint.number + " started");
        Robots robots = new Robots(INITIAL_ORE_ROBOTS, 0, 0, 0);
        Resources resources = new Resources( 0, 0, 0, 0);
        int maxGeodes = calcMaxGeodes(time, robots, resources);
        System.out.println("Blueprint " + blueprint.number + ": max geodes = " + maxGeodes);
        return maxGeodes;
    }

    private int calcMaxGeodes(int time, Robots robots, Resources resources) {
        if (time == 1) {
            int geodes = resources.geo + robots.geoR;
            if (geodes > maxGeodes) {
                maxGeodes = geodes;
            }
            return geodes;
        }

        // best case: if we create one more Geode robot every next minute, we'll collect:
        // resources.geo => existing geodes
        // + robots.geoR * time  => existing robots can collect
        // + sum(time - 1) => new robots will collect
        if (resources.geo + robots.geoR * time + sum(time - 1) < maxGeodes) {
            return maxGeodes;
        }

        Key key = new Key(time, robots, resources);
        Integer cachedValue = cache.get(key);
        if (cachedValue != null) {
            return cachedValue;
        }

        if (canBuildGeo(resources)) {
            return calcMaxGeodes(time - 1,
                    robots.plus(0, 0, 0, 1),
                    resources.plus(robots.oreR - blueprint.geodeRobotOreCost, robots.clayR,
                            robots.obsR - blueprint.geodeRobotObsidianCost, robots.geoR)
            );
        }

        List<Integer> options = new ArrayList<>();
        if (canBuildObs(resources)) {
            options.add(
                    calcMaxGeodes(time - 1,
                            robots.plus(0, 0, 1, 0),
                            resources.plus(robots.oreR - blueprint.obsidianRobotOreCost, robots.clayR - blueprint.obsidianRobotClayCost, robots.obsR, robots.geoR)
                    )
            );
        }
        if (canBuildClay(resources)) {
            options.add(
                    calcMaxGeodes(time - 1,
                            robots.plus(0, 1, 0, 0),
                            resources.plus(robots.oreR - blueprint.clayRobotOreCost, robots.clayR, robots.obsR, robots.geoR)
                    )
            );
        }
        if (canBuildOre(resources)) {
            options.add(
                    calcMaxGeodes(time - 1,
                            robots.plus(1, 0, 0, 0),
                            resources.plus(robots.oreR - blueprint.oreRobotOreCost, robots.clayR, robots.obsR, robots.geoR)
                    )
            );
        }

        // build no robots this step
        options.add(calcMaxGeodes(time - 1, robots, resources.plus(robots.oreR, robots.clayR, robots.obsR, robots.geoR)));

        Integer geodes = options.stream().max(Integer::compareTo).get();
        cache.put(key, geodes);

        if (geodes > maxGeodes) {
            maxGeodes = geodes;
        }

        return geodes;
    }

    private boolean canBuildGeo(Resources resources) {
        return blueprint.geodeRobotOreCost <= resources.ore && blueprint.geodeRobotObsidianCost <= resources.obs;
    }

    private boolean canBuildObs(Resources resources) {
        return blueprint.obsidianRobotOreCost <= resources.ore && blueprint.obsidianRobotClayCost <= resources.clay;
    }

    private boolean canBuildClay(Resources resources) {
        return blueprint.clayRobotOreCost <= resources.ore;
    }

    private boolean canBuildOre(Resources resources) {
        return blueprint.oreRobotOreCost <= resources.ore;
    }

    private int sum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + i;
        }
        return sum;
    }

    private record Resources(int ore, int clay, int obs, int geo) {
        public Resources plus(int dOre, int dClay, int dObs, int dGeo) {
            return new Resources(ore + dOre, clay + dClay, obs + dObs, geo + dGeo);
        }
    }

    private record Robots(int oreR, int clayR, int obsR, int geoR) {
        public Robots plus(int dOre, int dClay, int dObs, int dGeo) {
            return new Robots(oreR + dOre, clayR + dClay, obsR + dObs, geoR + dGeo);
        }
    }

    private record Key(int time, Robots robots, Resources resources) {}
}
