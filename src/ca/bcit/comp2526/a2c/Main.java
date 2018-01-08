package ca.bcit.comp2526.a2c;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Main sets up the World and runs the Game of Life.
 * 
 * @author Steve Cho
 * @version 2017
 */
public final class Main {
    private static final Toolkit TOOLKIT;
    private static final int LIMIT = 25;

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
    }

    /**
     * Cannot construct Main object.
     */
    private Main() {
    }

    /**
     * Creates and World and GameFrame to run the game.
     * 
     * @param argv
     *            argument to pass
     */
    public static void main(final String[] argv) {
        final GameFrame frame;
        final World world;

        RandomGenerator.reset();
        // create a 25 b
        world = new World(LIMIT, LIMIT);
        world.init();
        frame = new GameFrame(world);
        position(frame);
        frame.init();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Positions and GameFrame and sets its size.
     * 
     * @param frame
     */
    private static void position(final GameFrame frame) {
        final Dimension size;

        size = calculateScreenArea(0.80f, 0.80f);
        frame.setSize(size);
        frame.setLocation(centreOnScreen(size));
    }

    /**
     * Centres the screen.
     * 
     * @param size
     *            to centre
     * @return Point
     */
    public static Point centreOnScreen(final Dimension size) {
        final Dimension screenSize;

        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }

        screenSize = TOOLKIT.getScreenSize();

        return (new Point((screenSize.width - size.width) / 2,
                (screenSize.height - size.height) / 2));
    }

    /**
     * Calculates screen area.
     * 
     * @param widthPercent
     *            percent of width
     * @param heightPercent
     *            percent of height
     * @return Dimension
     */
    public static Dimension calculateScreenArea(final float widthPercent,
            final float heightPercent) {
        final Dimension screenSize;
        final Dimension area;
        final int width;
        final int height;
        final int size;

        if ((widthPercent <= 0.0f) || (widthPercent > 100.0f)) {
            throw new IllegalArgumentException("widthPercent cannot be "
                    + "<= 0 or > 100 - got: " + widthPercent);
        }

        if ((heightPercent <= 0.0f) || (heightPercent > 100.0f)) {
            throw new IllegalArgumentException("heightPercent cannot be "
                    + "<= 0 or > 100 - got: " + heightPercent);
        }

        screenSize = TOOLKIT.getScreenSize();
        width = (int) (screenSize.width * widthPercent);
        height = (int) (screenSize.height * heightPercent);
        size = Math.min(width, height);
        area = new Dimension(size, size);

        return (area);
    }
}
