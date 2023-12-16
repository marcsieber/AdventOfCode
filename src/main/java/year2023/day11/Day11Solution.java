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

//    private static final String inputFile = "2023/provided/day11-example.txt";
    private static final String inputFile = "2023/provided/day11.txt";

    private static final char galaxy  = '#';
    private static final char empty   = '.';

    public static void main(String[] args) {

        // general
        List<String> input            = Utils.readInputFromResources(inputFile);
        char[][] starMap              = Utils.as2dArray(input);
        List<Point> galaxyCoordinates = findGalaxies(starMap);

        // first
        List<Expansion> expansions = new ArrayList<>(findColumnsToExpand(starMap).stream()
                .map(i -> new Expansion(i, true, 2)) // double row count
                .toList());

        expansions.addAll(findRowsToExpand(starMap).stream()
                .map(i -> new Expansion(i, false, 2))  // double column count
                .toList());

        System.out.println(calculateDistances(expansions, galaxyCoordinates));

        // second
        expansions = expansions.stream ()
                .map(e -> new Expansion(e.index, e.row, 1000000))
                .toList();

        System.out.println(calculateDistances(expansions, galaxyCoordinates));
    }

    private record Expansion(int index, boolean row, int expansion) { }

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

    private static long calculateDistances(List<Expansion> expansions, List<Point> points) {
        long totalDistance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);

                long expandX = calculateDistanceWithExpansion(expansions, p1, p2, true);
                long expandY = calculateDistanceWithExpansion(expansions, p1, p2, false);
                totalDistance +=  expandX + expandY ;
            }
        }
        return totalDistance;
    }

    private static long calculateDistanceWithExpansion(List<Expansion> expansions, Point from, Point to, boolean row) {
        expansions = expansions.stream()
                .filter( e -> e.row && row || ( ! row && ! e.row))
                .toList();

        int coordinateFrom = row ? from.x : from.y;
        int coordinateTo   = row ? to.x   : to.y;
        int step           = (int) Math.signum( coordinateTo - coordinateFrom);

        long expansion = 0;
        for (int i = coordinateFrom +step; i != (step + coordinateTo); i += step) {
            final int idx = i;
            int expandBy = expansions.stream()
                    .filter(e -> e.index == idx)
                    .mapToInt(Expansion::expansion).findFirst().orElse(1);
            expansion += expandBy;
        }
        return expansion;
    }

}
