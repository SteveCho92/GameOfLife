package ca.bcit.comp2526.a2c;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * GameFrame creates the user interface of the game.
 * 
 * @author Steve Cho
 * @version 2017
 */
public class GameFrame extends JFrame {

    private static final long serialVersionUID = 6646638598275200418L;
    private final World world;
    private JPanel cellGrid;

    /**
     * Constructs a GameFrame.
     * 
     * @param w
     *            World to initialize in the game
     */
    public GameFrame(final World w) {
        world = w;
    }

    /**
     * Creates the layout of the GameFrame and adds the World, save and load
     * button to it.
     */
    public void init() {
        setTitle("Assignment 2C");
        // boxlayout for the frame
        BoxLayout box = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
        setLayout(box);

        // grid layout for the actual game
        GridLayout grid = new GridLayout(world.getRowCount(),
                world.getColumnCount());
        
        // Jpanel with grid layout to put in frame's boxlayout
        cellGrid = new JPanel(grid);
        add(cellGrid);
        
        //JPanel with Boxlayout with buttons inside
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.LINE_AXIS));
        JButton save = new JButton("save");
        JButton load = new JButton("load");
        menu.add(save);
        menu.add(load);
        
        add(menu);
        addMouseListener(new TurnListener(this));


        DoubleLinkedList<Cell> linkedList = new DoubleLinkedList<Cell>();

        for (int row = 0; row < world.getRowCount(); row++) {
            for (int col = 0; col < world.getColumnCount(); col++) {
                try {
                    linkedList.addToEnd(world.getCellAt(row, col));
                } catch (CouldNotAddException e) {
                    e.printStackTrace();
                }
                cellGrid.add(world.getCellAt(row, col));
            }
        }

        // anonymous class for save button action, allows to save state of the
        // game
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {

                    File workingDirectory = new File(
                            System.getProperty("user.dir"));
                    JFileChooser saveFile = new JFileChooser();
                    saveFile.setCurrentDirectory(workingDirectory);
                    saveFile.showSaveDialog(save);
                    File fileName = saveFile.getSelectedFile();

                    FileOutputStream fileOut = new FileOutputStream(fileName);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(linkedList);
                    out.close();
                    fileOut.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });

        // anonymous class for load button action, allows to load a new state of
        // a game
        load.addActionListener(new ActionListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent arg0) {
                DoubleLinkedList<Cell> linkedList = null;
                try {
                    File workingDirectory = new File(
                            System.getProperty("user.dir"));
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(workingDirectory);
                    chooser.showOpenDialog(load);
                    File file = chooser.getSelectedFile();

                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    linkedList = (DoubleLinkedList<Cell>) in.readObject();
                    in.close();
                    fileIn.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                world.loadLinkedList(linkedList);

            }
        });

    }

    /**
     * Takes a turn and repaints the GameFrame.
     */
    public void takeTurn() {
        world.takeTurn();
        repaint();
    }
}
