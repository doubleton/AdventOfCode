package aoc2022.day22;

public enum Facing {

    RIGHT(0), DOWN(1), LEFT(2), UP(3);

    final int cost;

    public Facing turn(PathItem pathItem) {
        return switch (this) {
            case RIGHT -> switch (pathItem.kind) {
                case MOVE -> this;
                case RIGHT -> DOWN;
                case LEFT -> UP;
            };
            case DOWN -> switch (pathItem.kind) {
                case MOVE -> this;
                case RIGHT -> LEFT;
                case LEFT -> RIGHT;
            };
            case LEFT -> switch (pathItem.kind) {
                case MOVE -> this;
                case RIGHT -> UP;
                case LEFT -> DOWN;
            };
            case UP -> switch (pathItem.kind) {
                case MOVE -> this;
                case RIGHT -> RIGHT;
                case LEFT -> LEFT;
            };
        };
    }

    Facing(int cost) {
        this.cost = cost;
    }
}
