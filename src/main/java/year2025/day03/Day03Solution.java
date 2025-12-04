package year2025.day03;

import common.Utils;

import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/3">Day 03</a>
 */
public class Day03Solution {

//    private static final String inputFile = "2025/provided/day03-example.txt";
    private static final String inputFile = "2025/provided/day03.txt";

    static void main() {

        final List<String> input = Utils.readInputFromResources(inputFile);

        final int sumMaxOutput = input.stream()
                .map(BatteryBank::new)
                .mapToInt(BatteryBank::getMaxOutput)
                .sum();

        System.out.println( sumMaxOutput);
    }
    
    // Utilities
    // ------------------------------------------------------------------------

    private record BatteryBank(String bank) {

        int getMaxOutput() {
            final int idx1 = maxIdx(bank, 0, bank.length() -1);
            final int idx2 = maxIdx(bank, idx1 + 1, bank.length());

            return (bank.charAt(idx1) - '0') * 10 +
                   (bank.charAt(idx2) - '0');
        }

        private static int maxIdx(String s, int start, int end) {
            int max = '0';
            int maxIdx = 0;
            for (int i = start; i < end; i++) {
                final char currentChar = s.charAt(i);
                if (max < currentChar) {
                    max = currentChar;
                    maxIdx = i;
                }
            }
            return maxIdx;
        }

    }
}
