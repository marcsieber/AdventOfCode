package year2024.day14;

import common.Utils;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @see <a href="http://adventofcode.com/2024/day/14">Day 14</a>
 */
public class Day14Solution {

    @SuppressWarnings("CommentedOutCode")
    //private static final String inputFile = "2024/provided/day14-example.txt";
    //private static final int    WIDTH     = 11;
    //private static final int    HEIGHT    = 7;

    private static final String inputFile = "2024/provided/day14.txt";
    private static final int    WIDTH     = 101;
    private static final int    HEIGHT    = 103;


    public static void main(String[] args) {

        final List<String> input         = Utils.readInputFromResources(inputFile);
        final List<Robot>  initialRobots = readRobotConfiguration(input);

        final int safetyScore = safetyScore(simulateMovements(100, initialRobots));
        System.out.println(safetyScore);

        final int firstTree = findTree(initialRobots);
        System.out.println(firstTree);
    }

    private static List<Robot> simulateMovements(int seconds, List<Robot> robots) {
        if (seconds == 0) { return robots; }
        return simulateMovements(seconds - 1, robots.stream()
                .map(Day14Solution::moveRobot)
                .toList()
        );
    }

    private static int findTree(List<Robot> robots) {
        int counter = 0;
        while (! formsTree(robots, 9)) {
            robots = simulateMovements(1, robots);
            counter++;
        }
        return counter;
    }

    private static Robot moveRobot(Robot robot) {
        final Point newPos = Utils.addVector(robot.p, robot.v);
        //@formatter:off
        if (newPos.x < 0)        { newPos.x = WIDTH  + newPos.x; }
        if (newPos.y < 0)        { newPos.y = HEIGHT + newPos.y; }
        if (newPos.x >= WIDTH )  { newPos.x = newPos.x - WIDTH; }
        if (newPos.y >= HEIGHT ) { newPos.y = newPos.y - HEIGHT; }
        //@formatter:on
        return new Robot(newPos, robot.v);
    }

    private static int safetyScore(List<Robot> robots) {
        //@formatter:off
        final int q1 = safetyScore(new Point(            0,              0), new Point(WIDTH / 2 -1, HEIGHT / 2 -1), robots);
        final int q2 = safetyScore(new Point(WIDTH / 2 + 1,              0), new Point(WIDTH -1,     HEIGHT / 2 -1), robots);
        final int q3 = safetyScore(new Point(            0, HEIGHT / 2 + 1), new Point(WIDTH / 2 -1, HEIGHT -1), robots);
        final int q4 = safetyScore(new Point(WIDTH / 2 + 1, HEIGHT / 2 + 1), new Point(WIDTH -1,     HEIGHT -1), robots);
        //@formatter:on

        return q1 * q2 * q3 * q4;
    }

    /**
     * @param p1 top left position
     * @param p2 bottom right position
     */
    private static int safetyScore(Point p1, Point p2, List<Robot> robots) {
        return (int) robots.stream()
                .filter(r -> p1.x <= r.p.x && r.p.x <= p2.x)
                .filter(r -> p1.y <= r.p.y && r.p.y <= p2.y)
                .count();
    }

    @SuppressWarnings("SameParameterValue")
    private static boolean formsTree(List<Robot> robots, int branchLength) {
        final char[][] array2d = new char[HEIGHT][WIDTH];
        for (Robot robot : robots) {
            array2d[robot.p.y][robot.p.x] = '.';
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                start:
                for (int i = 0; i <= branchLength; i++) {
                    for (int j = 0; j <= branchLength; j++) {
                        if (Utils.isWithinBounds(array2d, new Point(x - i, y + j))) {
                            if (array2d[y + j][x - i] != '.') {
                                break start;
                            } else if (i == branchLength) {
                                Utils.print2dArray(array2d);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // Utilities
    // ------------------------------------------------------------------------

    private record Robot(Point p, Point v) { }

    private static List<Robot> readRobotConfiguration(List<String> input) {
        final Pattern robotConfigPattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        return input.stream()
                .map(robotConfigPattern::matcher)
                .map(matcher -> {
                    final boolean _ = matcher.find();
                    return new Robot(
                            new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                            new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))));
                }).toList();
    }

}