package year2024.day04;

import common.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @see <a href="http://adventofcode.com/2024/day/4">Day 04</a>
 */
public class Day04Solution {

    @SuppressWarnings("CommentedOutCode")
    //private static final String inputFile = "2024/provided/day04-example.txt";
    //private static final String inputFile = "2024/provided/day04-example2.txt";
    private static final String inputFile = "2024/provided/day04.txt";

    public static void main(String[] args) {
        final List<String> input = Utils.readInputFromResources(inputFile);
        final char[][] array2d = Utils.as2dArray(input);

        final long countXmas = countXmas(array2d);
        System.out.println(countXmas);

        final long countXDashMas = countXDashMas(array2d);
        System.out.println(countXDashMas);
    }

    private static long countXmas(char[][] array2d) {
        long count = 0;
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {

                if (array2d[y][x] != 'X') { continue; }

                final Point startingPos = new Point(x, y);
                final List<Direction> directions = findNextDirections('M', startingPos, array2d);
                for (Direction direction : directions) {
                    Point next = direction.add(startingPos);
                    if (Utils.isWithinBounds(array2d, (next = direction.add(next))) && array2d[next.y][next.x] == 'A' &&
                        Utils.isWithinBounds(array2d, (next = direction.add(next))) && array2d[next.y][next.x] == 'S') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static long countXDashMas(char[][] array2d) {
        long count = 0;
        for (int y = 0; y < array2d.length; y++) {
            for (int x = 0; x < array2d[y].length; x++) {

                if (array2d[y][x] != 'A') { continue; }

                final Point xy = new Point(x, y);
                final boolean diagonalDescending = isDiagonalMas(array2d, Direction.SOUTH_EAST.add(xy), Direction.NORTH_WEST.add(xy));
                final boolean diagonalAscending  = isDiagonalMas(array2d, Direction.SOUTH_WEST.add(xy), Direction.NORTH_EAST.add(xy));

                if (diagonalAscending && diagonalDescending) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isDiagonalMas(char[][] array2d, Point left, Point right) {
        return
            (Utils.isWithinBounds(array2d, (right)) && Utils.isWithinBounds(array2d, (left)) && array2d[right.y][right.x] == 'M' && array2d[left.y][left.x] == 'S') ||
            (Utils.isWithinBounds(array2d, (right)) && Utils.isWithinBounds(array2d, (left)) && array2d[right.y][right.x] == 'S' && array2d[left.y][left.x] == 'M');
    }

    @SuppressWarnings("SameParameterValue")
    private static List<Direction> findNextDirections( char next, Point currentPos, char[][] array2d) {
        return Arrays.stream(Direction.values())
                .filter(d -> Utils.isWithinBounds(array2d, d.add(currentPos)))
                .filter(d -> {
                    final Point p = d.add(currentPos);
                    return array2d[p.y][p.x] == next;
                })
                .collect(Collectors.toList());
    }


    private enum Direction {
        NORTH_WEST(new Point(-1, -1)),
        NORTH     (new Point( 0, -1)),
        NORTH_EAST(new Point( 1, -1)),
        EAST      (new Point( 1,  0)),
        SOUTH_EAST(new Point( 1,  1)),
        SOUTH     (new Point( 0,  1)),
        SOUTH_WEST(new Point(-1,  1)),
        WEST      (new Point(-1,  0));

        Direction(Point direction) {
            this.direction = direction;
        }
        private final Point direction;
        private Point add(Point point) { return new Point(direction.x+ point.x, direction.y + point.y); }
    }

}