package aoc2022.day17;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Chamber {

    private static final Rock[] ROCKS = {
        new Rock(new byte[]{
                0b00011110
        }),
        new Rock(new byte[]{
                0b00001000,
                0b00011100,
                0b00001000
        }),
        new Rock(new byte[]{
                0b00000100,
                0b00000100,
                0b00011100
        }),
        new Rock(new byte[]{
                0b00010000,
                0b00010000,
                0b00010000,
                0b00010000
        }),
        new Rock(new byte[]{
                0b00011000,
                0b00011000
        }),
    };

    private LinkedList<Byte> well = new LinkedList<>();

    private long accHeight;

    private char[] moves;

    public void init(String moves) {
        this.moves = moves.toCharArray();
    }

    public void simulate(long count) {
        AtomicInteger step = new AtomicInteger(0);
        for (long i = 0; i < count; i++) {
            Rock rock = ROCKS[(int) (i % ROCKS.length)].copy();
            int rockBottom = well.size() + 3;
            simulateMoves(step, rock, rockBottom);
//            tryReduce();
        }
        System.out.println("Last step: " + step.get());
    }

    private void tryReduce() {
        int height = well.size();
        ListIterator<Byte> it = well.listIterator(height);
        while (it.hasPrevious()) {
            Byte line = it.previous();
            height--;
            if (line == 0b01111111) {
                well = new LinkedList<>(well.subList(height, well.size()));
                accHeight += height;
            }
        }
    }

    private void simulateMoves(AtomicInteger step, Rock rock, int rockBottom) {
        boolean blocked = false;
        int rockHeight = rock.getShape().length;
        do {
            char move = moves[step.get() % moves.length];
            switch (move) {
                case '<' -> rock.moveLeft(well, rockBottom);
                case '>' -> rock.moveRight(well, rockBottom);
            }

            if (rockBottom > well.size()) {
                step.incrementAndGet();
                rockBottom--;
                continue;
            }

            if (rockBottom == 0) {
                blocked = true;
            } else {
                for (int i = 0; i < rockHeight; i++) {
                    if (rockBottom + i - 1 == well.size()) {
                        break;
                    }
                    byte rockLine = rock.getShape()[rockHeight - i - 1];
                    byte wellLine = well.get(rockBottom + i - 1);
                    if ((rockLine ^ wellLine) != (rockLine | wellLine)) {
                        blocked = true;
                        break;
                    }
                }
            }

            if (blocked) {
                for (int i = 0; i < rockHeight; i++) {
                    byte rockLine = rock.getShape()[rockHeight - i - 1];
                    if (rockBottom + i >= well.size()) {
                        well.add(rockLine);
                    } else {
                        byte wellLine = well.get(rockBottom + i);
                        well.set(rockBottom + i, (byte) (wellLine | rockLine));
                    }
                }
            } else {
                rockBottom--;
            }
            step.incrementAndGet();
        } while (!blocked);
    }

    public void print() {
        ListIterator<Byte> it = well.listIterator(well.size());
        while (it.hasPrevious()) {
            Byte line = it.previous();

            System.out.print("|");
            for (int i = 0; i < 7; i++) {
                if ((line & (1 << (6 - i))) == 0) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println("|");
        }
        System.out.println("+-------+");
    }

    public long getHeight() {
        return well.size() + accHeight;
    }
}
