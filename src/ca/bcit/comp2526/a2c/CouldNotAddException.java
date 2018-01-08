package ca.bcit.comp2526.a2c;

/**
 * Exception for adding nulls on a list.
 * 
 * @author Steve Cho
 * @version 2017
 *
 */
public class CouldNotAddException extends Exception {

    private static final long serialVersionUID = -6409884344476848307L;

    /**
     * Constructs CouldNotAddException and displays error mesasge.
     * 
     * @param msg
     *            message for the exception
     */
    public CouldNotAddException(String msg) {
        System.out.println(msg);
    }
}
