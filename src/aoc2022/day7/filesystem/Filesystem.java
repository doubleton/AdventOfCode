package aoc2022.day7.filesystem;

import java.util.List;

public class Filesystem {

    private static final String COMMAND_PREFIX = "$";
    private static final String DIR_PREFIX = "dir";
    private static final String CD_PREFIX = "cd";
    private static final String PARENT_DIR_PREFIX = "..";

    private static final int TOTAL_DISK_SPACE = 70_000_000;
    private static final int REQUIRED_UNUSED_DISK_SPACE = 30_000_000;

    private final Dir root;

    public Filesystem() {
        root = new Dir("~", null);
        root.addEntry(new Dir("/", root));
    }

    public void init(List<String> output) {
        init(root, output);
    }

    private void init(Dir current, List<String> output) {
        if (output.isEmpty()) {
            return;
        }

        String line = output.remove(0);

        if (line.startsWith(COMMAND_PREFIX)) {
            String command = removePrefix(line, COMMAND_PREFIX);
            if (command.startsWith(CD_PREFIX)) {
                String dirName = removePrefix(command, CD_PREFIX);
                if (dirName.equals(PARENT_DIR_PREFIX)) {
                    init(current.parent, output);
                } else {
                    current = (Dir) current.getEntry(dirName);
                }
            }
        } else if (line.startsWith(DIR_PREFIX)) {
            String dirName = removePrefix(line, DIR_PREFIX);
            current.addEntry(new Dir(dirName, current));
        } else {
            String[] fileParts = line.split(" ");
            current.addEntry(new File(fileParts[1], Integer.parseInt(fileParts[0])));
        }

        init(current, output);
    }

    public int usedDiskSpace() {
        return root.getSize();
    }

    public int unUsedDiskSpace() {
        return TOTAL_DISK_SPACE - usedDiskSpace();
    }

    public int spaceToFree() {
        int unUsedDiskSpace = unUsedDiskSpace();
        if (unUsedDiskSpace > REQUIRED_UNUSED_DISK_SPACE) {
            return 0;
        } else {
            return REQUIRED_UNUSED_DISK_SPACE - unUsedDiskSpace;
        }
    }

    public int findSmallestDirectoryToDelete() {
        return findSmallestDirectorySize(root, TOTAL_DISK_SPACE, spaceToFree());
    }

    public int findSmallestDirectorySize(Dir dir, int min, int threshold) {
        int dirSize = dir.getSize();
        if (dirSize >= threshold && dirSize < min) {
            min = dirSize;
        }

        for (Entry entry : dir.getEntries()) {
            if (entry.isDir()) {
                min = findSmallestDirectorySize((Dir) entry, min, threshold);
            }
        }

        return min;
    }

    private static String removePrefix(String command, String prefix) {
        return command.substring(prefix.length() + 1);
    }

    public void print() {
        print(root, "");
    }

    private void print(Entry entry, String indent) {
        System.out.println(indent + entry.print());
        if (entry.isDir()) {
            Dir dir = (Dir) entry;
            for (Entry e : dir.getEntries()) {
                print(e, indent + "  ");
            }
        }
    }

    public int sumDirSizes(int threshold) {
        return sumDirSizes(root, threshold);
    }

    private int sumDirSizes(Dir dir, int threshold) {
        int total = 0;

        int size = dir.getSize();
        if (size <= threshold) {
            total += size;
        }

        for (Entry entry : dir.getEntries()) {
            if (entry.isDir()) {
                total += sumDirSizes((Dir) entry, threshold);
            }
        }

        return total;
    }
}
