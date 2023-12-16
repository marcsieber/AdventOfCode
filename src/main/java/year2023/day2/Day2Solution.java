package year2023.day2;

import common.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @see <a href="https://adventofcode.com/2023/day/2">Day 2</a>
 */
public class Day2Solution {

//  private static final String inputFile = "2023/provided/day2-example.txt";
    private static final String inputFile = "2023/provided/day2.txt";

    private static final GameSet target = new GameSet(12, 13, 14);

    public static void main(String[] args) {

        List<String> input = Utils.readInputFromResources(inputFile);

        int sum = input.stream()
                .map(Game::new)
                .filter(game -> game.isPossible(target))
                .mapToInt(Game::getGameId)
                .sum();

        System.out.println("Sume of GameIds: " +sum);

        int sumPower = input.stream()
                .map(Game::new)
                .map(Game::minimumNeededCubes)
                .mapToInt(GameSet::power)
                .sum();

        System.out.println("Sum of the power of all sets: " + sumPower);
    }


    private static class Game {
        private static final Pattern gameIdRegex = Pattern.compile("Game (\\d*)");

        final int gameId;
        final List<GameSet> sets;

        private Game(String line) {
            gameId = regexFindNumber(gameIdRegex, line);

            sets = Arrays.stream(line.split(";"))
                    .map(GameSet::new)
                    .collect(Collectors.toList());
        }

        public int getGameId() { return gameId; }

        private boolean isPossible(GameSet gameSet) {
            return sets.stream().allMatch(set -> set.isPossibleOutcome(gameSet));
        }

        private GameSet minimumNeededCubes() {

            int maxRed   = sets.stream().mapToInt(GameSet::getRed).max().orElse(0);
            int maxGreen = sets.stream().mapToInt(GameSet::getGreen).max().orElse(0);
            int maxBlue  = sets.stream().mapToInt(GameSet::getBlue).max().orElse(0);

            return new GameSet(maxRed, maxGreen, maxBlue);
        }
    }


    private static class GameSet {

        private static final Pattern redRegex   = Pattern.compile("(\\d*) red");
        private static final Pattern greenRegex = Pattern.compile("(\\d*) green");
        private static final Pattern blueRegex  = Pattern.compile("(\\d*) blue");

        private final int red;
        private final int green;
        private final int blue;

        private GameSet(String line) {
            this(
                regexFindNumber(redRegex, line),
                regexFindNumber(greenRegex, line),
                regexFindNumber(blueRegex, line)
            );
        }

        private GameSet(int red,  int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public int getRed() {return red;}
        public int getGreen() { return green; }
        public int getBlue() { return blue; }

        private int power() {
            return red * green * blue;
        }

        private boolean isPossibleOutcome(GameSet other) {
            return this.red <= other.red && this.green <= other.green && this.blue <= other.blue;
        }
    }


    private static int regexFindNumber(Pattern patter, String s) {
        Matcher m = patter.matcher(s);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

}
