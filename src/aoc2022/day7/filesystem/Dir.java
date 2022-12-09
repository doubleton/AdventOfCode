package aoc2022.day7.filesystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dir implements Entry {
    final String name;
    final Map<String, Entry> entries;
    final Dir parent;

    public Dir(String name, Dir parent) {
        this.name = name;
        entries = new HashMap<>();
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return entries.values().stream().mapToInt(Entry::getSize).sum();
    }

    @Override
    public boolean isDir() {
        return true;
    }

    @Override
    public String print() {
        return "- " + name + " (dir)";
    }

    public void addEntry(Entry entry) {
        entries.put(entry.getName(), entry);
    }

    public Entry getEntry(String name) {
        return entries.get(name);
    }

    public Collection<Entry> getEntries() {
        return entries.values();
    }
}
