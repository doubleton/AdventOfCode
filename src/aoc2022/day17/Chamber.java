package aoc2022.day17;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
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

    private final LinkedList<Byte> well = new LinkedList<>();

    private char[] moves;

    private final Map<CacheKey, CacheValue> cache = new HashMap<>();

    public void init(String moves) {
        this.moves = moves.toCharArray();
    }

    public void simulate(long count) {
        AtomicInteger step = new AtomicInteger(0);
        long offset = 0;

        for (long i = 0; i < count; i++) {
            int rockIndex = (int) (i % ROCKS.length);
            Rock rock = ROCKS[rockIndex].copy();
            int rockBottom = well.size() + 3;
            simulateMoves(step, rock, rockBottom);

            byte hash = calculateHash();
            int move = step.get() % moves.length;
            long height = getHeight();
            CacheKey key = new CacheKey(move, rockIndex, hash);
            if (height > 50 && cache.containsKey(key)) {
                CacheValue value = cache.get(key);
                long lastHeight = value.height();
                long lastRocksCount = value.rocksCount();

                long remainder = count - i;
                long reps = remainder / (i - lastRocksCount);
                offset = reps * (height - lastHeight);
                i += reps * (i - lastRocksCount);
                cache.clear();
            } else {
                cache.put(key, new CacheValue(height, i));
            }
        }

        System.out.println("Total height: " + (getHeight() + offset));
    }

    private byte calculateHash() {
        byte hash = 0;

        int height = well.size();
        ListIterator<Byte> it = well.listIterator(height);
        while (it.hasPrevious() && it.previousIndex() >= height - 50) {
            hash ^= it.previous();
        }
        return hash;
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
        return well.size();
    }

    private record CacheKey(int move, int rock, int hash) { }
    private record CacheValue(long height, long rocksCount) { }
}
