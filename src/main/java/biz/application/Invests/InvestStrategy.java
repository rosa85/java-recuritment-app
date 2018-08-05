package biz.application.Invests;
import biz.application.Exceptions.InsufficientInvestmentAmountException;
import biz.application.Exceptions.NoRequiredFundsException;
import biz.application.Exceptions.NoStrategyException;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import org.decimal4j.util.DoubleRounder;
import org.springframework.util.CollectionUtils;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class InvestStrategy {

    private  Map<FundType, Integer> configuration;
    private int SUFFICIENT_INVESTMENT_AMOUNT = 1;

    public void useStrategy(Map<FundType, Integer> configuration) throws NoStrategyException {
        if(CollectionUtils.isEmpty(configuration))
            throw new NoStrategyException();
        this.configuration = configuration;
    }


    private Map<FundType, Integer> getConfiguration() {
            return configuration;
    }

    public InvestmentResult invest(int amount, List<Fund> funds) throws NoRequiredFundsException, InsufficientInvestmentAmountException {
        if(amount <= SUFFICIENT_INVESTMENT_AMOUNT) {
            throw new InsufficientInvestmentAmountException();
        }
        List<FundResult> result = new ArrayList<>();

        for(FundType fundType : FundType.values()) {
            List<Fund> fundsByType = getFundsByType(fundType, funds);

            if (CollectionUtils.isEmpty(fundsByType)) {
                throw new NoRequiredFundsException();
            }

            getResultByFundType(fundsByType, fundType, result, amount);
        }

        return getResultOfInvestment(amount, result);
    }

    private InvestmentResult getResultOfInvestment(Integer amount, List<FundResult> result) {
        Integer rest = amount -  result.stream().mapToInt(el->el.getValue()).sum();
        return new InvestmentResult(result, amount, rest);
    }

    private List<Fund> getFundsByType(FundType type, List<Fund> funds){
        if(funds == null){
            return Collections.EMPTY_LIST;
        }
        return funds.stream().filter(fund -> {
            return fund.getFoundType().equals(type);
        }).collect(Collectors.toList());
    }

    private void getResultByFundType(List<Fund> funds, FundType type, List<FundResult> result, Integer amount) {
        Integer percentageByType = getConfiguration().get(type);
        double percentageByFund = percentageByType.doubleValue() / funds.size();
        percentageByFund = DoubleRounder.round(percentageByFund, 2, RoundingMode.FLOOR);
        double percentageByFundRest = percentageByType - (percentageByFund * funds.size());
        double valueByFund = amount * (percentageByFund / 100);
        double valueByFundRest = amount * ((percentageByFund + percentageByFundRest) / 100);

        for(int i=0; i < funds.size(); i++) {
            if (i == 0) {
                result.add(new FundResult(funds.get(i), percentageByFundRest + percentageByFund, (int) Math.floor(valueByFundRest)));
            } else {
                result.add(new FundResult(funds.get(i), percentageByFund, (int) Math.floor(valueByFund)));
            }
        }

    }

}
