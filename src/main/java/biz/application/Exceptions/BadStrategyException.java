package biz.application.Exceptions;

public class BadStrategyException extends Exception {
    public BadStrategyException() {
        super(ExceptionMessage.BAD_STRATEGY_MESSAGE);
    }
}
