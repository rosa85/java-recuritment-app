package com.blueservices.jtest;

import biz.application.Exceptions.InsufficientInvestmentAmountException;
import biz.application.Exceptions.NoRequiredFundsException;
import biz.application.Exceptions.BadStrategyException;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import biz.application.Invests.InvestStrategy;
import biz.application.Invests.InvestmentResult;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class StrategyTest {

    private InvestStrategy aggressiveStrategy;


    @Before
    public void setUp() throws BadStrategyException {

       Map<FundType, Integer> configuration = AggressiveStrategyTest.getAggressiveConfiguration();
       aggressiveStrategy = new InvestStrategy();
       aggressiveStrategy.useStrategy(configuration);

    }

    @Test(expected=BadStrategyException.class)
    public void shouldThrowExceptionBecauseNoConfigurationStrategyFunds() throws Exception {
        // given //when //then
        aggressiveStrategy.useStrategy(null);
    }

    @Test(expected=NoRequiredFundsException.class)
    public void shouldThrowExceptionBecauseNoRequiredFunds() throws Exception {
        // given
        List<Fund> funds = new ArrayList<>();
        Fund fund1 = new Fund(1, "Fundusz Polski 1", FundType.POLISH);
        funds.add(fund1);
        Fund fund2 = new Fund(2, "Fundusz Polski 2", FundType.POLISH);
        funds.add(fund2);

        //when //then
        InvestmentResult result = aggressiveStrategy.invest(10001, funds);
        
    }

    @Test(expected=NoRequiredFundsException.class)
    public void shouldThrowExceptionBecauseFundsNull() throws Exception {
        // given when then
        InvestmentResult result = aggressiveStrategy.invest(1000, null);
    }

    @Test(expected = InsufficientInvestmentAmountException.class)
    public void shouldThrowExceptionBecauseAmountIsZero() throws Exception {
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
        InvestmentResult result = aggressiveStrategy.invest(0, funds);
    }
}
