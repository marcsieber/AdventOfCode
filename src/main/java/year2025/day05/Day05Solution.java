package year2025.day05;

import common.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/5">Day 05</a>
 */
public class Day05Solution {

    //    private static final String inputFile = "2025/provided/day05-example.txt";
    private static final String inputFile = "2025/provided/day05.txt";

    static void main() {

        final List<String>              input    = Utils.readInputFromResources(inputFile);
        final InventoryManagementSystem system   = populateInventoryManagementSystem(input);
        final List<Long>                products = listOfProducts(input);

        System.out.println(findFreshIngredients(system, products));
        System.out.println(system.totalFreshIds());
    }

    private static int findFreshIngredients(InventoryManagementSystem system, List<Long> products) {
        int fresh = 0;
        for (long product : products) {
            if (system.isProductFresh(product)) {
                fresh++;
            }
        }
        return fresh;
    }

    private static class InventoryManagementSystem {
        private final List<LongRange> productRanges = new ArrayList<>();

        private void addProductRange(LongRange range) {
            productRanges.add(range);
        }

        private boolean isProductFresh(long product) {
            for (LongRange range : productRanges) {
                if (range.contains(product)) {
                    return true;
                }
            }
            return false;
        }

        private long totalFreshIds() {
            final List<LongRange> sortedRanges = new ArrayList<>(this.productRanges);
            Collections.sort(sortedRanges);

            final List<LongRange> rangesWithoutOverlap = new ArrayList<>();
            LongRange             previousRange        = new LongRange(- 1, 0);
            for (LongRange currentRange : sortedRanges) {
                final LongRange.Intersection intersection = previousRange.intersects(currentRange);
                if (intersection == LongRange.Intersection.NONE) {
                    rangesWithoutOverlap.add(currentRange);
                    previousRange = currentRange;

                } else {
                    if (! rangesWithoutOverlap.isEmpty()) {
                        rangesWithoutOverlap.removeLast();
                    }

                    final LongRange rangeUnion = previousRange.union(currentRange);
                    rangesWithoutOverlap.add(rangeUnion);
                    previousRange = rangeUnion;
                }
            }

            return rangesWithoutOverlap.stream()
                    .mapToLong(LongRange::size)
                    .sum();
        }


    }

    private record LongRange(long start, long end) implements Comparable<LongRange> {
        enum Intersection {NONE, FIRST_START, FIRST_END, FULL}

        private long size() { return end - start + 1; }

        private boolean contains(long value) {
            return start <= value && value <= end;
        }

        private LongRange union(LongRange other) {
            //@formatter:off
            return switch(intersects(other)) {
                case FULL         -> this;
                case FIRST_START -> new LongRange(this.start, other.end);
                case FIRST_END   -> new LongRange(other.start, this.end);
                case NONE        -> throw new IllegalStateException("Ranges do not intersect");
            };
            //@formatter:on
        }

        private Intersection intersects(LongRange other) {
            if (this.start <= other.start && other.end <= this.end) {
                return Intersection.FULL;

            } else if (this.start <= other.start && other.start <= this.end) {
                return Intersection.FIRST_START;

            } else if (this.start <= other.end && other.end <= this.end) {
                return Intersection.FIRST_END;

            } else if (other.end < this.start || this.end < other.start) {
                return Intersection.NONE;
            }
            throw new IllegalStateException("Silly programmer :-)");
        }

        @Override
        public int compareTo(LongRange o) {
            return Long.compare(this.start, o.start);
        }
    }

    // Utilities
    // ------------------------------------------------------------------------

    private static InventoryManagementSystem populateInventoryManagementSystem(List<String> input) {
        final InventoryManagementSystem system = new InventoryManagementSystem();
        input.stream()
                .takeWhile(s -> ! s.isEmpty())
                .forEach(s -> {
                    final String[] split = s.split("-");
                    system.addProductRange(new LongRange(Long.parseLong(split[0]), Long.parseLong(split[1])));
                });
        return system;
    }

    private static List<Long> listOfProducts(List<String> input) {
        return input.stream()
                .dropWhile(s -> ! s.isEmpty())
                .filter(s -> ! s.isEmpty())
                .map(Long::parseLong)
                .toList();
    }

}
