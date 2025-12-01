package year2024.day03;

import common.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Hints for Riddle 2:
 * Instead of enabling / disabling the multiplication while parsing, we can extract
 * the active ranges. With that in place we can then simply use {@link #sumOfMulCommandLine(String)}
 * to find the sum over all active ranges.
 *
 * @see <a href="http://adventofcode.com/2024/day/3">Day 03</a>
 */
public class Day03Solution {

    @SuppressWarnings("CommentedOutCode")
    //private static final String inputFile = "2024/provided/day03-example.txt";
    //private static final String inputFile = "2024/provided/day03-example-2.txt"; // riddle 2 only
    private static final String inputFile = "2024/provided/day03.txt";

    private static final Pattern MUL_PATTERN       = Pattern.compile("mul\\((?<fst>\\d+),(?<snd>\\d+)\\)");
    private static final String  ACTIVATION_CODE   = "do()";
    private static final String  DEACTIVATION_CODE = "don't()";

    public static void main(String[] args) {

        // The input today can be read as a single continuous string.
        // For the second riddle this is even necessary
        final String input = String.join("", Utils.readInputFromResources(inputFile));

        final long sumOfMulCommand = sumOfMulCommandLine(input);
        System.out.println(sumOfMulCommand);

        final long sumWithCommandSwitching = sumWithCommandSwitching(input);
        System.out.println(sumWithCommandSwitching);
    }

    private static long sumOfMulCommandLine(String input) {
        final Matcher matcher = MUL_PATTERN.matcher(input);

        long sum = 0;
        while (matcher.find()) {
            final int m1 = Integer.parseInt(matcher.group("fst"));
            final int m2 = Integer.parseInt(matcher.group("snd"));
            sum += (long) m1 * m2;
        }
        return sum;
    }

    private static long sumWithCommandSwitching(String input) {
        return activatedSpaces(input).stream()
                .map(space -> input.substring(space.start(), space.end()))
                .mapToLong(Day03Solution::sumOfMulCommandLine)
                .sum();
    }

    private static List<ActiveSpace> activatedSpaces(String input) {
        final List<ActiveSpace> activeSpaces = new ArrayList<>();

        int start = 0; // we start always in an active state
        int end   = input.indexOf(DEACTIVATION_CODE, start);
        end = end == - 1 ? input.length() - 1 : end + DEACTIVATION_CODE.length(); // does not matter where we add the word length

        activeSpaces.add(new ActiveSpace(start, end));

        int current = end;
        while (start != - 1 && end != - 1) {
            start = input.indexOf(ACTIVATION_CODE, current);
            end   = input.indexOf(DEACTIVATION_CODE, current);
            end   = end == - 1 ? input.length() - 1 : end + DEACTIVATION_CODE.length();  // does not matter where we add the word length

            if (start < end && start != - 1 || start != - 1 && end == - 1) { // found another space
                activeSpaces.add(new ActiveSpace(start, end));
            }
            current = end;
        }
        return activeSpaces;
    }

    /**
     * Denotes a range which is activated by the {@link #ACTIVATION_CODE} and
     * deactivated by {@link #DEACTIVATION_CODE}. Both key codes are fully
     * included in the range.
     */
    private record ActiveSpace(int start, int end) { }
}
