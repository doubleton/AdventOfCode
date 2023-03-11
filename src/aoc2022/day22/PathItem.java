package aoc2022.day22;

public class PathItem {

    PathItemKind kind;
    Integer count;

    public PathItem(PathItemKind kind) {
        this(kind, null);
    }

    public PathItem(PathItemKind kind, Integer count) {
        this.kind = kind;
        this.count = count;
    }

    @Override
    public String toString() {
        return count != null ? count.toString() : kind.name();
    }

    public enum PathItemKind {
        MOVE, RIGHT, LEFT
    }
}
