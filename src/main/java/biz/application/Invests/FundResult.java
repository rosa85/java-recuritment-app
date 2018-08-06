package biz.application.Invests;

import biz.application.Funds.Fund;

public class FundResult {

    private Fund fund;
    private double percent;
    private int value;

    public FundResult(Fund fund, double percent, int value) {
        this.fund = fund;
        this.percent = percent;
        this.value = value;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundResult that = (FundResult) o;

        if (Double.compare(that.percent, percent) != 0) return false;
        if (value != that.value) return false;
        return fund != null ? fund.equals(that.fund) : that.fund == null;
    }

    @Override
    public String toString() {
        return "FundResult{" +
                "fund=" + fund.getName() +
                ", percent=" + percent +
                ", value=" + value +
                '}';
    }
}
