package aoc2022.day8;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day8 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(args[1]);

        byte[][] trees = initTrees(lines);

        printTrees(trees);

        System.out.println("Visible: " + countVisible(trees));
        System.out.println("Max scenic score: " + maxScenicScore(trees));
    }

    private static int maxScenicScore(byte[][] trees) {
        int size = trees.length;
        int max = 0;
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                int score = scenicScore(i, j, trees);
                if (score > max) {
                    max = score;
                }
            }
        }
        return max;
    }

    private static int scenicScore(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        if (tree == 0) {
            return 1;
        }
        return visibleToLeft(i, j, trees) *
            visibleToRight(i, j, trees) *
            visibleToTop(i, j, trees) *
            visibleToBottom(i, j, trees);
    }

    private static int visibleToTop(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = i - 1; x >= 0; x--) {
            if (trees[x][j] >= tree) {
                return i - x;
            }
        }
        return i;
    }

    private static int visibleToBottom(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = i + 1; x < trees.length; x++) {
            if (trees[x][j] >= tree) {
                return x - i;
            }
        }
        return trees.length - i - 1;
    }

    private static int visibleToLeft(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = j - 1; x >= 0; x--) {
            if (trees[i][x] >= tree) {
                return j - x;
            }
        }
        return j;
    }

    private static int visibleToRight(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = j + 1; x < trees.length; x++) {
            if (trees[i][x] >= tree) {
                return x - j;
            }
        }
        return trees.length - j - 1;
    }

    private static int countVisible(byte[][] trees) {
        int size = trees.length;
        int total = 4 * (size - 1);
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                if (treeVisible(i, j, trees)) {
                    total++;
                }
            }
        }
        return total;
    }

    private static boolean treeVisible(int i, int j, byte[][] trees) {
        int size = trees.length;
        byte tree = trees[i][j];
        if (tree == 0) {
            return false;
        }

        if (i < size / 2) {
            if (visibleFromTop(i, j, trees)) return true;
            if (visibleFromBottom(i, j, trees)) return true;
        } else {
            if (visibleFromBottom(i, j, trees)) return true;
            if (visibleFromTop(i, j, trees)) return true;
        }

        if (j < size / 2) {
            if (visibleFromLeft(i, j, trees)) return true;
            if (visibleFromRight(i, j, trees)) return true;
        } else {
            if (visibleFromRight(i, j, trees)) return true;
            if (visibleFromLeft(i, j, trees)) return true;
        }

        return false;
    }

    private static boolean visibleFromTop(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = i - 1; x >= 0; x--) {
            if (trees[x][j] >= tree) {
                return false;
            }
        }
        return true;
    }

    private static boolean visibleFromBottom(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = i + 1; x < trees.length; x++) {
            if (trees[x][j] >= tree) {
                return false;
            }
        }
        return true;
    }

    private static boolean visibleFromLeft(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = j - 1; x >= 0; x--) {
            if (trees[i][x] >= tree) {
                return false;
            }
        }
        return true;
    }

    private static boolean visibleFromRight(int i, int j, byte[][] trees) {
        byte tree = trees[i][j];
        for (int x = j + 1; x < trees.length; x++) {
            if (trees[i][x] >= tree) {
                return false;
            }
        }
        return true;
    }

    private static byte[][] initTrees(List<String> lines) {
        int size = lines.size();
        byte[][] trees = new byte[size][size];
        for (int i = 0; i < size; i++) {
            String line = lines.get(i);
            trees[i] = new byte[size];
            for (int j = 0; j < size; j++) {
                trees[i][j] = Byte.parseByte(line.substring(j, j+1));
            }
        }
        return trees;
    }

    private static void printTrees(byte[][] trees) {
        for (byte[] line : trees) {
            System.out.println(Arrays.toString(line));
        }
    }
}
