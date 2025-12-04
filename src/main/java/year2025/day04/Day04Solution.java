package year2025.day04;

import common.Utils;

import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/4">Day 04</a>
 */
public class Day04Solution {

//    private static final String inputFile = "2025/provided/day04-example.txt";
    private static final String inputFile = "2025/provided/day04.txt";

    public static final char PAPER_ROLL               = '@';
    public static final int  MAX_ADJACENT_PAPER_ROLLS = 3;

    static void main() {

        final List<String> input = Utils.readInputFromResources(inputFile);

        final char[][] map = Utils.as2dArray(input);

        System.out.println(countAccessibleRolls(map));

    }

    private static int countAccessibleRolls(char[][] map) {
        final int width  = map[0].length;
        final int height = map.length;

        int accessibleRolls = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == PAPER_ROLL) {
                    final int surroundingRolls = countPaperRollsSurrounding(map, height, width, x, y);
                    if (surroundingRolls <= MAX_ADJACENT_PAPER_ROLLS) {
                        accessibleRolls++;
                    }
                }
            }
        }
        return accessibleRolls;
    }

    private static int countPaperRollsSurrounding(char[][] map, int height, int width, int posX, int posY) {
        int amountSurroundingPaperRolls = 0;
        for (int y = - 1; y <= 1; y++) {
            for (int x = - 1; x <= 1; x++) {
                if (x == 0 && y == 0) { continue; }
                final int newPosX = posX + x;
                final int newPosY = posY + y;

                final boolean inBounds = 0 <= newPosX && newPosX < width && 0 <= newPosY && newPosY < height;
                if (inBounds) {
                    if (map[newPosY][newPosX] == PAPER_ROLL) {
                        amountSurroundingPaperRolls++;
                    }
                }
            }
        }

        return amountSurroundingPaperRolls;
    }

    // Utilities
    // ------------------------------------------------------------------------

}
