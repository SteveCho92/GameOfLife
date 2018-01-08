package ca.bcit.comp2526.a2c;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Organism populates a Cell and it can move, eat and reproduce depending on
 * varying conditions of the type of Organism.
 * 
 * @author Steve Cho
 * @version 2017
 */
@SuppressWarnings("serial")
public abstract class Organism extends JPanel {

    private Cell cell;
    private boolean foodFound;
    private boolean isAlive;
    private int life;
    private int lifeLimit;
    private int reproduceSpace;
    private int reproducePartner;
    private int reproduceFood;

    /**
     * Constructs the Organism, sets the Cell it is in, and set it alive.
     * 
     * @param location
     *            Cell that this Organism is in
     */
    public Organism(final Cell location) {
        this.cell = location;
        this.isAlive = true;
        this.life = 0;
        this.foodFound = false;

    }
    /**
     * Returns the Cell for this Organism.
     * 
     * @return Cell the Cell
     */
    public Cell getCell() {
        return cell;
    }
    /**
     * Sets the number of food needed for reproducing.
     * 
     * @param food
     *            int for number of food needed
     */
    public void setReproduceFood(int food) {
        if (food > 0) {
            this.reproduceFood = food;
        }
    }

    /**
     * Sets the number of empty Cells needed for reproducing.
     * 
     * @param space
     *            int minimum space needed
     */
    public void setReproduceSpace(int space) {
        if (space > 0) {
            this.reproduceSpace = space;
        }
    }
    /**
     * Sets the number of reproduce partner needed for reproducing.
     * 
     * @param partner
     *            int minimum partners needed
     */
    public void setReproducePartner(int partner) {
        if (partner > 0) {
            this.reproducePartner = partner;
        }
    }
    /**
     * Sets the lifeLimit of this Organism.
     * 
     * @param limit
     *            int life limit
     */
    public void setLifeLimit(int limit) {
        if (limit > 0) {
            this.lifeLimit = limit;
        }
    }

    /**
     * Checks if this Organism is alive or not.
     * 
     * @return boolean for being alive
     */
    public boolean getIsAlive() {
        return this.isAlive;
    }

    /**
     * Sets this Organism's Cell as the next Cell.
     * 
     * @param nextCell
     *            the new Cell of this Organism
     */
    public void setCell(final Cell nextCell) {
        if (nextCell != null && this.cell != nextCell) {
            this.cell = nextCell;
        }
    }
    /**
     * Moves the Organism to the next Cell.
     * 
     * @param nextCell
     *            Cell to move into
     */
    public void move(final Cell nextCell) {
        if (nextCell != null) {
            if (foodFound) {
                this.eat(nextCell);
            }

            this.cell.removeOrganism();
            this.setCell(nextCell);
            nextCell.addLife(this);

        }
    }

    /**
     * Finds empty Cells.
     * 
     * @param adjacentCell
     *            the Cell[] to find empty Cells
     * @return ArrayList<Cell> empty Cells
     */
    public ArrayList<Cell> findEmptyCells(final Cell[] adjacentCell) {
        ArrayList<Cell> emptyCells = new ArrayList<Cell>();
        for (int i = 0; i < adjacentCell.length; i++) {
            if (adjacentCell[i].getOrganism() == null) {
                emptyCells.add(adjacentCell[i]);
            }

        }
        return emptyCells;
    }

    /**
     * Chooses the next Cell to move into. It prioritizes a Cell with edible
     * food for this Organism.
     * 
     * @return Cell to move to
     */
    public Cell choosePosition() {
        Cell[] adjacentCell = this.cell.getAdjacentCells();
        Cell nextCell = null;
        ArrayList<Cell> edibles = findFood(adjacentCell);
        ArrayList<Cell> emptyCells = findEmptyCells(adjacentCell);
        if (!edibles.isEmpty()) {
            foodFound = true;
            int rand = RandomGenerator.nextNumber(edibles.size());
            nextCell = edibles.get(rand);
        } else if (!emptyCells.isEmpty() && edibles.isEmpty()) {
            int rand = RandomGenerator.nextNumber(emptyCells.size());
            nextCell = emptyCells.get(rand);
        }

        return nextCell;
    }
    /**
     * Finds food the Organism can eat.
     * 
     * @param adjacentCell
     *            Cells to find food in
     * @return ArrayList<Cell> Cells containing the food Organism can eat
     */
    public abstract ArrayList<Cell> findFood(Cell[] adjacentCell);

    /**
     * Removes the organism.
     */
    public void dies() {
        this.isAlive = false;
        this.cell.removeOrganism();
    }

    /**
     * Eats the Plant in the Cell.
     * 
     * @param toEat
     *            Cell that contains Organism to be eatten
     */
    public void eat(final Cell toEat) {
        toEat.getOrganism().dies();
        this.life = 0;
        this.foodFound = false;

    }

    /**
     * Increases the life of this Organism.
     */
    public void addLife() {
        this.life++;
    }

    /**
     * Sets the background color depending on type of Organism.
     */
    public abstract void init();

    /**
     * Takes a turn for this organism.
     */
    public void lifeSpan() {

        if (life >= this.lifeLimit) {
            this.dies();
        }
    }

    /**
     * Finds all the possible mates in the adjacent Cell.
     * 
     * @param adjacentCells
     *            Cells to check for mates
     * @return ArrayList<Cell> array list of Cells with mates
     */
    public ArrayList<Cell> findMates(Cell[] adjacentCells) {
        ArrayList<Cell> mates = new ArrayList<Cell>();
        for (Cell mate : adjacentCells) {

            if (foundMate(mate)) {
                mates.add(mate);
            }
        }
        return mates;
    }

    /**
     * Finds possible mate for this Organism in the Cell parameter.
     * 
     * @param mate
     *            the Cell to find a mate
     * @return boolean for mate found
     */
    public abstract boolean foundMate(Cell mate);

    /**
     * Checks the reproduction conditions and gives life to the randomly chosen
     * next Cell.
     */
    public void reproduce() {
        Cell[] adjacentCells = this.cell.getAdjacentCells();
        ArrayList<Cell> emptyCells = this.findEmptyCells(adjacentCells);
        ArrayList<Cell> mates = this.findMates(adjacentCells);
        ArrayList<Cell> food = this.findFood(adjacentCells);

        if (mates.size() >= this.reproducePartner
                && emptyCells.size() >= this.reproduceSpace
                && food.size() >= this.reproduceFood) {

            int rand = RandomGenerator.nextNumber(emptyCells.size());
            Cell nextCell = emptyCells.get(rand);
            this.giveLife(nextCell);

        }
    }

    /**
     * Create a new Organism in the Cell parameter.
     * 
     * @param newCell
     *            Cell to create new Organism in
     */
    public abstract void giveLife(Cell newCell);

    /**
     * Takes a turn for this Organism. It moves if it's an animal, and all
     * Organisms reproduce after.
     */
    public void takeTurn() {
        this.addLife();
        if (isAlive) {
            Cell nextCell = this.choosePosition();
            if (this instanceof Animal) {
                this.move(nextCell);
                this.lifeSpan();
            }
            this.reproduce();
        }
    }

}
