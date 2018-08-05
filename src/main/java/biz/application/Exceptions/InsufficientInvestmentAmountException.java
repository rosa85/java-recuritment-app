package biz.application.Exceptions;

public class InsufficientInvestmentAmountException extends Exception{

    public InsufficientInvestmentAmountException() {
        super(ExceptionMessage.INSUFFICIENT_INVESTMENT_AMOUNT_MESSAGE);
    }
}
