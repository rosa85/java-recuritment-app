package com.blueservices.jtest;

import biz.application.Exception.NoRequiredFunds;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import biz.application.Invests.InvestStrategy;
import biz.application.Invests.InvestmentResult;
import biz.application.Invests.Style;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class StrategyTest {

    private InvestStrategy aggressiveStrategy;


    @Before
    public void setUp() {

       Map<FundType, Integer> aggressiveConfiguration = new HashMap<>();
       aggressiveConfiguration.put(FundType.POLISH, 40);
       aggressiveConfiguration.put(FundType.FOREIGN, 20);
       aggressiveConfiguration.put(FundType.CASH, 40);
       aggressiveStrategy = new InvestStrategy(aggressiveConfiguration, Style.AGGRESSIVE);

    }

    @Test(expected=NoRequiredFunds.class)
    public void shouldThrowExceptionBecauseNoRequiredFunds() throws NoRequiredFunds {
        // given
        Set<Fund> funds = new HashSet<>();
        Fund fund1 = new Fund(1, "Fundusz Polski 1", FundType.POLISH);
        funds.add(fund1);
        Fund fund2 = new Fund(2, "Fundusz Polski 2", FundType.POLISH);
        funds.add(fund2);

        //when
        InvestmentResult result = aggressiveStrategy.invest(10001, funds);

        //then
    }
}
