package year2024.day02;

import common.Utils;

import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2024/day/">Day 02</a>
 */
public class Day02Solution {

    //private static final String inputFile = "2024/provided/day02-example.txt";
    private static final String inputFile = "2024/provided/day02.txt";

    public static void main(String[] args) {

        final List<String> input = Utils.readInputFromResources(inputFile);
        final List<List<Integer>> levels = input.stream()
                .map(Utils::readAsListOfInt)
                .toList();

        final long countValid = countValidLevels(levels);
        System.out.println(countValid);
    }

    private static long countValidLevels(List<List<Integer>> levels) {
        return levels.stream()
                .filter(Day02Solution::isStrictlyMonotone)
                .filter(Day02Solution::isValidDelta)
                .count();
    }

    private static boolean isStrictlyMonotone(List<Integer> listOfNumbers) {
        if (listOfNumbers.size() < 2) { return false; }

        final float signum = Math.signum(listOfNumbers.get(0) - listOfNumbers.get(1));
        if (signum == 0.0f) { return false; }

        for (int i = 1; i < listOfNumbers.size() - 1; i++) {
            if (Math.signum(listOfNumbers.get(i) - listOfNumbers.get(i + 1)) != signum) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDelta(List<Integer> listOfNumbers) {
        for (int i = 0; i < listOfNumbers.size() - 1; i++) {
            final int diff = Math.abs(listOfNumbers.get(i) - listOfNumbers.get(i + 1));
            if ( ! (1 <= diff && diff <= 3)) {
                return false;
            }
        }
        return true;
    }

}
