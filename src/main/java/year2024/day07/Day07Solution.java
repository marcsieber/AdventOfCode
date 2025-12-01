package year2024.day07;

import common.Utils;

import java.util.*;
import java.util.function.BiFunction;

/**
 * It's backtracking time baby 🥳
 *
 * @see <a href="http://adventofcode.com/2024/day/7">Day 07</a>
 */
public class Day07Solution {

    //private static final String inputFile = "2024/provided/day07-example.txt";
    private static final String inputFile = "2024/provided/day07.txt";

    public static void main(String[] args) {
        final List<String>   input     = Utils.readInputFromResources(inputFile);
        final List<Equation> equations = populateEquations(input);

        final long countValid = sumValidEquationResults(equations, Operator.ADD, Operator.MULTIPLY);
        System.out.println(countValid);

        final long countValidWithConcat = sumValidEquationResults(equations, Operator.values());
        System.out.println(countValidWithConcat);
    }

    private static long sumValidEquationResults(List<Equation> equations, Operator... operators) {
        return equations.stream()
                .filter(e -> isValid(e, operators))
                .mapToLong(Equation::result)
                .sum();
    }

    private static boolean isValid(Equation equation, Operator... operators) {
        final List<Long> numbers = equation.numbers();
        return isValid(equation.result, numbers.getFirst(), subList(numbers), operators);
    }

    private static boolean isValid(long expected, long intermediateResult, List<Long> remainingNumbers, Operator... operators) {
        if (remainingNumbers.isEmpty()) {
            return intermediateResult == expected;
        }
        final long localNumber = remainingNumbers.getFirst();
        for (Operator operator : operators) {
            final long localResult = operator.apply(intermediateResult, localNumber);
            if (isValid(expected, localResult, subList(remainingNumbers), operators)) {
                return true;
            }
        }
        return false;
    }

    // Utilities
    // ------------------------------------------------------------------------

    private static List<Equation> populateEquations(List<String> input) {
        return input.stream()
                .map(s -> {
                    final String[] split = s.split(":");
                    final List<Long> numbers = Arrays.stream(split[1].split(" "))
                            .filter(sub -> ! sub.isEmpty())
                            .map(Long::parseLong)
                            .toList();
                    return new Equation(Long.parseLong(split[0]), numbers);
                }).toList();
    }

    private static <T> List<T> subList(List<T> list) {
        return list.subList(1, list.size());
    }

    private record Equation(long result, List<Long> numbers) { }

    private enum Operator {
        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b),
        CONCAT((a, b) -> {
            final long num = b != 0 ? b : 1; // any 1 letter number would be possible
            return a * (long) (Math.pow(10, ((long) Math.log10(num)) + 1)) + b;
        });

        private final BiFunction<Long, Long, Long> apply;

        Operator(BiFunction<Long, Long, Long> apply) {
            this.apply = apply;
        }

        private Long apply(Long a, Long b) { return apply.apply(a, b); }
    }

}