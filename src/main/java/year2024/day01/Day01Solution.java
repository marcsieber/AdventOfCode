package year2024.day01;

import common.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @see <a href="http://adventofcode.com/2024/day/1">Day 01</a>
 */
public class Day01Solution {

    //private static final String inputFile = "2024/provided/day01-example.txt";
    private static final String inputFile = "2024/provided/day01.txt";

    public static void main(String[] args) {

        final List<String> input = Utils.readInputFromResources(inputFile);
        final List<Integer> list1 = new ArrayList<>();
        final List<Integer> list2 = new ArrayList<>();

        prepareData(input, list1, list2);

        final long diff = listDifference(list1, list2);
        System.out.println(diff);

        final long similarityScore = similarityScore(list1, list2);
        System.out.println(similarityScore);
    }

    /**
     * Divides the input into two lists and sorts both of them ascending.
     */
    private static void prepareData(List<String> input, List<Integer> list1, List<Integer> list2) {
        input.forEach(s -> {
            final String[] split = s.split(" {3}");
            list1.add(Integer.parseInt(split[0]));
            list2.add(Integer.parseInt(split[1]));
        });

        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);
    }

    private static long listDifference(List<Integer> list1, List<Integer> list2) {
        return IntStream.range(0, list1.size())
                .map(idx -> Math.abs(list1.get(idx) - list2.get(idx)))
                .sum();
    }

    private static long similarityScore(List<Integer> list1, List<Integer> list2) {
        final Map<Integer, Long> numberByOccurrence = list2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return list1.stream()
                .reduce(0, (sum, i) -> (int) (i * numberByOccurrence.getOrDefault(i, 0L)) + sum);
    }

}
