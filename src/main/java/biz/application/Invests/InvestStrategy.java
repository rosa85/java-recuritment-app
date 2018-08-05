package biz.application.Invests;
import biz.application.Exception.NoRequiredFunds;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import org.decimal4j.util.DoubleRounder;
import org.springframework.util.CollectionUtils;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static biz.application.Exception.ExceptionMessage.NO_REQUIRED_FUNDS_EXCEPTION;


public class InvestStrategy {

    private final Map<FundType, Integer> configuration;
    private final Style style;

    public InvestStrategy(Map<FundType, Integer> configuration, Style style) {
        this.configuration = configuration;
        this.style = style;
    }


    private Map<FundType, Integer> getConfiguration() {
        if(!CollectionUtils.isEmpty(configuration))
            return configuration;
        return Collections.EMPTY_MAP;
    }

    public InvestmentResult invest(Integer amount, Set<Fund> funds) throws NoRequiredFunds {
        Map<Fund, Integer> result = new HashMap<>();

        for(FundType fundType : FundType.values()) {
            List<Fund> fundsByType = getFundsByType(fundType, funds);

            if (CollectionUtils.isEmpty(fundsByType)) {
                throw new NoRequiredFunds(NO_REQUIRED_FUNDS_EXCEPTION);
            }

            getResultByFundType(fundsByType, fundType, result, amount);
        }

        return getResultOfInvestment(amount, result);
    }

    private InvestmentResult getResultOfInvestment(Integer amount, Map<Fund, Integer> result) {
        Integer rest = amount -  result.values().stream().mapToInt(Number::intValue).sum();
        return new InvestmentResult(result, amount, rest);
    }

    private List<Fund> getFundsByType(FundType type, Set<Fund> funds){
        return funds.stream().filter(fund -> {
            return fund.getFoundType().equals(type);
        }).collect(Collectors.toList());
    }

    private void getResultByFundType(List<Fund> funds, FundType type, Map<Fund, Integer> result, Integer amount) {
        Integer percentageByType = getConfiguration().get(type);
        double percentageByFund = percentageByType.doubleValue() / funds.size();
        percentageByFund = DoubleRounder.round(percentageByFund, 2, RoundingMode.FLOOR);
        double percentageByFundRest = percentageByType - (percentageByFund * funds.size());
        double valueByFund = amount * (percentageByFund / 100);
        double valueByFundRest = amount * ((percentageByFund + percentageByFundRest) / 100);

        IntStream.range(0, funds.size()).forEach(i -> {
            if (i == funds.size()-1) {
                result.put(funds.get(i), (int) Math.floor(valueByFundRest));
            }
            else {
                result.put(funds.get(i), (int) Math.floor(valueByFund));
            }
        });
    }

}
