package biz.application.Invests;
import biz.application.Exceptions.InsufficientInvestmentAmountException;
import biz.application.Exceptions.NoRequiredFundsException;
import biz.application.Exceptions.BadStrategyException;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import org.decimal4j.util.DoubleRounder;
import org.springframework.util.CollectionUtils;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class InvestStrategy {

    private  Map<FundType, Integer> configuration;
    private static int SUFFICIENT_INVESTMENT_AMOUNT = 1;
    private static final int NUMBER_OF_DECIMAL_PLACES = 2;
    private static final int FIRST_ELEMENT_OF_LIST = 0;
    private static int DEFAULT_STRATEGY_TOTAL_INVESTMENT_PERCENT_VALUE = 100;

    public void useStrategy(Map<FundType, Integer> configuration) throws BadStrategyException {
        if(CollectionUtils.isEmpty(configuration)) {
            throw new BadStrategyException();
        }

        int sum = configuration.values().stream().mapToInt(Number::intValue).sum();
        if(sum != DEFAULT_STRATEGY_TOTAL_INVESTMENT_PERCENT_VALUE) {
            throw new BadStrategyException();
        }

        this.configuration = configuration;
    }

    private Map<FundType, Integer> getConfiguration() {
            return configuration;
    }

    public InvestmentResult invest(int amount, List<Fund> funds) throws NoRequiredFundsException, InsufficientInvestmentAmountException {
        if(amount < SUFFICIENT_INVESTMENT_AMOUNT) {
            throw new InsufficientInvestmentAmountException();
        }
        checkRequiredFund(funds);
        List<FundResult> result = Arrays.stream(FundType.values()).map(type -> getFundsByType(type, funds)).map(
                fundsByType -> getInvestResultForFundType(fundsByType, fundsByType.get(FIRST_ELEMENT_OF_LIST).getFoundType(), amount)
        ).flatMap(el -> el.stream()).collect(Collectors.toList());

        return getResultOfInvestment(amount, result);
    }


    private void checkRequiredFund(List<Fund> funds) throws NoRequiredFundsException {
        if(funds == null){
            throw new NoRequiredFundsException();
        }
        for(FundType fundType : FundType.values()) {
            boolean match = funds.stream().anyMatch(fund -> fund.getFoundType().equals(fundType));
            if (!match) {
                throw new NoRequiredFundsException();
            }
        }
    }

    private InvestmentResult getResultOfInvestment(Integer amount, List<FundResult> result) {
        Integer rest = amount -  result.stream().mapToInt(FundResult::getValue).sum();
        return new InvestmentResult(result, amount, rest);
    }

    private List<Fund> getFundsByType(FundType type, List<Fund> funds){
        return funds.stream().filter(fund -> fund.getFoundType().equals(type)
        ).collect(Collectors.toList());
    }

    private List<FundResult> getInvestResultForFundType(List<Fund> funds, FundType type, Integer amount) {
        List<FundResult> result = new ArrayList<>();
        Integer percentForFundType = getConfiguration().get(type);
        double percentPerFund = percentForFundType.doubleValue() / funds.size();
        percentPerFund = DoubleRounder.round(percentPerFund, NUMBER_OF_DECIMAL_PLACES, RoundingMode.FLOOR);
        double percentRest = percentForFundType - (percentPerFund * funds.size());
        percentRest = DoubleRounder.round(percentRest, NUMBER_OF_DECIMAL_PLACES, RoundingMode.UP);
        double fundValue = amount * (percentPerFund / 100);
        double restValue = amount * ((percentPerFund + percentRest) / 100);

        for(int i=0; i < funds.size(); i++) {
            if (i == FIRST_ELEMENT_OF_LIST) {
                result.add(new FundResult(funds.get(i), percentRest + percentPerFund, (int) Math.floor(restValue)));
            } else {
                result.add(new FundResult(funds.get(i), percentPerFund, (int) Math.floor(fundValue)));
            }
        }
        return result;
    }


    public void setSufficientInvestmentAmount(int value) {
        SUFFICIENT_INVESTMENT_AMOUNT = value;
    }

    public static void setDefaultStrategyTotalInvestmentPercentValue(int value) {
        DEFAULT_STRATEGY_TOTAL_INVESTMENT_PERCENT_VALUE = value;
    }
}
