package aoc2022.day12;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day12 {

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readLines(args[1]);
        Graph graph = new Graph();
        graph.init(input);

        printShortestPathFromStart(graph);
        printShortestPathEver(graph);
    }

    private static void printShortestPathFromStart(Graph graph) {
        graph.printGraph();
        System.out.println("Shortest S->E path is " + graph.shortestPathLength(graph.getStartPos()));
    }

    private static void printShortestPathEver(Graph graph) {
        int minPath = Integer.MAX_VALUE;
        for (int i = 0; i < graph.getSquares().length; i++) {
            for (int j = 0; j < graph.getSquares()[i].length; j++) {
                char node = graph.getSquares()[i][j];
                if (node == 'a') {
                    int path = graph.shortestPathLength(new Graph.Pos(i, j));
                    if (path < minPath) {
                        minPath = path;
                    }
                }
            }
        }

        System.out.println("Shortest ever path is " + minPath);
    }
}
