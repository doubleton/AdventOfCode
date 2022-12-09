package aoc2022.day7.filesystem;

public class File implements Entry {
    final String name;
    final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean isDir() {
        return false;
    }

    @Override
    public String print() {
        return "- " + name + " (file, size=" + size + ")";
    }
}
