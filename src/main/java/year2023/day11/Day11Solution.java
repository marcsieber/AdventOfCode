package year2023.day11;

import common.Utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @see <a href="https://adventofcode.com/2023/day/11">Day 11</a>
 */
public class Day11Solution {

//    private static final String inputFile = "day11-example.txt";
    private static final String inputFile = "day11.txt";

    private static final char galaxy  = '#';
    private static final char empty   = '.';

    public static void main(String[] args) {

        // general
        List<String> input        = Utils.readInputFromResources(inputFile);
        char[][] starMap          = Utils.as2dArray(input);

        // first
        List<Integer> expansionColumns = findColumnsToExpand(starMap);
        List<Integer> expansionRows    = findRowsToExpand(starMap);

        starMap = fillEmptySpace(starMap, expansionColumns, expansionRows);
        List<Point> galaxyCoordinates = findGalaxies(starMap);
        System.out.println(calculateDistances(galaxyCoordinates));

        // second
    }

    private static List<Integer> findRowsToExpand(char[][] array2d) {
        return IntStream.range(0, array2d.length)
                .filter(y -> IntStream.range(0, array2d[y].length).allMatch(x -> array2d[y][x] == empty))
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> findColumnsToExpand(char[][] array2d) {
        return IntStream.range(0, array2d[0].length)
                .filter(x -> IntStream.range(0, array2d.length).allMatch(y -> array2d[y][x] == empty))
                .boxed()
                .collect(Collectors.toList());
    }

    private static char [][] fillEmptySpace(char[][] array2d, List<Integer> expansionColumns, List<Integer> expansionRows) {
        char [][] copy = array2d;
        int idx = 0;
        for (int row : expansionRows) {
            copy = Utils.insertRowAtPos(copy, row - idx-- , empty);
        }
        idx = 0;
        for (int column : expansionColumns) {
            copy = Utils.insertColumnAtPos(copy, column - idx--, empty);
        }
        return copy;
    }

    private static List<Point> findGalaxies(char[][] array2d) {
        List<Point> coordinates = new ArrayList<>();
        for (int y = 0; y < array2d.length; y ++) {
            for (int x = 0; x < array2d[y].length; x++) {
                if (array2d[y][x] == galaxy) {
                    coordinates.add(new Point(x, y));
                }
            }
        }
        return coordinates;
    }

    private static int calculateDistances(List<Point> points) {
        int totalDistance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);
                totalDistance += Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
            }
        }
        return totalDistance;
    }

}
