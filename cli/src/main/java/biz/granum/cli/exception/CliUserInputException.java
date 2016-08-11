
package biz.granum.cli.exception;


public class CliUserInputException extends RuntimeException {
    public CliUserInputException() {
    }

    public CliUserInputException(String message) {
        super(message);
    }

    public CliUserInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public CliUserInputException(Throwable cause) {
        super(cause);
    }
}
 
