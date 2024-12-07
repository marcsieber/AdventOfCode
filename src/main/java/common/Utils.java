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
        final List<String> lines = new ArrayList<>();

        try (final InputStream is = Utils.class.getClassLoader().getResourceAsStream(fileName)) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            
        } catch (final IOException e) {
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

        final int height = input.size();
        final int width  = input.getFirst().length();

        final char [][] array = new char[height][width];
        for (int i = 0; i < input.size(); i++) {
            array[i] = input.get(i).toCharArray();
        }
        return array;
    }

    public static char[][] arrayCopy(char[][] that) {
        if (that.length == 0) { return new char[0][0]; }

        final char[][] copy = new char[that.length][that[0].length];
        for (int y = 0; y < that.length; y++) {
            System.arraycopy(that[y], 0, copy[y], 0, that[y].length);
        }
        return copy;
    }

    public static boolean isWithinBounds(char[][] array2d, Point position) {
        final int x = position.x;
        final int y = position.y;
        return 0 <= y && 0 <= x && y < array2d.length && x < array2d[y].length;
    }

    public static char[][] insertColumnAtPos(char[][] array, int pos, char defaultValue) {
        if (pos < 0 || pos > array.length) { throw new ArrayIndexOutOfBoundsException(pos); }
        final char[][] copy = new char[array.length][];
        for (int y = 0; y < array.length; y++) {
            final char[] newRow = new char[array[y].length + 1];
            newRow[pos] = defaultValue;
            System.arraycopy(array[y], 0, newRow, 0, pos);
            System.arraycopy(array[y], pos, newRow, pos + 1, array[y].length - pos);
            copy[y] = newRow;
        }
        return copy;
    }

    public static char[][] insertRowAtPos(char[][] array, int pos, char defaultValue) {
        if (pos < 0 || pos > array.length) { throw new ArrayIndexOutOfBoundsException(pos); }

        final char[][] copy = new char[array.length + 1][array[0].length];
        System.arraycopy(array, 0, copy, 0, pos);
        System.arraycopy(array, pos,      copy, pos + 1, array.length - pos);
        Arrays.fill(copy[pos], defaultValue);
        return copy;
    }


    public static void replaceCharacters(char [][] array2d, List<Point> coordinates, char newChar) {
        for (Point coords : coordinates) {
            array2d[coords.y][coords.x] = newChar;
        }
    }

    @SuppressWarnings("unused")
    public static void print2dArray(char [][] array2d) {
        for (char[] chars : array2d) {
            System.out.println(new String(chars));
        }
    }

}
