package common;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static List<String> readInputFromResources(String fileName)  {
        List<String> lines = new ArrayList<>();

        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(fileName)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Could not load file %s", fileName));
        }

        return lines;
    }
    
    public static int smallestIndex(int [] array) {
        if (array.length == 0) return -1;

        int smallestIdx = 0;
        long value      = array[smallestIdx];

        for (int i = 0; i < array.length; i++) {
            if (value > array[i]) {
                value = array[i];
                smallestIdx = i;
            }
        }
        return smallestIdx;
    }

    public static int biggestIndex(int [] array) {
        if (array.length == 0) return -1;

        int biggestIdx = 0;
        long value      = array[biggestIdx];

        for (int i = 0; i < array.length; i++) {
            if (value < array[i]) {
                value = array[i];
                biggestIdx = i;
            }
        }
        return biggestIdx;
    }

    public static List<Integer> readAsListOfInt(String input) {
        return Arrays.stream(input.split(" "))
                .filter(s -> ! s.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

    public static List<Long> readAsListOfLong(String input) {
        return Arrays.stream(input.split(" "))
                .filter(s -> ! s.isBlank())
                .map(Long::parseLong)
                .toList();
    }

    public static char[][] as2dArray(List<String> input) {
        if (input.isEmpty()) { return new char[0][0]; }

        int height = input.size();
        int width  = input.get(0).length();

        char [][] array = new char[height][width];
        for (int i = 0; i < input.size(); i++) {
            array[i] = input.get(i).toCharArray();
        }
        return array;
    }

    public static char[][] arrayCopy(char[][] that) {
        if (that.length == 0) { return new char[0][0]; }

        char[][] copy = new char[that.length][that[0].length];
        for (int y = 0; y < that.length; y++) {
            System.arraycopy(that[y], 0, copy[y], 0, that[y].length);
        }
        return copy;
    }

    public static void replaceCharacters(char [][] array2d, List<Point> coordinates, char newChar) {
        for (Point coords : coordinates) {
            array2d[coords.y][coords.x] = newChar;
        }
    }

    public static void print2dArray(char [][] array2d) {
        for (char[] chars : array2d) {
            System.out.println(new String(chars));
        }
    }

}
