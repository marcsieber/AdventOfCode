package year2025.day01;

import common.Utils;

import java.util.List;

/**
 * @see <a href="http://adventofcode.com/2025/day/1">Day 01</a>
 */
public class Day01Solution {

    //    private static final String inputFile = "2025/provided/day01-example.txt";
    private static final String inputFile = "2025/provided/day01.txt";


    static void main(String[] args) {

        final List<Command> commands = Utils.readInputFromResources(inputFile).stream().map(Command::new).toList();

        final RadialDial dial1 = new RadialDial(50, false);
        System.out.println(revealPasscode(dial1, commands));

        final RadialDial dial2 = new RadialDial(50, true);
        System.out.println(revealPasscode(dial2, commands));
    }

    private static int revealPasscode(RadialDial dial, List<Command> commands) {
        commands.forEach(dial::move);
        return dial.getZeroPositionHit();
    }


    // Utilities
    // ------------------------------------------------------------------------
    private record Command(int value) {
        Command(String s) {
            final int signum = switch (s.charAt(0)) {
                case 'L' -> - 1;
                case 'R' -> 1;
                default -> throw new IllegalStateException("Unexpected value: " + s.charAt(0));
            };
            this(signum * Integer.parseInt(s.substring(1)));
        }
    }

    private static class RadialDial {
        private static final int minValue = 0;
        private static final int maxValue = 99;

        private final boolean usingClick;

        private int currentValue;
        private int zeroPositionHit = 0;

        RadialDial(int startValue, boolean usingClick) {
            this.currentValue = startValue;
            this.usingClick   = usingClick;
        }

        void move(Command command) {
            int remainingValue = command.value();

            final int sigNum = (int) Math.signum(remainingValue);
            while (remainingValue != 0) {
                currentValue += sigNum;

                if (currentValue < minValue) {
                    currentValue = maxValue;

                } else if (maxValue < currentValue) {
                    currentValue = minValue;
                }

                if (usingClick && currentValue == 0) {
                    zeroPositionHit++;
                }

                remainingValue -= sigNum;
            }

            if (currentValue == 0 && ! usingClick) {
                zeroPositionHit++;
            }
        }

        public int getZeroPositionHit() { return zeroPositionHit; }
    }

}
