package com.blueservices.jtest;

import biz.application.Exceptions.BadStrategyException;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import biz.application.Invests.FundResult;
import biz.application.Invests.InvestStrategy;
import biz.application.Invests.InvestmentResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class BalancedStrategyTest {

    private InvestStrategy balancedStrategy;

    @Before
    public void setUp() throws BadStrategyException {
        Map<FundType, Integer> balancedConfiguration = new HashMap<>();
        balancedConfiguration.put(FundType.POLISH, 30);
        balancedConfiguration.put(FundType.FOREIGN, 60);
        balancedConfiguration.put(FundType.CASH, 10);
        balancedStrategy = new InvestStrategy(balancedConfiguration);
    }

    @Test
    public void amountOfInvestmentForFundTypeIsCorrectDividedForStrategy() throws Exception {
        // given
        List<Fund> funds = new ArrayList<>();
        Fund fund1 = new Fund(1, "Fundusz Polski 1", FundType.POLISH);
        funds.add(fund1);
        Fund fund2 = new Fund(2, "Fundusz Polski 2", FundType.POLISH);
        funds.add(fund2);
        Fund fund3 = new Fund(3, "Fundusz Zagraniczny 1", FundType.FOREIGN);
        funds.add(fund3);
        Fund fund4 = new Fund(4, "Fundusz Zagraniczny 2", FundType.FOREIGN);
        funds.add(fund4);
        Fund fund5 = new Fund(5, "Fundusz Zagraniczny 3", FundType.FOREIGN);
        funds.add(fund5);
        Fund fund6 = new Fund(6, "Fundusz Pieniężny 1", FundType.CASH);
        funds.add(fund6);

        //when
        InvestmentResult result = balancedStrategy.invest(10000, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 15.0, 1500));
        expected.add(new FundResult(fund2, 15.0, 1500));
        expected.add(new FundResult(fund3, 20.0, 2000));
        expected.add(new FundResult(fund4, 20.0, 2000));
        expected.add(new FundResult(fund5, 20.0, 2000));
        expected.add(new FundResult(fund6, 10.0, 1000));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(0));
    }


    @Test
    public void shouldReturnRestForUnDividedAmount() throws Exception {
        // given
        List<Fund> funds = new ArrayList<>();
        Fund fund1 = new Fund(1, "Fundusz Polski 1", FundType.POLISH);
        funds.add(fund1);
        Fund fund2 = new Fund(2, "Fundusz Polski 2", FundType.POLISH);
        funds.add(fund2);
        Fund fund3 = new Fund(3, "Fundusz Zagraniczny 1", FundType.FOREIGN);
        funds.add(fund3);
        Fund fund4 = new Fund(4, "Fundusz Zagraniczny 2", FundType.FOREIGN);
        funds.add(fund4);
        Fund fund5 = new Fund(5, "Fundusz Zagraniczny 3", FundType.FOREIGN);
        funds.add(fund5);
        Fund fund6 = new Fund(6, "Fundusz Pieniężny 1", FundType.CASH);
        funds.add(fund6);

        //when
        InvestmentResult result = balancedStrategy.invest(10001, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 15.0, 1500));
        expected.add(new FundResult(fund2, 15.0, 1500));
        expected.add(new FundResult(fund3, 20.0, 2000));
        expected.add(new FundResult(fund4, 20.0, 2000));
        expected.add(new FundResult(fund5, 20.0, 2000));
        expected.add(new FundResult(fund6, 10.0, 1000));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(1));

    }

    @Test
    public void shouldCorrectlyDivideFloatingPointValues() throws Exception {
        // given
        List<Fund> funds = new ArrayList<>();
        Fund fund1 = new Fund(1, "Fundusz Polski 1", FundType.POLISH);
        funds.add(fund1);
        Fund fund2 = new Fund(2, "Fundusz Polski 2", FundType.POLISH);
        funds.add(fund2);
        Fund fund3 = new Fund(3, "Fundusz Polski 3", FundType.POLISH);
        funds.add(fund3);
        Fund fund4 = new Fund(4, "Fundusz Zagraniczny 2", FundType.FOREIGN);
        funds.add(fund4);
        Fund fund5 = new Fund(5, "Fundusz Zagraniczny 3", FundType.FOREIGN);
        funds.add(fund5);
        Fund fund6 = new Fund(6, "Fundusz Pieniężny 1", FundType.CASH);
        funds.add(fund6);

        //when
        InvestmentResult result = balancedStrategy.invest(10000, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 10.0, 1000));
        expected.add(new FundResult(fund2, 10.0, 1000));
        expected.add(new FundResult(fund3, 10.0, 1000));
        expected.add(new FundResult(fund4, 30.0, 3000));
        expected.add(new FundResult(fund5, 30.0, 3000));
        expected.add(new FundResult(fund6, 10.0, 1000));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(0));

    }

}
