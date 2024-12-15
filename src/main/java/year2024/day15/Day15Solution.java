package year2024.day15;

import common.Utils;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="http://adventofcode.com/2024/day/15">Day 15</a>
 */
public class Day15Solution {

    private static final String inputFile = "2024/provided/day15-example.txt";
    //private static final String inputFile = "2024/provided/day15.txt";

    public static final char WALL  = '#';
    public static final char BOX   = 'O';
    public static final char EMPTY = '.';
    public static final char ROBOT = '@';
    public static final char BOX_LEFT = '[';
    public static final char BOX_RIGHT = ']';

    public static void main(String[] args) {

        final List<String> input = Utils.readInputFromResources(inputFile);

        final char[][] smallWarehouse = readWarehouse(input);
        final char[]   robotMovements = readRobotMovements(input);

        final Point robotStartingPos = Utils.uniqueCharsInArray2d(smallWarehouse).get(ROBOT).getFirst();
        final int   gpsSum           = sumGPSCoordinatesAfterRobotMoving(robotStartingPos, robotMovements, smallWarehouse);
        System.out.println(gpsSum);
    }

    private static int sumGPSCoordinatesAfterRobotMoving(Point robotPosition, char[] movements, char[][] warehouse) {
        warehouse = Utils.arrayCopy(warehouse);
        simulateRobotMovements(robotPosition, movements, warehouse);
        Utils.print2dArray(warehouse);
        return Utils.uniqueCharsInArray2d(warehouse).get(BOX).stream()
                .mapToInt(Day15Solution::gpsCoordinate)
                .sum();
    }

    private static void simulateRobotMovements(Point robotPosition, char[] movements, char[][] warehouse) {
        for (char movement : movements) {
            final Direction direction = Direction.of(movement);
            robotPosition = move(robotPosition, direction, warehouse);
        }
    }

    /**
     * Moves the robot one step into the given direction
     */
    private static Point move(Point robotPosition, Direction direction, char[][] warehouse) {
        Point nextPosition = robotPosition;
        char  nextObject;
        //noinspection StatementWithEmptyBody
        while ((nextObject = charAt(
                nextPosition = Utils.addVector(nextPosition, direction.getDirectionVec()),
                warehouse
        )) == BOX) { } // loop until a wall or empty space is found
        if (nextObject == WALL) { // the next none box position is a wall we cannot move
            return robotPosition;
        }

        final Point nextRobotPosition = Utils.addVector(robotPosition, direction.getDirectionVec());
        warehouse[nextRobotPosition.y][nextRobotPosition.x] = ROBOT;
        warehouse[robotPosition.y][robotPosition.x]         = EMPTY;

        if (! nextPosition.equals(nextRobotPosition)) { // test if we haven't moved no an empty field.
            warehouse[nextPosition.y][nextPosition.x] = BOX;
        }
        return nextRobotPosition;
    }

    private static int gpsCoordinate(Point position) {
        return position.y * 100 + position.x;
    }

    // Utilities
    // ------------------------------------------------------------------------
    private static char[][] readWarehouse(List<String> input) {
        return Utils.as2dArray(input.stream()
                .takeWhile(s -> s.startsWith("#"))
                .toList());
    }

    private static char[] readRobotMovements(List<String> input) {
        return input.stream()
                .dropWhile(s -> ! s.isEmpty())
                .filter(s -> ! s.isEmpty())
                .collect(Collectors.joining()).toCharArray();
    }

    private static char charAt(Point p, char[][] array2d) {
        return array2d[p.y][p.x];
    }

    private enum Direction {
        //@formatter:off
        DOWN('v',   new Point( 0,  1)),
        LEFT('<',   new Point(-1,  0)),
        RIGHT('>',  new Point( 1,  0)),
        UP('^',     new Point( 0, -1));
        //@formatter:on

        private final char  direction;
        private final Point directionVec;

        Direction(char direction, Point directionVec) {
            this.direction    = direction;
            this.directionVec = directionVec;
        }

        public Point getDirectionVec() { return directionVec; }

        private static Direction of(char c) {
            for (Direction dir : Direction.values()) {
                if (dir.direction == c) {
                    return dir;
                }
            }
            throw new IllegalArgumentException("Invalid direction: " + c);
        }
    }

}
