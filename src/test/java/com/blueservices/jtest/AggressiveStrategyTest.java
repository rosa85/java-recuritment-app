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

public class AggressiveStrategyTest {

    private InvestStrategy safeStrategy;

    @Before
    public void setUp() throws BadStrategyException {
        Map<FundType, Integer> aggressiveConfiguration = getAggressiveConfiguration();
        safeStrategy = new InvestStrategy(aggressiveConfiguration);
    }

    public static Map<FundType, Integer>  getAggressiveConfiguration(){
        Map<FundType, Integer> aggressiveConfiguration = new HashMap<>();
        aggressiveConfiguration.put(FundType.POLISH, 40);
        aggressiveConfiguration.put(FundType.FOREIGN, 20);
        aggressiveConfiguration.put(FundType.CASH, 40);
        return aggressiveConfiguration;
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
        InvestmentResult result = safeStrategy.invest(10000, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 20.0, 2000));
        expected.add(new FundResult(fund2, 20.0, 2000));
        expected.add(new FundResult(fund3, 6.68, 668));
        expected.add(new FundResult(fund4, 6.66, 666));
        expected.add(new FundResult(fund5, 6.66, 666));
        expected.add(new FundResult(fund6, 40.0, 4000));

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
        InvestmentResult result = safeStrategy.invest(10001, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 20.0, 2000));
        expected.add(new FundResult(fund2, 20.0, 2000));
        expected.add(new FundResult(fund3, 6.68, 668));
        expected.add(new FundResult(fund4, 6.66, 666));
        expected.add(new FundResult(fund5, 6.66, 666));
        expected.add(new FundResult(fund6, 40.0, 4000));

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
        InvestmentResult result = safeStrategy.invest(10000, funds);

        //then
        List<FundResult> expected = new ArrayList<>();
        expected.add(new FundResult(fund1, 13.34, 1334));
        expected.add(new FundResult(fund2, 13.33, 1333));
        expected.add(new FundResult(fund3, 13.33, 1333));
        expected.add(new FundResult(fund4, 10, 1000));
        expected.add(new FundResult(fund5, 10, 1000));
        expected.add(new FundResult(fund6, 40, 4000));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(0));

    }
}
