package ca.bcit.comp2526.a2c;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Plant can reproduce with other Plants in the World and they are edible to
 * Herbivores and Omnivores.
 * 
 * @author Steve Cho
 * @version 2017
 */
@SuppressWarnings("serial")
public class Plant extends Organism
        implements
            Seedable,
            HerbEdible,
            OmniEdible {
    /**
     * Number of empty Cells needed to seed.
     */
    public static final int SEEDING_ZONE = 3;

    /**
     * Number of Plants needed to seed.
     */
    public static final int PLANTS_TO_SEED = 2;

    /**
     * Number of food needed to seed.
     */
    public static final int FOOD_TO_REPRODUCE = 0;

    /**
     * Constructs a Plant and sets Organism's conditions for reproducing.
     * 
     * @param location
     *            the Cell that this Plant is on
     */
    public Plant(Cell location) {
        super(location);
        super.setReproducePartner(PLANTS_TO_SEED);
        super.setReproduceSpace(SEEDING_ZONE);
        super.setReproduceFood(FOOD_TO_REPRODUCE);
    }
    /**
     * Color the Cell that the Plant is on to green.
     */
    public void init() {
        super.getCell().setBackground(Color.GREEN);
    }

    @Override
    /**
     * Finds food the Plant can eat.
     * 
     * @param adjacentCell
     *            Cells to find food in
     * @return ArrayList<Cell> Cells containing nothing
     */
    public ArrayList<Cell> findFood(Cell[] adjacentCell) {
        ArrayList<Cell> noFood = new ArrayList<Cell>();
        return noFood;
    }

    @Override
    /**
     * Creates new Plant in the nextCell.
     * 
     * @param nextCell
     *            the Cell to add the new Plant
     */
    public void giveLife(Cell nextCell) {
        Plant newPlant = new Plant(nextCell);
        nextCell.addLife(newPlant);
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
        if (mate.getOrganism() instanceof Seedable) {
            mateFound = true;
        }
        return mateFound;

    }

}
