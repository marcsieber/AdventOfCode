package year2024.day02;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2024/day/">Day 02</a>
 */
public class Day02Solution {

    //private static final String inputFile = "2024/provided/day02-example.txt";
    private static final String inputFile = "2024/provided/day02.txt";

    public static void main(String[] args) {

        final List<String> input = Utils.readInputFromResources(inputFile);
        final List<List<Integer>> reports = input.stream()
                .map(Utils::readAsListOfInt)
                .toList();

        final long countValid = countValid(reports);
        System.out.println(countValid);

        final long countValidWithDampener = countValidWithDampener(reports);
        System.out.println(countValidWithDampener);
    }

    private static long countValid(List<List<Integer>> reports) {
        return reports.stream()
                .filter(Day02Solution::isStrictlyMonotone)
                .filter(Day02Solution::isValidDelta)
                .count();
    }

    private static long countValidWithDampener(List<List<Integer>> reports) {
        int counter = 0;
        for (List<Integer> report : reports) {
            if (isStrictlyMonotone(report) && isValidDelta(report)) {
                counter++;

            } else {
                for (List<Integer> subReport : listVariationsWithOneRemoved(report)) {
                    if ( isStrictlyMonotone(subReport) && isValidDelta(subReport)) {
                        counter++;
                        break;
                    }
                }
            }
        }
        return counter;
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

    private static List<List<Integer>> listVariationsWithOneRemoved(List<Integer> listOfNumbers ) {
        final List<List<Integer>> variations = new ArrayList<>();
        for (int i = 0; i < listOfNumbers.size(); i++) {
            final List<Integer> list = new ArrayList<>(listOfNumbers);
            //noinspection RedundantCast
            list.remove( (int) i);
            variations.add(list);
        }
        return variations;
    }
}
