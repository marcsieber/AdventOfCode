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

        final long sumMaxOutput2Batteries = input.stream()
                .map(BatteryBank::new)
                .mapToLong(bank -> bank.getMaxOutput(2))
                .sum();

        System.out.println(sumMaxOutput2Batteries);

        final long sumMaxOutput12Batteries = input.stream()
                .map(BatteryBank::new)
                .mapToLong(bank -> bank.getMaxOutput(12))
                .sum();

        System.out.println(sumMaxOutput12Batteries);
    }
    
    // Utilities
    // ------------------------------------------------------------------------

    private record BatteryBank(String bank) {

        long getMaxOutput(int numBatteries) {
            final int [] indexes = new int[numBatteries];
            for (int i = 0; i < numBatteries; i++) {
                final int startIdx = i == 0 ? 0 : indexes[i - 1] + 1;
                indexes[i] = maxIdx(bank, startIdx, bank.length() - numBatteries + i +1);
            }

            long joltage = 0;
            for (int i = 0; i < numBatteries; i++) {
                final int valueAtIdx = bank.charAt(indexes[i]) - '0';
                joltage += valueAtIdx * (long) Math.pow(10, numBatteries - i - 1);
            }
            return joltage;
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
