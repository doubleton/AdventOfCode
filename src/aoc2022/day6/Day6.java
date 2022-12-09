package aoc2022.day6;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day6 {

    private static final int PACKET_MARKER_LENGTH = 4;
    private static final int MESSAGE_MARKER_LENGTH = 14;

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(args);
//        System.out.println(findStartPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", PACKET_MARKER_LENGTH));
//        System.out.println(findStartPosition("nppdvjthqldpwncqszvftbrmjlhg", PACKET_MARKER_LENGTH));
//        System.out.println(findStartPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", PACKET_MARKER_LENGTH));
//        System.out.println(findStartPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", PACKET_MARKER_LENGTH));
        System.out.println(findStartPosition(lines.get(0), PACKET_MARKER_LENGTH));

//        System.out.println(findStartPosition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", MESSAGE_MARKER_LENGTH));
//        System.out.println(findStartPosition("bvwbjplbgvbhsrlpgdmjqwftvncz", MESSAGE_MARKER_LENGTH));
//        System.out.println(findStartPosition("nppdvjthqldpwncqszvftbrmjlhg", MESSAGE_MARKER_LENGTH));
//        System.out.println(findStartPosition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", MESSAGE_MARKER_LENGTH));
//        System.out.println(findStartPosition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", MESSAGE_MARKER_LENGTH));
        System.out.println(findStartPosition(lines.get(0), MESSAGE_MARKER_LENGTH));
    }

    public static int findStartPosition(String input, int markerLength) {
        LinkedList<Character> marker = new LinkedList<>();
        for (int i = 0; i < markerLength; i++) {
            marker.add(input.charAt(i));
        }

        int i = markerLength;
        while (i < input.length()) {
            if (uniq(marker, markerLength)) {
                return i;
            }
            marker.removeFirst();
            marker.addLast(input.charAt(i));
            i++;
        }

        return input.length();
    }

    private static boolean uniq(List<Character> marker, int markerLength) {
        return Set.copyOf(marker).size() == markerLength;
    }
}
