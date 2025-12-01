package year2024.day08;

import common.Utils;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="http://adventofcode.com/2024/day/8">Day 08</a>
 */
public class Day08Solution {

    //private static final String inputFile = "2024/provided/day08-example.txt";
    private static final String inputFile = "2024/provided/day08.txt";

    private static final char EMPTY_LOCATION = '.';
    public static final  char ANTINODE       = '#';

    public static void main(String[] args) {

        final List<String> input   = Utils.readInputFromResources(inputFile);
        final char[][]     array2d = Utils.as2dArray(input);

        final Map<Character, List<Point>> antennaLocations = Utils.uniqueCharsInArray2d(array2d); // contains unwanted points
        antennaLocations.remove(EMPTY_LOCATION);

        final int countAmountUniqueAntinodeLocations = uniqueAntinodeLocations(array2d, antennaLocations, false);
        System.out.println(countAmountUniqueAntinodeLocations);

        final int countAmountUniqueAntinodeLocationsWithResonance = uniqueAntinodeLocations(array2d, antennaLocations, true);
        System.out.println(countAmountUniqueAntinodeLocationsWithResonance);
    }

    private static int uniqueAntinodeLocations(char[][] array2d, Map<Character, List<Point>> antennaLocations, boolean resonance) {
        final char[][] antinodeLocations = Utils.arrayCopy(array2d);
        for (List<Point> antennas : antennaLocations.values()) {
            for (Point p1 : antennas) {
                for (Point p2 : antennas) {
                    if (! p1.equals(p2)) {
                        final Point vecP1P2 = Utils.positionVector(p1, p2);
                        //@formatter:off
                        boolean loop = true;
                        Point   antinodeLocation = Utils.addVector(resonance ? p1 : p2, vecP1P2); // in resonance mode we start on p1.
                        while (loop) {
                            if (! resonance) { loop = false; }
                            if (Utils.isWithinBounds(array2d, antinodeLocation)) {
                                antinodeLocations[antinodeLocation.y][antinodeLocation.x] = ANTINODE;
                                antinodeLocation = Utils.addVector(antinodeLocation, vecP1P2);

                            } else { loop = false; }
                        }
                        //@formatter:off
                    }
                }
            }
        }

        return (int) Utils.uniqueCharsInArray2d(antinodeLocations).get(ANTINODE).stream()
                .distinct()
                .count();
    }

}