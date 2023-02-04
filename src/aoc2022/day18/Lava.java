package aoc2022.day18;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Lava {

    private final Set<Droplet> droplets = new HashSet<>();

    private Integer minX, maxX, minY, maxY, minZ, maxZ;

    public int calculateSurfaceArea(List<String> input) {
        int surfaceArea = 0;
        for (String string : input) {
            Droplet droplet = Droplet.parse(string);
            surfaceArea += 6;

            int neighbours = findNeighboursCount(droplet);
            surfaceArea -= neighbours * 2;

            updateBoundaries(droplet);
            droplets.add(droplet);
        }

        return surfaceArea;
    }

    private void updateBoundaries(Droplet droplet) {
        if (minX == null || droplet.x < minX) minX = droplet.x;
        if (maxX == null || droplet.x > maxX) maxX = droplet.x;
        if (minY == null || droplet.y < minY) minY = droplet.y;
        if (maxY == null || droplet.y > maxY) maxY = droplet.y;
        if (minZ == null || droplet.z < minZ) minZ = droplet.z;
        if (maxZ == null || droplet.z > maxZ) maxZ = droplet.z;
    }

    public int calculateOuterSurface() {
        LinkedList<Droplet> q = new LinkedList<>();
        Droplet root = new Droplet(minX - 1, minY - 1, minZ - 1);
        Set<Droplet> seen = new HashSet<>();
        q.add(root);

        int surface = 0;

        while (!q.isEmpty()) {
            Droplet droplet = q.removeLast();
            if (droplets.contains(droplet)) {
                continue;
            }
            if (droplet.x < minX - 1 || droplet.x > maxX + 1 ||
                droplet.y < minY - 1 || droplet.y > maxY + 1 ||
                droplet.z < minZ - 1 || droplet.z > maxZ + 1) {
                continue;
            }
            surface += checkNeighbour(droplet.copy(1, 0, 0), q, seen);
            surface += checkNeighbour(droplet.copy(-1, 0, 0), q, seen);
            surface += checkNeighbour(droplet.copy(0, 1, 0), q, seen);
            surface += checkNeighbour(droplet.copy(0, -1, 0), q, seen);
            surface += checkNeighbour(droplet.copy(0, 0, 1), q, seen);
            surface += checkNeighbour(droplet.copy(0, 0, -1), q, seen);
        }

        return surface;
    }

    private int checkNeighbour(Droplet neighbour, LinkedList<Droplet> q, Set<Droplet> seen) {
        if (droplets.contains(neighbour)) {
            return 1;
        } else if (seen.add(neighbour)) {
            q.add(neighbour);
        }
        return 0;
    }

    private int findNeighboursCount(Droplet droplet) {
        int neighbours = 0;
        if (droplets.contains(droplet.copy(1, 0, 0))) neighbours++;
        if (droplets.contains(droplet.copy(-1, 0, 0))) neighbours++;
        if (droplets.contains(droplet.copy(0, 1, 0))) neighbours++;
        if (droplets.contains(droplet.copy(0, -1, 0))) neighbours++;
        if (droplets.contains(droplet.copy(0, 0, 1))) neighbours++;
        if (droplets.contains(droplet.copy(0, 0, -1))) neighbours++;

        return neighbours;
    }

    public static class Droplet {
        final int x;
        final int y;
        final int z;

        public Droplet(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Droplet copy(int dx, int dy, int dz) {
            return new Droplet(x + dx, y + dy, z + dz);
        }

        public static Droplet parse(String string) {
            String[] coordinates = string.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int z = Integer.parseInt(coordinates[2]);
            return new Droplet(x, y, z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Droplet droplet = (Droplet) o;
            return x == droplet.x && y == droplet.y && z == droplet.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

}
