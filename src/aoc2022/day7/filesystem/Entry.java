package aoc2022.day7.filesystem;

public interface Entry {

    String getName();

    int getSize();

    boolean isDir();

    String print();
}
