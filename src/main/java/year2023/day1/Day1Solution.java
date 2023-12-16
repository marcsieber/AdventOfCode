package year2023.day1;

import common.Utils;

import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/1">Day 1</a>
 */
public class Day1Solution {

//  private static final String inputFile = "2023/provided/day1-example.txt";
    private static final String inputFile = "2023/provided/day1.txt";

    private static final List<String> numbers = List.of(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    );

    public static void main(String[] args) {

        List<String> input = Utils.readInputFromResources(inputFile);

        long total = input.stream()
                .map(Day1Solution::preprocess)
                .mapToLong(Day1Solution::readCalibrationValue1)
                .sum();

        System.out.println(total);
    }

    /**
     * Preprocesses the string <code>s</code> by replacing the first letter of
     * the number string with the corresponding number.
     */
    private static String preprocess(String s) {
        int[] results1 = new int[9];
        for (int i = 0; i < numbers.size(); i++) {
            int idx = s.indexOf(numbers.get(i));
            results1[i] = idx != -1 ? idx : Integer.MAX_VALUE;
        }
        int first = Utils.smallestIndex(results1);

        int[] results2 = new int[9];
        for (int i = 0; i < numbers.size(); i++) {
            int idx = s.lastIndexOf(numbers.get(i));
            results2[i] = idx != -1 ? idx : Integer.MIN_VALUE;
        }
        int second = Utils.biggestIndex(results2);

        if (results1[first] != Integer.MAX_VALUE) {
            int idx = results1[first];
            s = s.substring(0, idx) + (first+1) + s.substring(idx+1);
        }
        if (results2[second] != Integer.MIN_VALUE) {
            int idx = results2[second];
            s = s.substring(0, idx) + (second+1) + s.substring(idx+1);
        }
        return s;
    }


    private static long readCalibrationValue1(String input) {
        int zero = 48; // ascii 0

        int first = 0;
        for (int i = 0; i < input.length(); i++) {
            int value = input.charAt(i) - zero;
            if (0 <= value && value <= 9) {
                first = value;
                break;
            }
        }

        int second = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            int value = input.charAt(i) - zero;
            if (0 <= value && value <= 9) {
                second = value;
                break;
            }
        }
        return first * 10 + second;
    }





}
