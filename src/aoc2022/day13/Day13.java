package aoc2022.day13;

import aoc2022.common.Utils;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day13 {

    public static void main(String[] args) throws IOException, ParseException {
        List<String> input = Utils.readLines(args[1]);

        System.out.println(sumCorrectIndices(input));
        System.out.println("------------");
        System.out.println(getDecoderKey(input));
    }

    private static int getDecoderKey(List<String> lines) throws ParseException {
        JSONArray dividerPacket1 = PackerParser.parse("[[2]]");
        JSONArray dividerPacket2 = PackerParser.parse("[[6]]");

        List<JSONArray> packets = new ArrayList<>();
        packets.add(dividerPacket1);
        packets.add(dividerPacket2);

        for (String line : lines) {
            if (!line.isEmpty()) {
                packets.add(PackerParser.parse(line));
            }
        }

        packets.sort(new PacketComparator());

        for (JSONArray packet : packets) {
            System.out.println(packet);
        }

        int index1 = packets.indexOf(dividerPacket1) + 1;
        int index2 = packets.indexOf(dividerPacket2) + 1;

        return index1 * index2;
    }

    private static int sumCorrectIndices(List<String> lines) throws ParseException {
        int i = 1;
        int sum = 0;
        PacketComparator comparator = new PacketComparator();
        Iterator<String> it = lines.iterator();
        while (it.hasNext()) {
            JSONArray left = PackerParser.parse(it.next());
            JSONArray right = PackerParser.parse(it.next());

            if (comparator.compare(left, right) < 0) {
                sum += i;
                System.out.println("Index " + i + " is in correct order");
            }

            if (it.hasNext()) {
                it.next();
            }
            i++;
        }
        return sum;
    }
}
