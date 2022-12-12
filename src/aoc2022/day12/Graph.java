package aoc2022.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {

    private static final char START = 'S';
    private static final char END = 'E';

    private char[][] squares;

    private Pos startPos;
    private Pos endPos;

    public void init(List<String> input) {
        squares = new char[input.size()][];
        int i = 0;
        for (String line : input) {
            squares[i] = line.toCharArray();

            int startIndex = line.indexOf(START);
            if (startIndex >= 0) {
                startPos = new Pos(i, startIndex);
            }

            int endIndex = line.indexOf(END);
            if (endIndex >= 0) {
                endPos = new Pos(i, endIndex);
            }

            i++;
        }
    }

    public char[][] getSquares() {
        return squares;
    }

    public Pos getStartPos() {
        return startPos;
    }

    public int shortestPathLength(Pos startPos) {
        int maxI = squares.length;
        int maxJ = squares[0].length;

        int[][] distances = new int[squares.length][];
        for (int i = 0; i < squares.length; i++) {
            distances[i] = new int[squares[i].length];
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        Set<Pos> marked = new HashSet<>();
        distances[startPos.i][startPos.j] = 0;

        PriorityQueue<Pos> queue = new PriorityQueue<>(Comparator.comparingInt(p -> distances[p.i][p.j]));
        queue.add(startPos);

        while (!queue.isEmpty()) {
            Pos node = queue.poll();
            if (marked.contains(node)) {
                continue;
            }

            marked.add(node);

            int distance = distances[node.i][node.j];
            for (Pos next : neighbours(node, maxI, maxJ)) {
                if (distances[next.i][next.j] > distance + 1) {
                    distances[next.i][next.j] = distance + 1;
                    queue.add(next);
                }
            }
        }

        return distances[endPos.i][endPos.j];
    }

    private List<Pos> neighbours(Pos node, int maxI, int maxJ) {
        List<Pos> result = new ArrayList<>();
        if (node.j > 0) {
            Pos next = new Pos(node.i, node.j - 1);  // left
            if (pathExists(node, next)) {
                result.add(next);
            }
        }
        if (node.j < maxJ - 1) {
            Pos next = new Pos(node.i, node.j + 1); // right
            if (pathExists(node, next)) {
                result.add(next);
            }
        }
        if (node.i > 0) {
            Pos next = new Pos(node.i - 1, node.j); // up
            if (pathExists(node, next)) {
                result.add(next);
            }
        }
        if (node.i < maxI - 1) {
            Pos next = new Pos(node.i + 1, node.j); // down
            if (pathExists(node, next)) {
                result.add(next);
            }
        }
        return result;
    }

    public void printGraph() {
        for (char[] square : squares) {
            System.out.println(square);
        }
        System.out.println("Start pos: " + startPos);
        System.out.println("End pos: " + endPos);
    }

    private boolean pathExists(Pos p1, Pos p2) {
        return pathExists(squares[p1.i][p1.j], squares[p2.i][p2.j]);
    }

    public static boolean pathExists(char n1, char n2) {
        return normalize(n2) - normalize(n1) <= 1;
    }

    private static char normalize(char square) {
        return switch (square) {
            case START -> 'a';
            case END -> 'z';
            default -> square;
        };
    }

    public static class Pos {
        int i;
        int j;

        public Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return i + "," + j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return i == pos.i && j == pos.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
