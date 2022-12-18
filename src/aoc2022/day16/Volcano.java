package aoc2022.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Volcano {

    private final static Pattern VALVE_PATTERN = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)$");

    private final Map<String, Set<String>> edges;

    private final Map<String, Integer> flowRatesByValve;

    private final Map<String, Map<String, List<String>>> paths;

    public Volcano() {
        edges = new HashMap<>();
        flowRatesByValve = new HashMap<>();
        paths = new HashMap<>();
    }

    public void init(List<String> input) {
        for (String line : input) {
            Matcher matcher = VALVE_PATTERN.matcher(line);
            if (matcher.find()) {
                String node = matcher.group(1);
                int flowRate = Integer.parseInt(matcher.group(2));
                String[] nodes = matcher.group(3).split(", ");
                edges.put(node, Set.of(nodes));
                flowRatesByValve.put(node, flowRate);
            }
        }
        initPaths();
    }

    private void initPaths() {
        for (Map.Entry<String, Set<String>> nodeEntry : edges.entrySet()) {
            String node = nodeEntry.getKey();

            Set<String> neighbours = nodeEntry.getValue();
            for (String neighbour : neighbours) {
                putTwoKeyMap(paths, node, neighbour, List.of(neighbour));
            }

            Set<String> otherNodes = new HashSet<>(edges.keySet());
            otherNodes.removeAll(neighbours);
            for (String otherNode : otherNodes) {
                List<String> path = path(node, otherNode);
                putTwoKeyMap(paths, node, otherNode, path);
            }
        }
    }

    private static <V> void putTwoKeyMap(Map<String, Map<String, V>> map, String key1, String key2, V value) {
        map.computeIfAbsent(key1, key -> new HashMap<>()).putIfAbsent(key2, value);
    }

    public int findMaxPressure() {
        Set<String> closedValves = flowRatesByValve.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        return findMaxPressure("AA", closedValves, 30, 0, 0);
    }

    private int findMaxPressure(String valve, Set<String> closedValves, int time, int pressure, int totalPressure) {
        if (time == 0) {
            return totalPressure;
        }

        int maxPressure = totalPressure + pressure * time;
        for (String nextValve : closedValves) {
            int stepsToNextValve = paths.get(valve).get(nextValve).size();
            if (stepsToNextValve >= time) {
                continue;
            }

            Set<String> newClosedValves = copyAndRemove(closedValves, nextValve);
            int newTime = time - stepsToNextValve - 1;
            int newPressure = pressure + flowRatesByValve.get(nextValve);

            int newMaxPressure = findMaxPressure(nextValve, newClosedValves, newTime, newPressure, totalPressure + pressure * (stepsToNextValve + 1));
            maxPressure = Math.max(maxPressure, newMaxPressure);
        }

        return maxPressure;
    }

    public int findMaxPressureWithElephant() {
        String[] allValves = flowRatesByValve.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toArray(String[]::new);

        int size = allValves.length;
        int count = (1 << size) - 1;
        int maxPressure = 0;
        // iterate over all possible options to split allValves into two sets
        // checking all numbers from 1 to (2^size - 2) in binary form:
        // 0 0 0 0 1
        // 0 0 0 1 0
        // 0 0 0 1 1
        // ...
        // 1 1 1 0 1
        // 1 1 1 1 0
        for (int i = 1; i < count; i++) {
            Set<String> myValves = new HashSet<>();
            Set<String> elValves = new HashSet<>();
            for (int j = 0; j < size; j++) {
                String valve = allValves[j];
                if ((i >> j) % 2 == 0) {
                    elValves.add(valve);
                } else {
                    myValves.add(valve);
                }
            }
            int myPressure = findMaxPressure("AA", myValves, 26, 0, 0);
            int elPressure = findMaxPressure("AA", elValves, 26, 0, 0);
            maxPressure = Math.max(maxPressure, myPressure + elPressure);
        }

        return maxPressure;
    }

    private static Set<String> copyAndRemove(Set<String> values, String value) {
        Set<String> newValues = new HashSet<>(values);
        newValues.remove(value);
        return newValues;
    }

    private static List<String> makeStep(List<String> path) {
        List<String> newPath = new LinkedList<>(path);
        newPath.remove(0);
        return newPath;
    }

    // BFS to find the shortest path from source to target
    private List<String> path(String source, String target) {
        if (source.equals(target)) {
            return List.of();
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> path = new HashMap<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()){
            String currentNode = queue.poll();
            for (String node : edges.get(currentNode)) {
                if (!visited.contains(node)) {
                    visited.add(node);
                    queue.add(node);
                    path.put(node, currentNode);
                    if (node.equals(target)) {
                        break;
                    }
                }
            }
        }

        LinkedList<String> result = new LinkedList<>();
        result.add(target);

        String node = target;
        while (!node.equals(source)) {
            node = path.get(node);
            if (!node.equals(source)) {
                result.addFirst(node);
            }
        }

        return result;
    }

    public void print() {
        for (Map.Entry<String, Set<String>> entry : edges.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + ", flowRate: " + flowRatesByValve.get(entry.getKey()));
        }
    }

    public void printPaths() {
        for (Map.Entry<String, Map<String, List<String>>> entry : paths.entrySet()) {
            String node = entry.getKey();
            for (Map.Entry<String, List<String>> pathsToNode : entry.getValue().entrySet()) {
                String otherNode = pathsToNode.getKey();
                System.out.println("Path " + node + " -> " + otherNode + " is: " + pathsToNode.getValue());
            }
        }
    }

}
