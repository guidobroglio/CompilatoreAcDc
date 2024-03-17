package exception‎;

public class SyntacticException extends Exception {

    public SyntacticException(String string) {
        super(string);
    }

    public SyntacticException(String string, Throwable cause) {
        super(string, cause);
    }
}