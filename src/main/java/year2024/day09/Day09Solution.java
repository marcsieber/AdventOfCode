package year2024.day09;

import common.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2024/day/9">Day 09</a>
 */
public class Day09Solution {

    //private static final String inputFile = "2024/provided/day09-example.txt";
    private static final String inputFile = "2024/provided/day09.txt";

    public static void main(String[] args) {
        final String input = Utils.readInputFromResources(inputFile).getFirst();

        final long diskChecksumSingleBlockFragmentation = checksumWithSingleBlockOptimizationStrategy(input);
        System.out.println(diskChecksumSingleBlockFragmentation);

        final long diskChecksumWholeBlockFragmentation = checksumWithWholeBlockOptimizationStrategy(input);
        System.out.println(diskChecksumWholeBlockFragmentation);
    }

    private static long checksumWithSingleBlockOptimizationStrategy(String input) {
        final List<FileBlock> diskMap = buildDiskMap(input);
        optimizeDiskUsageWithSingleBlockStrategy(diskMap);
        return calculateFileSystemChecksum(diskMap);
    }

    private static long checksumWithWholeBlockOptimizationStrategy(String input) {
        final List<FileBlock> diskMap = buildDiskMap(input);
        optimizeDiskUsageWithWholeBlockStrategy(diskMap);
        return calculateFileSystemChecksum(diskMap);
    }

    private static List<FileBlock> buildDiskMap(String input) {
        final List<FileBlock> diskMap = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            final int blockCount = (int) input.charAt(i) - '0';
            for (int j = 0; j < blockCount; j++) {
                final boolean isFile = i % 2 == 1;
                diskMap.add(new FileBlock(isFile ? -1 : i/2, blockCount));
            }
        }
        return diskMap;
    }

    private static void optimizeDiskUsageWithSingleBlockStrategy(List<FileBlock> diskMap) {
        int emptyBlockMarker = 0;
        int fileBlockMarker = diskMap.size()-1;

        // both markers need to be at least 2 apart in order to be able to swap.
        // _ _ E _ _ F _ -> swappable
        // _ _ _ E _ F _ -> not swappable because E and F would move past each other
        while (emptyBlockMarker < fileBlockMarker-2) {
            while(diskMap.get(emptyBlockMarker).id != -1) {
                emptyBlockMarker++;
            }
            while(diskMap.get(fileBlockMarker).id == -1) {
                fileBlockMarker--;
            }
            Collections.swap(diskMap, emptyBlockMarker, fileBlockMarker);
        }
    }

    private static void optimizeDiskUsageWithWholeBlockStrategy(List<FileBlock> diskMap) {
        int fileBlockMarker = diskMap.size()-1;
        while (fileBlockMarker != -1) {
            while(diskMap.get(fileBlockMarker).id == -1) { // repeat until we find a file on the current pos
                fileBlockMarker--;
            }
            final int blockSize = diskMap.get(fileBlockMarker).blocks;

            // set the block marker to the start of the block (left)
            // add 1 because the marker was already on the right most element of the block.
            fileBlockMarker = fileBlockMarker - blockSize +1;

            // we always reset, so we do not have to update the free blocks
            int emptyBlockMarker = 0;
            int emptyBlockCount = 0;
            // abort when enough free spaces found or a block would be placed backwards
            while(emptyBlockCount != blockSize && emptyBlockMarker < fileBlockMarker ) {
                if (diskMap.get(emptyBlockMarker).id == -1) {
                    emptyBlockCount ++;
                } else {
                    emptyBlockCount = 0;
                }
                emptyBlockMarker++;
            }
            // move empty block marker to the start of the empty block
            // -1 is not needed because marker was moved one index to far
            emptyBlockMarker = emptyBlockMarker - blockSize;
            if (emptyBlockCount == blockSize) {
                for (int i = 0; i < blockSize; i++) {
                    Collections.swap(diskMap, emptyBlockMarker + i, fileBlockMarker + i);
                }
            }
            fileBlockMarker --; // move 1 to the left the get to the next unprocessed block position
        }
    }

    private static long calculateFileSystemChecksum(List<FileBlock> diskMap) {
        long checksum = 0;
        for (long i = 0; i < diskMap.size(); i++) {
            final int id = diskMap.get((int) i).id;
            if (id != -1) {
                checksum += i * id;
            }
        }
        return checksum;
    }

    // Utilities
    // ------------------------------------------------------------------------

    /**
     * Represents 1 single block in the file system
     * @param id if the block file, -1 when empty space
     * @param blocks number of consecutively blocks of same type.
     */
    private record FileBlock(int id, int blocks) {
        @Override public String toString() {
            return id == -1 ? "." : Integer.toString(id);
        }
    }
}