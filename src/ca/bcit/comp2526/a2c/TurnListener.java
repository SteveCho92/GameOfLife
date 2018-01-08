package ca.bcit.comp2526.a2c;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Allows a mouse clicking event to make GameFrame take a turn.
 * 
 * @author Steve Cho
 * @version 2017
 */
public class TurnListener extends MouseAdapter {
    private GameFrame gameFrame;

    /**
     * Constructs TurnLisenter.
     * 
     * @param gameFrame
     *            to act on
     */
    
    public TurnListener(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    /**
     * Clicking the mouse makes gameFrame take a turn.
     */
    public void mouseClicked(MouseEvent e) {

        gameFrame.takeTurn();
    }
}
