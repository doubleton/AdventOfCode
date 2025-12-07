package aoc2022.day24;

public record Position(int row, int col) {
    public Position moveBlizzard(Direction direction, int maxRow, int maxCol) {
        return switch (direction) {
            case UP -> new Position(row == 1 ? maxRow : row - 1, col);
            case DOWN -> new Position(row == maxRow ? 1 : row + 1, col);
            case LEFT -> new Position(row, col == 1 ? maxCol : col - 1);
            case RIGHT -> new Position(row, col == maxCol ? 1 : col + 1);
            case WAIT -> this;
        };
    }

    public Position moveExpedition(Direction direction, int maxRow, int maxCol) {
        if (row == 0) {
            return direction == Direction.DOWN ? new Position(row + 1, col) : null;
        }
        if (row == 1 && col == 1 && direction == Direction.UP) {
            return new Position(0, 1);
        }

        return switch (direction) {
            case UP -> row == 1 ? null : new Position(row - 1, col);
            case DOWN -> row == maxRow ? null : new Position(row + 1, col);
            case LEFT -> col == 1 ? null : new Position(row, col - 1);
            case RIGHT -> col == maxCol ? null : new Position(row, col + 1);
            case WAIT -> this;
        };
    }
}
