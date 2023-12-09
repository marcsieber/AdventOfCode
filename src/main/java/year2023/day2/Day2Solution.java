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

//  private static final String inputFile = "day2-dummy.txt";
    private static final String inputFile = "day2.txt";

    private static final GameSet target = new GameSet(12, 13, 14);

    public static void main(String[] args) {

        List<String> input = Utils.readInputFromResources(inputFile);
        int sum = input.stream()
                .map(Game::new)
                .filter(game -> game.isPossible(target))
                .mapToInt(Game::getGameId)
                .sum();

        System.out.println(sum);
    }

    private static class Game {
        private static final Pattern gameIdRegex = Pattern.compile("Game (\\d*)");

        int gameId;
        List<GameSet> sets;

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
    }

    private static class GameSet {

        private static final Pattern redRegex   = Pattern.compile("(\\d*) red");
        private static final Pattern greenRegex = Pattern.compile("(\\d*) green");
        private static final Pattern blueRegex  = Pattern.compile("(\\d*) blue");

        int red;
        int green;
        int blue;

        private GameSet(String line) {
            this(
                regexFindNumber(redRegex, line),
                regexFindNumber(greenRegex, line),
                regexFindNumber(blueRegex, line)
            );
        }

        private GameSet(int red,  int green, int blue) {
            this.red = red;
            this.blue = blue;
            this.green = green;
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
