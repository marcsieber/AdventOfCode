package year2023.day6;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/6">Day 6</a>
 */
public class Day6Solution {

//  private static final String inputFile = "2023/provided/day6-example.txt";
    private static final String inputFile = "2023/provided/day6.txt";

    public static void main(String[] args) {

        // general
        List<String> input      = Utils.readInputFromResources(inputFile);
        List<Integer> times     = Utils.readAsListOfInt(input.get(0).split(":")[1]);
        List<Integer> distances = Utils.readAsListOfInt(input.get(1).split(":")[1]);
        List<Race>    races     = createRaces(times, distances);

        // first
        long product = races.stream()
                .map(Race::countWinningOptions)
                .reduce(1L, (a, b) -> a * b);

        System.out.println(product);

        // second
        long time     = concatInteger(times);
        long distance = concatInteger(distances);
        long count = new Race(time, distance).countWinningOptions();

        System.out.println(count);

    }

    private static List<Race> createRaces(List<Integer> times, List<Integer> distances) {
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
        return races;
    }

    private static long concatInteger(List<Integer> integers) {
        long concatenated = integers.get(0);
        for (int i = 1; i < integers.size(); i++) {
            int digitCount = (int) Math.ceil(Math.log10(integers.get(i)));
            concatenated = concatenated * (long) Math.pow(10, digitCount) + integers.get(i);
        }
        return concatenated;
    }

    private record Race(long time, long distance) {

        private long countWinningOptions() {
            int count = 0;
            for (int t = 1; t <= time; t++) {
                long distance = t * (time - t); // speed = 1 * t
                if (this.distance < distance) {
                    count++;
                }
            }
            return count;
        }
    }

}