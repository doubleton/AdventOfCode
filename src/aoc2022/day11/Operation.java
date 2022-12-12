package aoc2022.day11;

public abstract class Operation {

    abstract long inspect(long item);

    public static class MultiplyN extends Operation {

        private final long n;

        public MultiplyN(long n) {
            this.n = n;
        }

        @Override
        long inspect(long item) {
            return item * n;
        }
    }

    public static class Square extends Operation {

        @Override
        long inspect(long item) {
            return item * item;
        }
    }

    public static class AddN extends Operation {

        private final long n;

        public AddN(long n) {
            this.n = n;
        }

        @Override
        long inspect(long item) {
            return item + n;
        }
    }

    public static class DivideN extends Operation {

        private final long n;

        public DivideN(long n) {
            this.n = n;
        }

        @Override
        long inspect(long item) {
            return item / n;
        }
    }

    public static class Mod extends Operation {

        private final long n;

        public Mod(long n) {
            this.n = n;
        }

        @Override
        long inspect(long item) {
            return item % n;
        }
    }
}
