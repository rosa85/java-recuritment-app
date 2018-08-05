package biz.application.Invests;

import biz.application.Funds.Fund;

import java.util.Map;

public class InvestmentResult {

    private Map<Fund, Integer> result;

    private Integer amountOfInvestment;

    private Integer rest;

    public InvestmentResult(Map<Fund, Integer> result, Integer amountOfInvestment, Integer rest) {
        this.result = result;
        this.amountOfInvestment = amountOfInvestment;
        this.rest = rest;
    }

    public Map<Fund, Integer> getResult() {
        return result;
    }

    public Integer getAmountOfInvestment() {
        return amountOfInvestment;
    }

    public Integer getRest() {
        return rest;
    }
}
