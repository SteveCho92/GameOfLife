package ca.bcit.comp2526.a2c;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Omnivore moves, eat OmniEdibles, and reproduces with other Omnivore in the
 * world.
 * 
 * @author Steve Cho
 * @version 2017
 */
@SuppressWarnings("serial")
public class Omnivore extends Organism implements Animal, CarniEdible {
    /**
     * Total amount of turns a Omnivore can have.
     */
    public static final int LIFE_SPAN = 2;

    /**
     * Number of partners needed to reproduce.
     */
    public static final int REPRODUCE_PARTNERS = 1;

    /**
     * Number of empty Cells needed to reproduce.
     */
    public static final int REPRODUCE_SPACE = 3;

    /**
     * Number of food needed to reproduce.
     */
    public static final int REPRODUCE_FOOD = 3;

    /**
     * Constructs Omnivore with the location of the Cell it is created in and
     * sets Organism's conditions for reproducing.
     * 
     * @param location
     *            the Cell that Herbivore is created in
     */

    public Omnivore(final Cell location) {
        super(location);
        super.setLifeLimit(LIFE_SPAN);
        super.setReproduceFood(REPRODUCE_FOOD);
        super.setReproducePartner(REPRODUCE_PARTNERS);
        super.setReproduceSpace(REPRODUCE_SPACE);
    }
    /**
     * Sets background color of the cell as yellow.
     */
    public void init() {
        super.getCell().setBackground(Color.BLUE);

    }
    @Override
    /**
     * Finds food the Omnivore can eat.
     * 
     * @param adjacentCell
     *            Cells to find food in
     * @return ArrayList<Cell> Cells containing the OmniEdible
     */

    public ArrayList<Cell> findFood(Cell[] adjacentCell) {
        ArrayList<Cell> food = new ArrayList<Cell>();

        for (Cell foodType : adjacentCell) {
            if (foodType.getOrganism() instanceof OmniEdible) {
                food.add(foodType);
            }

        }
        return food;
    }
    @Override
    /**
     * Creates new Herbivore in the nextCell.
     * 
     * @param nextCell
     *            the Cell to add the new Herbivore
     */
    public void giveLife(Cell nextCell) {
        Omnivore omni = new Omnivore(nextCell);
        nextCell.addLife(omni);

    }
    @Override
    /**
     * Checks the Cell parameter for a possible mate.
     * 
     * @param mate
     *            Cell to check possible mate
     * @return boolean for possible mate found
     */
    public boolean foundMate(Cell mate) {
        boolean mateFound = false;
        if (mate.getOrganism() instanceof Omnivore) {
            mateFound = true;
        }
        return mateFound;

    }
}
