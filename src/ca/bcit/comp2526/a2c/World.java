/**
 * 
 */
package ca.bcit.comp2526.a2c;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The World is made of Cell[][], and stores specific Organism within a random
 * limit.
 * 
 * @author Steve Cho
 * @version 2017
 */
public class World implements Serializable {

    /**
     * upper limit for random generator.
     */
    public static final int UPPER_LIM = 100;

    /**
     * Limit for chance to generate plant.
     */
    public static final int PLANT_GEN = 50;

    /**
     * Limit for chance to generate herbivore.
     */
    public static final int HERB_GEN = 80;

    /**
     * Limit for chance to generate Carnivore.
     */
    public static final int CARNI_GEN = 40;

    /**
     * Limit for chance to generate Omnivore.
     */
    public static final int OMNI_GEN = 32;
    private static final long serialVersionUID = 8470724129689321005L;
    private int row;
    private int column;
    private Cell[][] cellGrid;
    private ArrayList<Organism> organisms;

    /**
     * Constructs a World with Cell[rows][columns].
     * 
     * @param rows
     *            the max row
     * @param columns
     *            the max column
     */
    public World(final int rows, final int columns) {
        this.row = rows;
        this.column = columns;
        cellGrid = new Cell[row][column];
        organisms = new ArrayList<Organism>();
    }

    /**
     * Creates 2D array of Cell and determines chance to generate Plant or
     * Herbivore using within that Cell RandomGenerator.
     */
    public void init() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                cellGrid[i][j] = new Cell(this, i, j);

                int randGen = RandomGenerator.nextNumber(UPPER_LIM);

                if (randGen >= HERB_GEN) {
                    Herbivore herb = new Herbivore(cellGrid[i][j]);
                    cellGrid[i][j].addLife(herb);
                    organisms.add(herb);

                } else if (randGen >= PLANT_GEN) {
                    Plant plant = new Plant(cellGrid[i][j]);

                    cellGrid[i][j].addLife(plant);
                    organisms.add(plant);
                } else if (randGen >= CARNI_GEN) {
                    Carnivore carni = new Carnivore(cellGrid[i][j]);
                    cellGrid[i][j].addLife(carni);
                    organisms.add(carni);
                } else if (randGen >= OMNI_GEN) {
                    Omnivore omni = new Omnivore(cellGrid[i][j]);
                    cellGrid[i][j].addLife(omni);
                    organisms.add(omni);
                }
            }
        }
    }

    /**
     * Returns the cell at specified location.
     * 
     * @param rowY
     *            the y coordinate of cell
     * @param columnX
     *            the x coordinate of cell
     * @return Cell at the coordinates of parameter
     */
    public Cell getCellAt(final int rowY, final int columnX) {
        return cellGrid[rowY][columnX];
    }

    /**
     * Takes a turn for all Organisms if they are alive, and update the World.
     */
    public void takeTurn() {

        for (int i = 0; i < organisms.size(); i++) {
            Organism org = organisms.get(i);
            if (org.getIsAlive()) {
                org.takeTurn();

            }

        }
        this.updateWorld();
    }

    /**
     * Clears the ArrayList<Organism> organisms, and only adds Organisms that
     * are alive.
     */
    public void updateWorld() {

        organisms.clear();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (cellGrid[i][j].getOrganism() != null
                        && cellGrid[i][j].getOrganism().getIsAlive()) {
                    organisms.add(cellGrid[i][j].getOrganism());
                }
            }
        }
    }

    /**
     * Loads the Organisms from the double linked list into the Cells in World.
     * If the loading Organism is null then it removes the current Organism in
     * the World's Cell.
     * 
     * @param list
     *            DoubleLinkedList<Cell> the loaded list from inputStream
     */
    public void loadLinkedList(DoubleLinkedList<Cell> list) {
        Iterator<Cell> it = list.iterator();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (it.hasNext()) {

                    Cell loadedCell = it.next();
                    if (loadedCell.getOrganism() != null) {
                        loadedCell.getOrganism().setCell(cellGrid[i][j]);
                        cellGrid[i][j].addLife(loadedCell.getOrganism());

                    } else {
                        cellGrid[i][j].removeOrganism();
                    }

                }

            }
        }
        updateWorld();
    }

    /**
     * Gets the row of this World.
     * 
     * @return int the row
     */
    public int getRowCount() {
        return this.row;
    }

    /**
     * Gets the column of this World.
     * 
     * @return int the column
     */
    public int getColumnCount() {
        return this.column;
    }

}
