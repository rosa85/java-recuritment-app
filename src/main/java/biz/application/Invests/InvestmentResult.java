package biz.application.Invests;

import java.util.List;

public class InvestmentResult {

    private List<FundResult> result;

    private Integer amountOfInvestment;

    private Integer rest;


    public InvestmentResult(List<FundResult> result, Integer amountOfInvestment, Integer rest) {
        this.result = result;
        this.amountOfInvestment = amountOfInvestment;
        this.rest = rest;
    }

    public List<FundResult> getResult() {
        return result;
    }

    public Integer getAmountOfInvestment() {
        return amountOfInvestment;
    }

    public Integer getRest() {
        return rest;
    }
}
