
package com.blueservices.jtest;

import biz.application.Application;
import biz.application.Invests.InvestStrategy;
import biz.application.Invests.InvestmentResult;
import biz.application.Invests.Style;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SafeStrategyTest {


    private InvestStrategy safeStrategy;


    @Before
    public void setUp() {
       Map<FundType, Integer> safeConfiguration = new HashMap<>();
       safeConfiguration.put(FundType.POLISH, 20);
       safeConfiguration.put(FundType.FOREIGN, 75);
       safeConfiguration.put(FundType.CASH, 5);
       safeStrategy = new InvestStrategy(safeConfiguration, Style.SAFE);

     /*  Map<FundType, Integer> aggressiveConfiguration = new HashMap<>();
       aggressiveConfiguration.put(FundType.POLISH, 40);
       aggressiveConfiguration.put(FundType.FOREIGN, 20);
       aggressiveConfiguration.put(FundType.CASH, 40);
       aggressiveStrategy = new InvestStrategy(aggressiveConfiguration, Style.AGGRESSIVE);


        Map<FundType, Integer> balancedConfiguration = new HashMap<>();
        balancedConfiguration.put(FundType.POLISH, 30);
        balancedConfiguration.put(FundType.FOREIGN, 60);
        balancedConfiguration.put(FundType.CASH, 10);
        balancedStrategy = new InvestStrategy(balancedConfiguration, Style.BALANCED);*/
    }


    @Test
    public void setSafeStrategyTest1() throws Exception {
        // given
        Set<Fund> funds = new HashSet<>();
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
        Map<Fund, Integer> expected = new HashMap<Fund, Integer>();
        expected.put(fund1, 1000);
        expected.put(fund2, 1000);
        expected.put(fund3, 2500);
        expected.put(fund4, 2500);
        expected.put(fund5, 2500);
        expected.put(fund6, 500);

        assertThat(result.getResult(), equalTo(expected));
        assertThat(result.getRest(), equalTo(0));

    }

    @Test
    public void setSafeStrategyTest2() throws Exception {
        // given
        Set<Fund> funds = new HashSet<>();
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
        Map<Fund, Integer> expected = new HashMap<Fund, Integer>();
        expected.put(fund1, 1000);
        expected.put(fund2, 1000);
        expected.put(fund3, 2500);
        expected.put(fund4, 2500);
        expected.put(fund5, 2500);
        expected.put(fund6, 500);

        assertThat(result.getResult(), equalTo(expected));
        assertThat(result.getRest(), equalTo(1));

    }

    @Test
    public void setSafeStrategyTest3() throws Exception {
        // given
        Set<Fund> funds = new HashSet<>();
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
        Map<Fund, Integer> expected = new HashMap<>();
        expected.put(fund1, 668);
        expected.put(fund2, 666);
        expected.put(fund3, 666);
        expected.put(fund4, 3750);
        expected.put(fund5, 3750);
        expected.put(fund6, 500);

        assertThat(result.getResult(), equalTo(expected));
        assertThat(result.getRest(), equalTo(0));

    }

}