package aoc2022.day7;

import aoc2022.common.Utils;
import aoc2022.day7.filesystem.Filesystem;

import java.io.IOException;
import java.util.List;

public class Day7 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(args);

        Filesystem fs = new Filesystem();
        fs.init(lines);

        fs.print();

//        System.out.println(fs.sumDirSizes(100000));
        System.out.println("usedDiskSpace: " + fs.usedDiskSpace());
        System.out.println("unUsedDiskSpace: " + fs.unUsedDiskSpace());
        System.out.println("spaceToFree: " + fs.spaceToFree());
        System.out.println("smallestDirectoryToDelete: " + fs.findSmallestDirectoryToDelete());
    }
}
