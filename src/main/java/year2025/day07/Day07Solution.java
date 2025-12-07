package year2025.day07;

import common.Utils;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @see <a href="http://adventofcode.com/2025/day/7">Day 07</a>
 */
public class Day07Solution {

    //    private static final String inputFile = "2025/provided/day07-example.txt";
    private static final String inputFile = "2025/provided/day07.txt";

    public static final char START    = 'S';
    public static final char SPLITTER = '^';
    public static final char EMPTY    = '.';

    static void main() {

        final List<String> input = Utils.readInputFromResources(inputFile);

        final char[][] map   = Utils.as2dArray(input);
        final Point    start = Utils.uniqueCharsInArray2d(map).get(START).getFirst();

        System.out.println(propagateBeam(map, Collections.singleton(start)));

    }

    private static int propagateBeam(char[][] map, Set<Point> starts) {
        final Set<Point> nextPositions = new HashSet<>();
        final Set<Point> didSplitOn    = new HashSet<>();

        for (Point p : starts) {
            final Point    below = Utils.addVector(p, new Point(0, 1));
            final StepInfo next  = findNextPoints(map, below);
            if (next.splits() && ! next.nexPositions().isEmpty()) {
                didSplitOn.add(below);
            }
            nextPositions.addAll(next.nexPositions());
        }

        if (! nextPositions.isEmpty()) {
            return didSplitOn.size() + propagateBeam(map, nextPositions);
        }
        return 0;
    }

    record StepInfo(boolean splits, Set<Point> nexPositions) {
    }
    private static StepInfo findNextPoints(char[][] map, Point point) {
        final Set<Point> nextPoints = new HashSet<>();

        final boolean   split = isSplit(map, point);
        if (! split) {
            addToIfValidStep(nextPoints, map, point);

        } else {
            addToIfValidStep(nextPoints, map, Utils.addVector(point, new Point(- 1, 0)));
            addToIfValidStep(nextPoints, map, Utils.addVector(point, new Point(1, 0)));
        }
        return new StepInfo(split, nextPoints);
    }

    private static void addToIfValidStep(Set<Point> list, char[][] map, Point point) {
        if (isValidStep(map, point)) {
            list.add(point);
        }
    }

    private static boolean isSplit(char[][] map, Point position) {
        return Utils.isWithinBounds(map, position) &&
                map[position.y][position.x] == SPLITTER;
    }

    private static boolean isValidStep(char[][] map, Point position) {
        return Utils.isWithinBounds(map, position) &&
                map[position.y][position.x] == EMPTY;
    }


    // Utilities
    // ------------------------------------------------------------------------

}
