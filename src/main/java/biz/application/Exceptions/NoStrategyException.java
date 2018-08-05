package biz.application.Exceptions;

public class NoStrategyException extends Exception {
    public NoStrategyException() {
        super(ExceptionMessage.NO_STRATEGY_MESSAGE);
    }
}
