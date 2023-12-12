package common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Generated Using IntelliJ AI
class UtilsTest {

    private char[][] originalArray;

    @BeforeEach
    void setUp() {
        originalArray = new char[][]{
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'}
        };
    }

    @AfterEach
    void tearDown() {
       originalArray = null;
    }

    @Test
    void insertRowAtPosInTheMiddle() {
        char[][] expectedArray = new char[][]{
            {'a', 'b', 'c'},
            {'x', 'x', 'x'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'}
        };
    
        assertArrayEquals(expectedArray, Utils.insertRowAtPos(originalArray, 1, 'x'));
    }

    @Test
    void insertRowAtPosInTheBeginning() {
        char[][] expectedArray = new char[][]{
            {'x', 'x', 'x'},
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'}
        };
    
        assertArrayEquals(expectedArray, Utils.insertRowAtPos(originalArray, 0, 'x'));
    }

    @Test
    void insertRowAtPosAtTheEnd() {
        char[][] expectedArray = new char[][]{
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'x', 'x', 'x'}
        };
    
        assertArrayEquals(expectedArray, Utils.insertRowAtPos(originalArray, 3, 'x'));
    }

    @Test
    void insertRowAtPosOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, 
            () -> Utils.insertRowAtPos(originalArray, -1, 'x'));
        assertThrows(ArrayIndexOutOfBoundsException.class, 
            () -> Utils.insertRowAtPos(originalArray, 4, 'x'));
    }

    @Test
    void insertColumnAtPos_ShouldInsertColumnAtCorrectPosition() {
        char[][] original = new char[][]{{'a', 'b'}, {'c', 'd'}};
        char[][] expected = new char[][]{{'a', '0', 'b'}, {'c', '0', 'd'}};

        assertArrayEquals(expected, Utils.insertColumnAtPos(original, 1, '0'));
    }

    @Test
    void insertColumnAtPos_WithNegativePosition_ShouldThrowArrayIndexOutOfBoundsException() {
        char[][] original = new char[][]{{'a', 'b'}, {'c', 'd'}};

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Utils.insertColumnAtPos(original, -1, '0'));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Utils.insertColumnAtPos(original, original.length+1, '0'));
    }

    @Test
    void insertColumnAtPos_ShouldInsertColumnAtBeginingIfPosIsZero() {
        char[][] original = new char[][]{{'a', 'b'}, {'c', 'd'}};
        char[][] expected = new char[][]{{'0', 'a', 'b'}, {'0', 'c', 'd'}};

        assertArrayEquals(expected, Utils.insertColumnAtPos(original, 0, '0'));
    }

    @Test
    void insertColumnAtPos_ShouldInsertColumnAtEndIfPosIsZero() {
        char[][] original = new char[][]{{ 'a', 'b', }, {'c', 'd'}};
        char[][] expected = new char[][]{{ 'a', 'b', '0'}, {'c', 'd', '0'}};

        assertArrayEquals(expected, Utils.insertColumnAtPos(original, 2, '0'));
    }
}