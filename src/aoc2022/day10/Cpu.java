package aoc2022.day10;

import java.util.Set;

public class Cpu {

    private static final Set<Integer> CYCLES_TO_MONITOR = Set.of(20, 60, 100, 140, 180, 220);
    public static final int SCREEN_WIDTH = 40;

    private int x;
    private int cycle;
    private int signalStrength;

    public Cpu() {
        x = 1;
        cycle = 0;
    }

    public void execute(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "addx" -> addx(Integer.parseInt(parts[1]));
            case "noop" -> noop();
            default -> throw new RuntimeException("Unknown command " + command);
        }
    }

    private void addx(int value) {
        tick();
        tick();
        x += value;
    }

    private void noop() {
        tick();
    }

    private void tick() {
        cycle++;
        if (CYCLES_TO_MONITOR.contains(cycle)) {
            signalStrength += cycle * x;
        }
        draw();
    }

    private void draw() {
        int crtPosition = (cycle - 1) % SCREEN_WIDTH;
        if (crtPosition >= x - 1 && crtPosition <= x + 1) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }

        if (cycle % SCREEN_WIDTH == 0) {
            System.out.println();
        }
    }

    public int getSignalStrength() {
        return signalStrength;
    }
}
