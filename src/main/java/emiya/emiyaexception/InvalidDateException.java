package emiya.emiyaexception;

/**
 * An exception that is thrown when the user input contains an invalid date.
 */
public class InvalidDateException extends EmiyaException {
    public InvalidDateException() {
        super("Please enter a valid date!\n");
    }
}
