package year2025.day05;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/5">Day 05</a>
 */
public class Day05Solution {

//    private static final String inputFile = "2025/provided/day05-example.txt";
    private static final String inputFile = "2025/provided/day05.txt";

    static void main() {

        final List<String> input = Utils.readInputFromResources(inputFile);
        final InventoryManagementSystem system = populateInventoryManagementSystem(input);
        final List<Long> products = listOfProducts(input);

        System.out.println(findFreshIngredients(system, products));
    }

    private static int findFreshIngredients(InventoryManagementSystem system, List<Long> products) {
        int fresh = 0;
        for (long product : products) {
            if (system.isProductAvailable(product)) {
                fresh ++;
            }
        }
        return fresh;
    }

    private record LongRange(long start, long end) {
        public boolean contains(long value) {
            return start <= value && value <= end;
        }
    }

    private static class InventoryManagementSystem {
        private final ArrayList<LongRange> productRanges = new ArrayList<>();

        private void addProductRange(LongRange range) {
            productRanges.add(range);
        }

        private boolean isProductAvailable(long product) {
            for (LongRange range : productRanges) {
                if (range.contains(product)) {
                    return true;
                }
            }
            return false;
        }
    }


    // Utilities
    // ------------------------------------------------------------------------

    private static InventoryManagementSystem populateInventoryManagementSystem(List<String> input) {
        final InventoryManagementSystem system = new InventoryManagementSystem();
        input.stream()
                .takeWhile(s -> ! s.isEmpty())
                .forEach(s -> {
                    String[] split = s.split("-");
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
