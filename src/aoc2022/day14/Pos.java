package aoc2022.day14;

import java.util.Objects;

public class Pos {

    int i;
    int j;

    public Pos(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void normalize(int minJ) {
        j -= minJ;
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
