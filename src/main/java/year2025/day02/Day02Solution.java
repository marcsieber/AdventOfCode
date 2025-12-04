package year2025.day02;

import common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @see <a href="http://adventofcode.com/2025/day/2">Day 02</a>
 */
public class Day02Solution {

    //private static final String inputFile = "2025/provided/day02-example.txt";
    private static final String inputFile = "2025/provided/day02.txt";

    public static void main(String[] args) {

        final List<ProductSeries> input = Utils.readInputFromResources(inputFile).stream()
                .map(s -> Arrays.asList(s.split(",")))
                .flatMap(List::stream)
                .map(ProductSeries::from)
                .toList();

        System.out.println(addInValidNumbers(input, Day02Solution::isValidProductNumber1));
        System.out.println(addInValidNumbers(input, Day02Solution::isValidProductNumber2));
    }

    private static long addInValidNumbers(List<ProductSeries> series, Predicate<Long> tester) {
        return series.stream()
                .map(l -> findInValidProductNumbers(l, tester))
                .flatMap(List::stream)
                .reduce(0L, Long::sum);
    }


    private static List<Long> findInValidProductNumbers(ProductSeries series, Predicate<Long> tester) {
        final List<Long> numbers = new ArrayList<>();
        for (long i = series.start; i <= series.end; i++) {
            if (! tester.test(i)) {
                numbers.add(i);
            }

        }
        return numbers;
    }


    private static boolean isValidProductNumber1(long number) {
        final String asStr = String.valueOf(number);
        if (asStr.length() % 2 == 1) { return true; }

        final int middle = asStr.length() / 2;
        for (int i = 0; i < middle; i++) {
            if (asStr.charAt(i) != asStr.charAt(middle + i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidProductNumber2(long number) {
        final String aStr      = String.valueOf(number);
        final int    strLength = aStr.length();

        int patternLength = 1;
        while (patternLength <= strLength / 2) {
            final String substring = aStr.substring(0, patternLength);
            if (aStr.replace(substring, "").isEmpty()) {
                return false;
            }
            patternLength++;
        }
        return true;
    }

    // Utilities
    // ------------------------------------------------------------------------

    private record ProductSeries(long start, long end) {
        static ProductSeries from(String s) {
            final String[] split = s.split("-");
            final long     start = Long.parseLong(split[0]);
            final long     end   = Long.parseLong(split[1]);

            return new ProductSeries(start, end);
        }
    }


}
