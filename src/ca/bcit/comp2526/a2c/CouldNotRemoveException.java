package ca.bcit.comp2526.a2c;

/**
 * Exception for not elements to remove.
 * 
 * @author Steve Cho
 * @version 2017
 *
 */
public class CouldNotRemoveException extends Exception {

    private static final long serialVersionUID = 13027921234525541L;

    /**
     * Constructs CouldNotRemoveException with an error message.
     * 
     * @param msg
     *            message for the exception
     */
    public CouldNotRemoveException(String msg) {
        System.out.println(msg);
    }

}
