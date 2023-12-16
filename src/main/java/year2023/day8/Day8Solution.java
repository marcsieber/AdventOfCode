package year2023.day8;

import common.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://adventofcode.com/2023/day/8">Day 8</a>
 */
@SuppressWarnings("CommentedOutCode")
public class Day8Solution {

//  private static final String inputFile = "2023/provided/day8-example1.txt";
//  private static final String inputFile = "2023/provided/day8-example2.txt";
//  private static final String inputFile = "2023/provided/day8-example3.txt";
    private static final String inputFile = "2023/provided/day8.txt";

    public static void main(String[] args) {

        // general
        List<String> input = Utils.readInputFromResources(inputFile);
        char [] directions = input.get(0).toCharArray();
        Map<String, NextHop> map = createMap(input.subList(2, input.size()));

        // first
        long stepCount = countStepsToTarget("AAA", "ZZZ", map, directions);
        System.out.println(stepCount);
    }

    private record NextHop(String left, String right) { }
    private static Map<String, NextHop> createMap(List<String> lines) {
        Map<String, NextHop> map = new HashMap<>();

        for (String line : lines) {
            line = line.replace("(", "").replace(")", "");
            String[] split = line.split("=");
            String current = split[0].trim();
            String[] lr    = split[1].split(",");
            map.put(current, new NextHop(lr[0].trim(), lr[1].trim()));
        }
        return map;
    }

    private static long countStepsToTarget(String src, String dest, Map<String, NextHop> map, char[] directions) {
        int steps = 0;
        String currentPosition = src;

        while( ! currentPosition.equals(dest)) {
            NextHop nextHop = map.get(currentPosition);
            currentPosition = directions[steps % directions.length] == 'L' ? nextHop.left : nextHop.right;
            steps ++;
        }
        return steps;
    }

}