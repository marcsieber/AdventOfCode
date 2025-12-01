package year2024.day06;

import common.Utils;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @see <a href="http://adventofcode.com/2024/day/6">Day 06</a>
 */
public class Day06Solution {

    //private static final String inputFile = "2024/provided/day06-example.txt";
    private static final String inputFile = "2024/provided/day06.txt";

    public static final char GUARD_STARTING_POSITION = '^';
    public static final char OBSTACLE                = '#';
    public static final char STEP                    = '"';

    public static void main(String[] args) {

        final List<String> input   = Utils.readInputFromResources(inputFile);
        final char[][]     array2d = Utils.as2dArray(input);

        final Point startingPosition = startingPosition(array2d);

        final long countStepsUntilFieldLeft = countTilesVisitedUntilAreaLeft(array2d, startingPosition);
        System.out.println(countStepsUntilFieldLeft);

        final long countOptionsToCreateAnEndlessLoop = countOptionsToCreateAnEndlessLoop(array2d, startingPosition);
        System.out.println(countOptionsToCreateAnEndlessLoop);
    }

    private static int countTilesVisitedUntilAreaLeft(char[][] array2d, Point startingPosition) {
        array2d = Utils.arrayCopy(array2d); // clone to not modify the original input
        simulateWalk(array2d, startingPosition, FacingDirection.NORTH); // guard always leaves
        return countUniqueSteps(array2d);
    }

    private static int countOptionsToCreateAnEndlessLoop(char[][] array2d, Point startingPosition) {
        int countTrapped = 0;
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {
                if (array2d[y][x] == OBSTACLE || array2d[y][x] == GUARD_STARTING_POSITION) { continue; }

                final char[][] original = Utils.arrayCopy(array2d); // clone to not modify the original input
                original[y][x] = OBSTACLE;
                final boolean trapped = ! simulateWalk(original, startingPosition, FacingDirection.NORTH);
                if (trapped) {
                    countTrapped++;
                }
            }
        }
        return countTrapped;
    }

    /**
     * @return <code>true</code> if the guard has left the area. <code>false</code> if the guard is trapped in a loop.
     */
    @SuppressWarnings("SameParameterValue")
    private static boolean simulateWalk(char[][] array2d, Point currentPosition, FacingDirection direction) {
        final Set<DirectionLog> logs = new HashSet<>();

        FacingDirection nextDirection = direction;
        Point           nextPosition  = currentPosition;
        while (Utils.isWithinBounds(array2d, nextPosition)) {
            if (! logs.add(new DirectionLog(nextDirection, nextPosition))) {
                return false;
            }

            array2d[nextPosition.y][nextPosition.x] = STEP;

            nextDirection = directionOfNextStep(array2d, nextPosition, nextDirection);
            nextPosition  = nextDirection.tileInFront(nextPosition);
        }
        return true;
    }

    /**
     * Returns the new {@link FacingDirection} to the next free tile.
     * <code>null</code> if every direction is blocked.
     */
    private static FacingDirection directionOfNextStep(char[][] array2d, Point currentPosition, FacingDirection direction) {
        FacingDirection newDirection = direction;
        for (int i = 0; i < 4; i++) {
            final Point frontPos = newDirection.tileInFront(currentPosition);
            if (! Utils.isWithinBounds(array2d, frontPos) || array2d[frontPos.y][frontPos.x] != OBSTACLE) {
                return newDirection;

            } else {
                newDirection = newDirection.faceRight();
            }
        }
        throw new IllegalStateException("Your stuck :-/");
    }

    // Utilities
    // ------------------------------------------------------------------------

    private static Point startingPosition(char[][] array2d) {
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {
                if (array2d[y][x] == GUARD_STARTING_POSITION) {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalArgumentException("No starting position found");
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private static int countUniqueSteps(char[][] array2d) {
        int count = 0;
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {
                if (array2d[y][x] == STEP) {
                    count++;
                }
            }
        }
        return count;
    }

    private record DirectionLog(FacingDirection direction, Point position) { }

    private enum FacingDirection {
        NORTH(0, - 1, 1), // do not move
        EAST(1, 0, 2), // do not move
        SOUTH(0, 1, 3), // do not move
        WEST(- 1, 0, 0); // do not move

        final int directionIdx;
        final int xInFront, yInFront;

        FacingDirection(int xInFront, int yInFront, int directionIdx) {
            this.xInFront     = xInFront;
            this.yInFront     = yInFront;
            this.directionIdx = directionIdx;
        }

        private FacingDirection faceRight() {
            return FacingDirection.values()[this.directionIdx];
        }

        private Point tileInFront(Point point) {
            return new Point(point.x + xInFront, point.y + yInFront);
        }
    }

}