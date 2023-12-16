package year2023.day5;

import common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/5">Day 5</a>
 */
public class Day5Solution {

//  private static final String inputFile = "2023/provided/day5-example.txt";
    private static final String inputFile = "2023/provided/day5.txt";

    private static final boolean debug = false;

    public static void main(String[] args) {

        // general
        List<String> input = Utils.readInputFromResources(inputFile);
        List<Long> seeds = Arrays.stream(input.get(0).split(":")[1].split(" "))
                .filter(s -> ! s.isBlank())
                .map(Long::parseLong)
                .toList();

        PermutationSet set = createPermutationSet(input.subList(1, input.size()));

        // first
        long nearestLocation = seeds.stream()
                .map(set::resolve)
                .min(Long::compareTo)
                .orElse(0L);

        System.out.println(nearestLocation);

        // second
        long min = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i);
            long size  = seeds.get(i+1);
            for (int j = 0; j < size; j++) {
                long resolved = set.resolve(start + j);
                if (resolved < min) {
                    min = resolved;
                }
            }
        }
        System.out.println(min);
    }

    private static PermutationSet createPermutationSet(List<String> lines) {
        PermutationSet set = new PermutationSet();

        for (String line : lines) {
            if ( ! (line.isBlank() || line.contains("map"))) {
                set.add(new Permutation(line));
            } else if (line.contains("map")) {
                set.newMapping();
            }
        }
        return set;
    }

    private static class PermutationSet {

        private final List<List<Permutation>> permutations = new ArrayList<>();

        private void newMapping() {
            permutations.add(new ArrayList<>());
        }

        private void add(Permutation permutation) {
            permutations.get(permutations.size() -1).add(permutation);
        }

        private long resolve(long value) {
            long result = value;
            if (debug) { System.out.print(value); }

            for (List<Permutation> perms : permutations) {
                for (Permutation permutation : perms) {
                    long permuted = permutation.resolve(result);
                    if (result != permuted) {
                        if (debug) { System.out.print(" -> " + permuted ); }
                        result = permuted;
                        break; // continue to the next set
                    }
                }
            }
            if (debug) { System.out.println(); }
            return result;
        }
    }


    private static class Permutation {

        final long source;
        final long start;
        final long end;
        final long delta;
        final long size;

        private Permutation (String line){
            final String [] values = line.split(" ");

            final long destination = Long.parseLong(values[0]);
            source      = Long.parseLong(values[1]);
            size        = Long.parseLong(values[2]);
            delta       = destination - source;

            start = source;
            end   = source + size;
        }

        private long resolve(long value) {
            if (start <= value && value < end ) {
                value = value + delta;
            }
            return value;
        }
    }

}