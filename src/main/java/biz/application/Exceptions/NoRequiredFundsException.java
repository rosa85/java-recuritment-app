package biz.application.Exceptions;

public class NoRequiredFundsException extends Exception {

    public NoRequiredFundsException() {
        super(ExceptionMessage.NO_REQUIRED_FUNDS_EXCEPTION_MESSAGE);
    }
}
