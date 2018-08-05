
package com.blueservices.jtest;

import biz.application.Application;
import biz.application.Exceptions.NoStrategyException;
import biz.application.Invests.FundResult;
import biz.application.Invests.InvestStrategy;
import biz.application.Invests.InvestmentResult;
import biz.application.Funds.Fund;
import biz.application.Funds.FundType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SafeStrategyTest {

    private InvestStrategy safeStrategy;

    @Before
    public void setUp() throws NoStrategyException {
       Map<FundType, Integer> safeConfiguration = new HashMap<>();
       safeConfiguration.put(FundType.POLISH, 20);
       safeConfiguration.put(FundType.FOREIGN, 75);
       safeConfiguration.put(FundType.CASH, 5);
       safeStrategy = new InvestStrategy();
       safeStrategy.useStrategy(safeConfiguration);
    }

    @Test
    public void setSafeStrategyTest1() throws Exception {
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
        expected.add(new FundResult(fund1, 10.0, 1000));
        expected.add(new FundResult(fund2, 10.0, 1000));
        expected.add(new FundResult(fund3, 25.0, 2500));
        expected.add(new FundResult(fund4, 25.0, 2500));
        expected.add(new FundResult(fund5, 25.0, 2500));
        expected.add(new FundResult(fund6, 5.0, 500));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(0));
    }

    @Test
    public void setSafeStrategyTest2() throws Exception {
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
        expected.add(new FundResult(fund1, 10.0,1000));
        expected.add(new FundResult(fund2, 10.0, 1000));
        expected.add(new FundResult(fund3, 25.0, 2500));
        expected.add(new FundResult(fund4, 25.0, 2500));
        expected.add(new FundResult(fund5, 25.0, 2500));
        expected.add(new FundResult(fund6, 5.0, 500));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(1));

    }

    @Test
    public void setSafeStrategyTest3() throws Exception {
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
        expected.add(new FundResult(fund1, 6.68, 668));
        expected.add(new FundResult(fund2, 6.66, 666));
        expected.add(new FundResult(fund3, 6.66, 666));
        expected.add(new FundResult(fund4, 37.5, 3750));
        expected.add(new FundResult(fund5, 37.5, 3750));
        expected.add(new FundResult(fund6, 5, 500));

        assertTrue(result.getResult().containsAll(expected));
        assertThat(result.getRest(), equalTo(0));

    }

}