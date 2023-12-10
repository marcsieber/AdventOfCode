package common;

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


}
