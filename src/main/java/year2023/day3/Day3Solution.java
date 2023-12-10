package year2023.day3;

import common.Utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Approach:
 * <ol>
 *   <li>Read the whole engine plan as a 2d array.</li>
 *   <li>Read all positions of symbols (characters not equal to '.')</li>
 *   <li>Create a List of all adjacent indexes</li>
 *   <li>Read the number lying partially on that index</li>
 * </ol>
 *
 * @see <a href="https://adventofcode.com/2023/day/3">Day 3</a>
 */
public class Day3Solution {

//  private static final String inputFile = "day3-example.txt";
    private static final String inputFile = "day3.txt";

    private static final char placeholder = '.';
    private static final char gear = '*';


    public static void main(String[] args) {

        // general
        List<String> input = Utils.readInputFromResources(inputFile);
        char[][] array = Utils.as2dArray(input);

        // first
        List<Point>       points                 = symbolPositions(array, false);
        List<List<Point>> surroundingPointsLists = surroundingPointsOf(points, array[0].length, array.length);
        List<Integer>     numbers                = readNumbersFromCoordinates(array, surroundingPointsLists, array[0].length, false);

        int sum = numbers.stream()
                .mapToInt(i -> i)
                .sum();

        System.out.println(sum);

        // second
        points                 = symbolPositions(array, true);
        surroundingPointsLists = surroundingPointsOf(points, array[0].length, array.length);
        numbers                = readNumbersFromCoordinates(array, surroundingPointsLists, array[0].length, true);

        sum = 0;
        for (int i = 0; i <numbers.size(); i+=2) {
            int a = numbers.get(i);
            int b = numbers.get(i+1);
            sum += a * b;
        }
        System.out.println(sum);
    }

    private static List<Point> symbolPositions(char[][] array, boolean gearsOnly) {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < array.length; y ++) {
            for (int x = 0; x < array[y].length; x++) {
                char c = array[y][x];
                if (c != placeholder && ! Character.isDigit(c)) {
                    if ( ! gearsOnly || c == gear) {
                        points.add(new Point(x, y));
                    }
                }
            }
        }
        return points;
    }

    /**
     * Returns for each point a list of surrounding points.
     */
    private static List<List<Point>> surroundingPointsOf(List<Point> points, int maxX, int maxY) {
        List<List<Point>> list = new ArrayList<>();
        for (Point point : points) {
            List<Point> surrounding = new ArrayList<>();

            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if ( ! (x == 0 && y == 0)) {
                        int newX = point.x + x;
                        int newY = point.y + y;

                        if (0 <= newX && newX < maxX && 0 <= newY && newX < maxY) {
                            surrounding.add(new Point(newX, newY));
                        }
                    }
                }
            }
            list.add(surrounding);
        }
        return list;
    }

    /**
     * Reads the numbers at the given coordinates. If the points correspond the same number
     * the number is only read once.
     */
    private static List<Integer> readNumbersFromCoordinates(char [][] array, List<List<Point>> surroundingPointsLists, int maxX, boolean adjacentOnly) {
        List<Integer> allNumbers = new ArrayList<>();

        final char zero = '0';
        for (List<Point> surroundingPoints : surroundingPointsLists) {

            List<Integer> numbers = new ArrayList<>();
            Set<Point> visited = new HashSet<>();
            for (Point point : surroundingPoints)
                nextPoint:{
                    char[] line = array[point.y];

                    if (!Character.isDigit(line[point.x])) {
                        continue;
                    }

                    // go max left of the number
                    int x = point.x;
                    while (0 < x && Character.isDigit(line[x - 1])) {
                        if (visited.contains(new Point(x, point.y))) {
                            break nextPoint;
                        }
                        x--;
                    }

                    // read number
                    int number = line[x] - zero;
                    while (x + 1 < maxX && Character.isDigit(line[x + 1])) {
                        number = number * 10 + (line[++x] - zero);
                        visited.add(new Point(x, point.y));
                    }
                    numbers.add(number);
                }

            if ( ! adjacentOnly || numbers.size() == 2)  {
                allNumbers.addAll(numbers);
            }
        }

        return allNumbers;
    }

}