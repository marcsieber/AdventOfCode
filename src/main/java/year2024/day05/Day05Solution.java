package year2024.day05;

import common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="http://adventofcode.com/2024/day/5">Day 05</a>
 */
public class Day05Solution {

    //private static final String inputFile = "2024/provided/day05-example.txt";
    private static final String inputFile = "2024/provided/day05.txt";

    public static void main(String[] args) {

        final List<String>           input         = Utils.readInputFromResources(inputFile);
        final List<PageOrderingRule> rules         = new ArrayList<>();
        final List<List<Integer>>    safetyManuals = new ArrayList<>();

        populateRulesAndManuals(input, rules, safetyManuals);

        final int validReportSum = sumOfCorrectOrderedReports(rules, safetyManuals);
        System.out.println(validReportSum);

        final int newlyCorrectlyOrderedSum = sumOfNewlyCorrectOrderedReports(rules, safetyManuals);
        System.out.println(newlyCorrectlyOrderedSum);

    }

    private static int sumOfCorrectOrderedReports(List<PageOrderingRule> rules, List<List<Integer>> safetyManuals) {
        return safetyManuals.stream()
                .filter(manual -> isSafetyManualCorrectlyOrdered(rules, manual))
                .mapToInt(Day05Solution::middlePageNumber)
                .sum();
    }

    private static int sumOfNewlyCorrectOrderedReports(List<PageOrderingRule> rules, List<List<Integer>> safetyManuals) {
        return safetyManuals.stream()
                .filter(manual -> ! isSafetyManualCorrectlyOrdered(rules, manual))
                .mapToInt(manual -> {
                    orderSafetyManuelPages(rules, manual);
                    return middlePageNumber(manual);
                }).sum();
    }


    /**
     * @return the middle number if correctly ordered otherwise 0.
     */
    private static boolean isSafetyManualCorrectlyOrdered(List<PageOrderingRule> rules, List<Integer> manual) {
        for (PageOrderingRule rule : rules) {
            final int fst = manual.indexOf(rule.before);
            final int snd = manual.indexOf(rule.after);
            if (fst != - 1 && snd != - 1 && fst > snd) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps pages which do not fulfill the guidelines until the report becomes valid :-)
     */
    private static void orderSafetyManuelPages(List<PageOrderingRule> rules, List<Integer> manual) {
        while (! isSafetyManualCorrectlyOrdered(rules, manual)) {//
            for (PageOrderingRule rule : rules) {
                final int fst = manual.indexOf(rule.before);
                final int snd = manual.indexOf(rule.after);
                if (fst != - 1 && snd != - 1 && fst > snd) {
                    manual.set(fst, rule.after);
                    manual.set(snd, rule.before);
                }
            }
        }
    }

    // Utilities
    // ------------------------------------------------------------------------

    private static void populateRulesAndManuals(List<String> input, List<PageOrderingRule> rules, List<List<Integer>> manuals) {
        input.stream()
                .takeWhile(s -> ! s.isEmpty())
                .map(s -> {
                    final String[] split = s.split("\\|");
                    return new PageOrderingRule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                }).forEach(rules::add);

        input.stream()
                .skip(rules.size() + 1) // blank line
                .map(s -> s.split(","))
                .map(pages -> Arrays.stream(pages)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .forEach(manuals::add);
    }

    private static int middlePageNumber(List<Integer> list) {
        return list.get(list.size() / 2);
    }

    private record PageOrderingRule(int before, int after) { }

}
