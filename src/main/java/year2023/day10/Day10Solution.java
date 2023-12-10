package year2023.day10;

import common.Utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Hint: a pipe always connect to 2 others
 *
 * @see <a href="https://adventofcode.com/2023/day/10">Day 10</a>
 */
public class Day10Solution {

//  private static final String inputFile = "day10-example1.txt";
//  private static final String inputFile = "day10-example2.txt";
    private static final String inputFile = "day10.txt";

    private static final char start = 'S';

    public static void main(String[] args) {

        // general
        List<String> input        = Utils.readInputFromResources(inputFile);
        char[][] array2d          = Utils.as2dArray(input);

        Point startCoordinates    = startingCoordinates(array2d);
        List<Point> startAdjacent = findCoordinateConnectingToStart(array2d, startCoordinates);
        List<Point> pipeNetwork   = findPipesOfNetwork(array2d, startCoordinates, startAdjacent);

        // first
        int half = pipeNetwork.size() / 2;
        System.out.println(half);

    }

    private static Point startingCoordinates(char [][] array2d) {
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {
                if (array2d[y][x] == start) {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalArgumentException("Start not found");
    }

    private static List<Point> findCoordinateConnectingToStart(char [][] array2d, Point startCoordinates) {
        List<Point> connectingPoints = new ArrayList<>();
        for (int y = -1; y <=1; y++) {
            for (int x = -1; x <=1; x++) {
                if ( ! (x == 0 && y == 0)) {
                    Point xy = new Point(startCoordinates.x + x, startCoordinates.y + y);
                    if (0 <=xy.x && 0 < xy.y && xy.y < array2d.length && xy.x < array2d[xy.y].length) {
                        List<Point> connectingTo = PipePart.valueOf(array2d[startCoordinates.y + y][startCoordinates.x + x]).getAdjacentCoordinates(xy);
                        for (Point p : connectingTo) {
                            if (p.equals(startCoordinates)) {
                                connectingPoints.add(xy);
                            }
                        }
                    }
                }
            }
        }
        return connectingPoints;
    }

    private static List<Point> findPipesOfNetwork(char[][] array2d, Point start, List<Point> startAdjacent) {
        Point current = startAdjacent.get(0);
        Point end     = startAdjacent.get(1);

        List<Point> allPoints = new ArrayList<>(List.of(start));
        while( !  current.equals(end)) next: {
            List<Point> nextPoints = PipePart.valueOf(array2d[current.y][current.x]).getAdjacentCoordinates(current);
            for (Point nextPoint : nextPoints) {
                if ( ! allPoints.contains(nextPoint)) {
                    allPoints.add(current);
                    current = nextPoint;
                    break next;  // every time only one point can be new
                }
            }
        }
        allPoints.add(end);
        return allPoints;
    }


    private enum PipePart {
        START        (  0,  0, 0, 0),
        NONE         (  0,  0, 0, 0),
        VERTICAL     (  0, -1, 0, 1),
        HORIZONTAL   ( -1,  0, 1, 0),
        NORTH_WEST_J (  0, -1,-1, 0),
        NORTH_EAST_L (  0, -1, 1, 0),
        SOUTH_WEST_7 ( -1,  0, 0, 1),
        SOUTH_EAST_F (  1,  0, 0, 1);

        PipePart(int p1X, int p1Y, int p2X, int p2Y) {
            this.p1X = p1X;
            this.p1Y = p1Y;
            this.p2X = p2X;
            this.p2Y = p2Y;
        }

        private final int p1X, p1Y, p2X, p2Y;

        private List<Point> getAdjacentCoordinates(int x, int y) { return getAdjacentCoordinates(new Point(x, y)); }
        private List<Point> getAdjacentCoordinates(Point p) {
            return List.of(
                    new Point(p.x + p1X, p.y + p1Y),
                    new Point(p.x + p2X, p.y + p2Y)
            );
        }

        public static PipePart valueOf(char c) {
            return switch (c) {
                case 'S' -> START;
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'J' -> NORTH_WEST_J;
                case 'L' -> NORTH_EAST_L;
                case '7' -> SOUTH_WEST_7;
                case 'F' -> SOUTH_EAST_F;
                default  -> NONE;
            };
        }
    }

}