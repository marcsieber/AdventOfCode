package year2023.day9;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/9">Day 9</a>
 */
public class Day9Solution {

//  private static final String inputFile = "2023/provided/day9-example.txt";
    private static final String inputFile = "2023/provided/day9.txt";

    public static void main(String[] args) {

        // general
        List<String> input = Utils.readInputFromResources(inputFile);

        // first
        long sum = input.stream()
                .map(Utils::readAsListOfLong)
                .mapToLong(values -> findNextValue(values, true))
                .sum();

        System.out.println(sum);

        // second
         sum = input.stream()
                .map(Utils::readAsListOfLong)
                .mapToLong(values -> findNextValue(values, false))
                .sum();

        System.out.println(sum);
    }

    private static long findNextValue(List<Long> values, boolean last) {
        if (values.stream().allMatch( v -> v == 0)) {
            return 0;
        }
        final int valueCount = values.size();
        final List<Long> nextValues = new ArrayList<>(valueCount -1);
        for (int i = 0; i < valueCount -1; i++) {
            nextValues.add(values.get(i+1) - values.get(i));
        }
        long next = findNextValue(nextValues, last);
        return last ?
                values.get(valueCount -1) + next:
                values.get(0) - next;
    }

}