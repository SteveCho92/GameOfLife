package ca.bcit.comp2526.a2c;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Cell can hold an Organism that can be a Plant or Herbivore.
 * 
 * @author Steve Cho
 * @version 2017
 */
public class Cell extends JPanel {

    /**
     * Number of adjacent cells in corner cell.
     */
    public static final int CORNERS = 3;

    /**
     * Number of adjacent cells along side cells.
     */
    public static final int SIDES = 5;

    /**
     * Number of adjacent cells along regular cells.
     */
    public static final int OTHERS = 8;

    private static final long serialVersionUID = -1368246137068673579L;
    private World world;
    private int row;
    private int column;
    private int rowStarter;
    private int rowEnder;
    private int colStarter;
    private int colEnder;
    private boolean hasOrganism;
    private Organism organism;

    /**
     * Constructs a Cell with coordinates for location within the World.
     * 
     * @param world
     *            World the world that this Cell is in
     * @param row
     *            int the Y coordinate of this Cell in the world
     * @param column
     *            int the X coordinate of this cell in the world.
     */
    public Cell(final World world, final int row, final int column) {
        this.world = world;
        this.row = row;
        this.column = column;
        this.init();
        this.hasOrganism = false;

    }

    /**
     * Adds an Organism to this Cell.
     * 
     * @param org
     *            Organism to add
     */
    public void addLife(Organism org) {
        if (org != null) {
            this.organism = org;
            hasOrganism = true;
            org.init();
        }
        // else {
        // this.organism = null;
        // hasOrganism = false;
        // this.init();
        // }
    }

    /**
     * Removes the Organism from this Cell and sets Cell background color white.
     */
    public void removeOrganism() {
        if (this.organism != null) {
            this.organism = null;
            hasOrganism = false;
            this.init();
        }
    }

    /**
     * Sets the background color of the Cell to white and border to black.
     */
    public void init() {
        // if(organism != null) {
        // organism.init();
        // }
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    /**
     * Gets the location of this Cell.
     * 
     * @return Point the Point with coordinates
     */
    public Point getLocation() {
        Point p = new Point(column, row);
        return p;
    }

    /**
     * Gets the adjacent Cells of this Cell in an array.
     * 
     * @return Cell[] the array of adjacent Cells
     */
    public Cell[] getAdjacentCells() {
        Cell[] adjacentCell = new Cell[0];
        int y = this.getLocation().y;
        int x = this.getLocation().x;
        int gridEdge = world.getColumnCount() - 1;

        // case for when Cell is at a corner
        if (x == 0 && y == 0 || x == 0 && y == gridEdge
                || x == gridEdge && y == 0 || x == gridEdge && y == gridEdge) {
            adjacentCell = new Cell[CORNERS];
            this.setCornerBounds();
            this.findAdjacentCells(adjacentCell);

        }

        // case for when Cell is along the sides
        if (x == 0 && y > 0 && y < gridEdge || x > 0 && x < gridEdge && y == 0
                || x == gridEdge && y > 0 && y < gridEdge
                || x > 0 && x < gridEdge && y == gridEdge) {
            adjacentCell = new Cell[SIDES];
            this.setSideBounds();
            this.findAdjacentCells(adjacentCell);

        }

        // case for Cells anywhere else but corners and sides
        if (x > 0 && x < gridEdge && y > 0 && y < gridEdge) {
            adjacentCell = new Cell[OTHERS];
            this.setOtherBounds();
            this.findAdjacentCells(adjacentCell);
        }
        return adjacentCell;
    }

    /**
     * Sets the bounds for a Cell that is not in a corner or side of the grid.
     */
    public void setOtherBounds() {
        int colEdge = world.getColumnCount() - 1;
        int rowEdge = world.getRowCount() - 1;

        if (column > 0 && column < colEdge && row > 0 && row < rowEdge) {
            rowStarter = -1;
            rowEnder = 1;
            colStarter = -1;
            colEnder = 1;
        }
    }

    /**
     * Sets the bounds for a Cell in a corner of the grid.
     */
    public void setCornerBounds() {
        int columnEdge = world.getColumnCount() - 1;
        int rowEdge = world.getRowCount() - 1;

        if (row == 0 && column == 0) {
            rowStarter = 0;
            rowEnder = 1;
            colStarter = 0;
            colEnder = 1;
        } else if (row == 0 && column == columnEdge) {
            rowStarter = 0;
            rowEnder = 1;
            colStarter = -1;
            colEnder = 0;
        } else if (row == rowEdge && column == 0) {
            rowStarter = -1;
            rowEnder = 0;
            colStarter = 0;
            colEnder = 1;
        } else if (row == rowEdge && column == columnEdge) {
            rowStarter = -1;
            rowEnder = 0;
            colStarter = -1;
            colEnder = 0;
        }
    }

    /**
     * Sets the side bounds for a Cell on the sides of the grid.
     */
    public void setSideBounds() {
        int rowEdge = world.getRowCount() - 1;
        int colEdge = world.getColumnCount() - 1;

        if (column == 0 && row > 0 && row < rowEdge) {
            rowStarter = -1;
            rowEnder = 1;
            colStarter = 0;
            colEnder = 1;
        } else if (column > 0 && column < colEdge && row == 0) {
            rowStarter = 0;
            rowEnder = 1;
            colStarter = -1;
            colEnder = 1;
        } else if (column == colEdge && row > 0 && row < rowEdge) {
            rowStarter = -1;
            rowEnder = 1;
            colStarter = -1;
            colEnder = 0;
        } else if (column > 0 && column < colEdge && row == rowEdge) {
            rowStarter = -1;
            rowEnder = 0;
            colStarter = -1;
            colEnder = 1;
        }
    }

    /**
     * Inserts adjacent Cells in an array by looping through the bounds
     * determined by this Cell position.
     * 
     * @param adjacentCell
     *            the possible array length of adjacent Cells for this Cell
     */
    public void findAdjacentCells(final Cell[] adjacentCell) {
        int k = 0;

        for (int i = row + rowStarter; i <= row + rowEnder; i++) {
            for (int j = column + colStarter; j <= column + colEnder; j++) {
                if (!(i == row && j == column)) {
                    adjacentCell[k] = world.getCellAt(i, j);
                    k++;
                }
            }
        }
    }

    /**
     * Checks if this Cell has an Organism.
     * 
     * @return boolean for having Organism
     */
    public boolean hasOrganism() {
        return hasOrganism;
    }

    /**
     * Gets the Organism in this Cell.
     * 
     * @return Organism the Organism in this Cell
     */
    public Organism getOrganism() {
        return this.organism;

    }
}
