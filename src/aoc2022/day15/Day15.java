package aoc2022.day15;

import aoc2022.common.Utils;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class Day15 {

    public static void main(String[] args) throws IOException, ParseException {
        List<String> input = Utils.readLines(args[1]);
//        System.out.println(countNbAtRow(input, 10));
//        System.out.println(countNbAtRow(input, 2_000_000));

//        System.out.println(findTuningFrequency(input, 20));
        System.out.println(findTuningFrequency(input, 4_000_000));
    }

    private static long countNbAtRow(List<String> input, int row) {
        Tunnels tunnels = new Tunnels();
        tunnels.init(input);

        return tunnels.countNbsAtRow(row);
    }

    private static long findTuningFrequency(List<String> input, int max) {
        Tunnels tunnels = new Tunnels();
        tunnels.init(input);

        return tunnels.findTuningFrequency(max);
    }

}
