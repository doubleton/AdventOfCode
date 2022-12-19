package aoc2022.day17;

import java.util.LinkedList;

public class Rock {

    private final byte[] shape;
    private final int shapeHeight;

    public Rock(byte[] shape) {
        this.shape = shape;
        this.shapeHeight = shape.length;
    }

    public byte[] getShape() {
        return shape;
    }

    public Rock copy() {
        return new Rock(shape.clone());
    }

    public void moveLeft(LinkedList<Byte> well, int rockBottom) {
        for (int i = 0; i < shapeHeight; i++) {
            byte rockLine = shape[shapeHeight - i - 1];
            if (rockLine >= 0b01000000) {
                return;
            }

            if (checkWell(well, rockBottom + i, (byte) (rockLine << 1))) {
                return;
            }
        }

        for (int i = 0; i < shape.length; i++) {
            shape[i] = (byte) (shape[i] << 1);
        }
    }

    public void moveRight(LinkedList<Byte> well, int rockBottom) {
        for (int i = 0; i < shapeHeight; i++) {
            byte rockLine = shape[shapeHeight - i - 1];
            if (rockLine % 2 == 1) {
                return;
            }

            if (checkWell(well, rockBottom + i, (byte) (rockLine >> 1))) {
                return;
            }
        }

        for (int i = 0; i < shape.length; i++) {
            shape[i] = (byte) (shape[i] >> 1);
        }
    }

    private boolean checkWell(LinkedList<Byte> well, int wellIndex, byte rockLine) {
        int wellHeight = well.size();

        if (wellIndex < wellHeight) {
            byte wellLine = well.get(wellIndex);
            return (rockLine ^ wellLine) != (rockLine | wellLine);
        }

        return false;
    }
}
