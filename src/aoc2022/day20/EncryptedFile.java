package aoc2022.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EncryptedFile {

    private final List<Long> numbers;
    private final Map<Integer, Integer> init2NewPositions = new HashMap<>();
    private final Map<Integer, Integer> new2InitPositions = new HashMap<>();

    public EncryptedFile(List<Long> numbers) {
        this.numbers = numbers;
        for (int i = 0; i < numbers.size(); i++) {
            init2NewPositions.put(i, i);
            new2InitPositions.put(i, i);
        }
    }

    public static EncryptedFile init(List<String> input) {
        List<Long> numbers = new LinkedList<>();
        for (String s : input) {
            numbers.add(Long.parseLong(s));
        }
        return new EncryptedFile(numbers);
    }

    public void decrypt() {
        decrypt(1, 1, false);
    }

    public void decrypt(int times, int decryptionKey, boolean debug) {
        numbers.replaceAll(v -> v * decryptionKey);

        List<Long> initialNumbers = new ArrayList<>(numbers);
        int size = numbers.size();

        for (int round = 0; round < times; round++) {
            if (debug) System.out.println("Round: " + round);

            for (int i = 0; i < size; i++) {
                if (debug) System.out.println("Step: " + i);

                long number = initialNumbers.get(i);
                int position = init2NewPositions.get(i);

                if (number == 0) {
                    continue;
                }

                int newPosition = (int) (position + number % (size - 1));
                if (newPosition <= 0) {
                    newPosition = size - 1 + newPosition;
                } else if (newPosition >= size) {
                    newPosition = newPosition - size + 1;
                }
                if (debug) System.out.println("number: " + number + ", position: " + position + ", newPosition: " + newPosition);

                if (newPosition > position) {
                    for (int j = position + 1; j <= newPosition; j++) {
                        int initPos = new2InitPositions.get(j);
                        init2NewPositions.compute(initPos, (k, v) -> {
                            int newPos = v - 1;
                            new2InitPositions.put(newPos, initPos);
                            return newPos;
                        });
                    }
                } else {
                    for (int j = position - 1; j >= newPosition; j--) {
                        int initPos = new2InitPositions.get(j);
                        init2NewPositions.compute(initPos, (k, v) -> {
                            int newPos = v + 1;
                            new2InitPositions.put(newPos, initPos);
                            return newPos;
                        });
                    }
                }

                init2NewPositions.put(i, newPosition);
                new2InitPositions.put(newPosition, i);

                long removedNumber = numbers.remove(position);
                if (removedNumber != number) {
                    System.out.println("Ouch... something is wrong: removedNumber = " + removedNumber + ", but expected to be " + number);
                }
                numbers.add(newPosition, number);

                if (debug) print();
            }
        }
    }

    public void print() {
        System.out.println("numbers: " + numbers);
        System.out.println("init2NewPositions: " + init2NewPositions);
        System.out.println("new2InitPositions: " + new2InitPositions);
    }

    public long findCoordinates() {
        return findCoordinates(false);
    }
    public long findCoordinates(boolean debug) {
        int[] targetPositions = new int[]{1000, 2000, 3000};
        int zeroPosition = numbers.indexOf(Long.parseLong("0"));
        if (debug) System.out.println("zeroPosition: " + zeroPosition);
        long sum = 0;
        for (int targetPosition : targetPositions) {
            int index = (zeroPosition + targetPosition) % numbers.size();
            long number = numbers.get(index);
            if (debug) System.out.println("numbers[" + index + "]: " + number);
            sum += number;
        }
        return sum;
    }
}
