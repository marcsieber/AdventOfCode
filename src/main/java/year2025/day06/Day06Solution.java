package year2025.day06;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/6">Day 06</a>
 */
public class Day06Solution {

//    private static final String inputFile = "2025/provided/day06-example.txt";
    private static final String inputFile = "2025/provided/day06.txt";
    public static final  char   EMPTY     = ' ';

    public static void main(String... args) {

        final List<String> input = Utils.readInputFromResources(inputFile);

        final char[][] numberField = readValues(input);
        final List<NumberPositionInfo> infos = readNumberPositions(input);
        final List<MathOperation> mathOperations = readOperator(input);

        final long result1 = solve(numberField, mathOperations, infos, new HorizontalNumberReader());
        System.out.println(result1);
    }


    private static long solve(char[][] numberField, List<MathOperation> operations, List<NumberPositionInfo> infos, NumberReader reader) {
        long total = 0;
        for (int i = 0; i < operations.size(); i++) {
            final MathOperation reducer = operations.get(i);
            final long resultPerColum = reader.readValues(numberField, i, infos).stream()
                    .mapToLong(l -> l)
                    .reduce(reducer::calculate)
                    .orElse(0);
            total += resultPerColum;
        }

        return total;
    }

    protected record NumberPositionInfo(int offset, int length) { }

    protected interface NumberReader {
        /**
         * Reads a number in the given <code>numberField</code>
         *
         * @param numberField 2D Array containing all the values
         * @param columnIdx   Index of the Number List
         * @param infos       Meta information about the column width
         * @return all values contained in the specified column
         */
        List<Long> readValues(char[][] numberField, int columnIdx, List<NumberPositionInfo> infos);
    }

    private static class HorizontalNumberReader implements NumberReader {

        @Override
        public List<Long> readValues(char[][] numberField, int columnIdx, List<NumberPositionInfo> numberPositions) {
            final List<Long> values = new ArrayList<>();
            for (final char[] yChars : numberField) {
                final StringBuilder builder = new StringBuilder();
                final NumberPositionInfo info = numberPositions.get(columnIdx);
                for (int i = info.offset(); i < info.offset() + info.length(); i++) {
                    final char currentChar = yChars[i];
                    if (currentChar != EMPTY) {
                        builder.append(currentChar);
                    }
                }
                values.add(Long.valueOf(builder.toString()));
            }
            return values;
        }
    }

    // Utilities
    // ------------------------------------------------------------------------

    private static char[][] readValues(List<String> input) {
        final char[][] numbersIncludingOp = Utils.as2dArray(input);
        final char[][] numbersOnly = new char[numbersIncludingOp.length - 1][];

        System.arraycopy(numbersIncludingOp, 0, numbersOnly, 0, numbersOnly.length);
        return numbersOnly;
    }

    @FunctionalInterface
    interface MathOperation { long calculate(long a, long b); }

    private static List<MathOperation> readOperator(List<String> input) {
        final List<MathOperation> functions = new ArrayList<>();
        final String [] operators = input.getLast().replace(" ", "").split("");

        for (String o : operators) {
            final MathOperation func = switch (o) {
                case"+" -> Long::sum;
                case"*" -> (a, b) -> a * b;
                default -> throw new IllegalStateException("Unknown operator: " + o);
            };
            functions.add(func);
        }
        return functions;
    }

    private static List<NumberPositionInfo> readNumberPositions(List<String> input) {
        final List<NumberPositionInfo> infos = new ArrayList<>();
        final String operators = input.getLast() + " x"; // add a fake space at the end

        int offset = 0;
        for (int i = 1; i < operators.length(); i++) {
            if (operators.charAt(i) != EMPTY || i == operators.length() - 1) {
                infos.add(new NumberPositionInfo(offset, i - offset - 1)); // length -1 because there is a space between numbers
                offset = i;
            }
        }
        return infos;
    }

}
