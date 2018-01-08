package ca.bcit.comp2526.a2c;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Carnivore can moves, eat CarniEdible Organisms, and reproduce with other
 * Carnivore in the world.
 * 
 * @author Steve Cho
 * @version 2017
 *
 */
@SuppressWarnings("serial")
public class Carnivore extends Organism implements Animal, OmniEdible {
    /**
     * Total amount of turns a Carnivore can have.
     */
    public static final int LIFE_SPAN = 7;

    /**
     * Number of partners needed to reproduce.
     */
    public static final int REPRODUCE_PARTNERS = 1;

    /**
     * Number of empty Cells needed to reproduce.
     */
    public static final int REPRODUCE_SPACE = 2;

    /**
     * Number of food needed to reproduce.
     */
    public static final int REPRODUCE_FOOD = 2;

    /**
     * Constructs Carnivore with the location of the Cell it is created in and
     * sets Organism's conditions for reproducing.
     * 
     * @param location
     *            the Cell that Herbivore is created in
     */

    public Carnivore(final Cell location) {
        super(location);
        super.setLifeLimit(LIFE_SPAN);
        super.setReproduceFood(REPRODUCE_FOOD);
        super.setReproducePartner(REPRODUCE_PARTNERS);
        super.setReproduceSpace(REPRODUCE_SPACE);
    }

    @Override
    /**
     * Finds food the Carnivore can eat.
     * 
     * @param adjacentCell
     *            Cells to find food in
     * @return ArrayList<Cell> Cells containing CarniEdible
     */

    public ArrayList<Cell> findFood(Cell[] adjacentCell) {
        ArrayList<Cell> food = new ArrayList<Cell>();

        for (Cell foodType : adjacentCell) {
            if (foodType.getOrganism() instanceof CarniEdible) {
                food.add(foodType);
            }

        }
        return food;
    }

    @Override
    /**
     * Sets background color to magenta.
     */
    public void init() {
        super.getCell().setBackground(Color.MAGENTA);

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
        if (mate.getOrganism() instanceof Carnivore) {
            mateFound = true;
        }
        return mateFound;
    }

    @Override
    /**
     * Creates new Carnivore in the nextCell.
     * 
     * @param nextCell
     *            the Cell to add the new Carnivore
     */
    public void giveLife(Cell nextCell) {
        Carnivore carni = new Carnivore(nextCell);
        nextCell.addLife(carni);

    }

}
