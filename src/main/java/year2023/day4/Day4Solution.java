package year2023.day4;

import common.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://adventofcode.com/2023/day/4">Day 4</a>
 */
public class Day4Solution {

//  private static final String inputFile = "day4-dummy.txt";
    private static final String inputFile = "day4.txt";

    public static void main(String[] args) {

        List<String> input = Utils.readInputFromResources(inputFile);

        int sum = input.stream()
                .map(Card::new)
                .mapToInt(Card::calculateWinningPoints)
                .sum();

        System.out.println(sum);
    }

    private static class Card {

        List<Integer> winningNumbers;
        List<Integer> actualNumbers;

        private Card(String input) {
            String[] numbers = input.split(":")[1].split("\\|");

            winningNumbers = Arrays.stream(numbers[0].split(" "))
                    .filter(s -> ! s.isBlank())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            actualNumbers = Arrays.stream(numbers[1].split(" "))
                    .filter(s -> ! s.isBlank() )
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        private int calculateWinningPoints() {
            long countWinningNumbers = actualNumbers.stream()
                    .filter(winningNumbers::contains)
                    .count();

            if (countWinningNumbers == 0) return 0;
            return (int) Math.pow(2, countWinningNumbers-1);
        }

    }

}