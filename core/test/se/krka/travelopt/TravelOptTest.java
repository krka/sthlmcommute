package se.krka.travelopt;

import org.junit.Test;
import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TravelOptTest {

    final TravelOptLocale locale = EnglishLocale.INSTANCE;
    private final TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(Prices.FULL, locale));

    @Test
	public void testEmpty() {
		TravelPlan travelPlan = TravelPlan.builder(locale).build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		assertEquals(Money.ZERO, result.getTotalCost());
	}

	@Test
	public void testSingleDay() {
        TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-19")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		assertEquals(Money.parse("67.5 SEK"), result.getTotalCost());
		assertEquals(1, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-19"), result.getTickets().get(0).getStartDate());
	}

	@Test
	public void testTwoDays() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-19")).
				addDay(Util.parseDate("2010-12-20")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		//System.out.println(result);
		assertEquals(Money.parse("135 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-19"), result.getTickets().get(0).getStartDate());
		assertEquals(Util.parseDay("2010-12-20"), result.getTickets().get(1).getStartDate());
	}


	@Test
	public void testFourDays() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-19")).
				addDay(Util.parseDate("2010-12-20")).
				addDay(Util.parseDate("2010-12-21")).
				addDay(Util.parseDate("2010-12-22")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		//System.out.println(result);
		assertEquals(Money.parse("260 SEK"), result.getTotalCost());
		assertEquals(1, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-19"), result.getTickets().get(0).getStartDate());
	}

	@Test
	public void testWithGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-18")).
				addDay(Util.parseDate("2010-12-19")).
				addDay(Util.parseDate("2010-12-20")).
				addDay(Util.parseDate("2010-12-21")).
				addDay(Util.parseDate("2010-12-22")).
				addDay(Util.parseDate("2010-12-23")).

				addDay(Util.parseDate("2011-01-03")).
				addDay(Util.parseDate("2011-01-04")).
				addDay(Util.parseDate("2011-01-05")).
				addDay(Util.parseDate("2011-01-06")).

				addDay(Util.parseDate("2011-01-10")).
				addDay(Util.parseDate("2011-01-11")).
				addDay(Util.parseDate("2011-01-12")).
				addDay(Util.parseDate("2011-01-13")).
				addDay(Util.parseDate("2011-01-14")).

				addDay(Util.parseDate("2011-01-17")).
				addDay(Util.parseDate("2011-01-18")).
				addDay(Util.parseDate("2011-01-19")).
				addDay(Util.parseDate("2011-01-20")).
				addDay(Util.parseDate("2011-01-21")).

				addDay(Util.parseDate("2011-01-24")).
				addDay(Util.parseDate("2011-01-25")).
				addDay(Util.parseDate("2011-01-26")).
				addDay(Util.parseDate("2011-01-27")).
				addDay(Util.parseDate("2011-01-28")).

				addDay(Util.parseDate("2011-01-31")).
				addDay(Util.parseDate("2011-02-01")).
				addDay(Util.parseDate("2011-02-02")).
				addDay(Util.parseDate("2011-02-03")).
				addDay(Util.parseDate("2011-02-04")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		//System.out.println(result);
		assertEquals(Money.parse("1150 SEK"), result.getTotalCost());
		assertEquals(3, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(Util.parseDay("2011-01-03"), result.getTickets().get(1).getStartDate());
		assertEquals(Util.parseDay("2011-02-02"), result.getTickets().get(2).getStartDate());
	}

	@Test
	public void testWithGapExtended() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addPeriod(Util.parseDate("2010-12-18"), Util.parseDate("2010-12-23")).
				addPeriod(Util.parseDate("2011-01-03"), Util.parseDate("2011-02-01"), "m-f").
				buildExtended("m-f");
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("950 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(Util.parseDay("2011-01-03"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testSevenDaysGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-01")).
				addDay(Util.parseDate("2010-12-02")).
				addDay(Util.parseDate("2010-12-03")).
				addDay(Util.parseDate("2010-12-07")).
				addDay(Util.parseDate("2010-12-08")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		//System.out.println(result);
		assertEquals(Money.parse("327.5 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-01"), result.getTickets().get(0).getStartDate());
		assertEquals(Util.parseDay("2010-12-08"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void test16DaysWithGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-01")).
				addDay(Util.parseDate("2010-12-02")).
				addDay(Util.parseDate("2010-12-03")).
				addDay(Util.parseDate("2010-12-04")).

				addDay(Util.parseDate("2010-12-09")).
				addDay(Util.parseDate("2010-12-10")).
				addDay(Util.parseDate("2010-12-11")).
				addDay(Util.parseDate("2010-12-12")).

				addDay(Util.parseDate("2010-12-16")).
				addDay(Util.parseDate("2010-12-17")).
				addDay(Util.parseDate("2010-12-18")).
				addDay(Util.parseDate("2010-12-19")).

				addDay(Util.parseDate("2010-12-28")).
				addDay(Util.parseDate("2010-12-29")).
				addDay(Util.parseDate("2010-12-30")).
				addDay(Util.parseDate("2010-12-31")).

				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
        //System.out.println(travelPlan);
		//System.out.println(result);
		assertEquals(Money.parse("757.5 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(Util.parseDay("2010-12-01"), result.getTickets().get(0).getStartDate());
		assertEquals(Util.parseDay("2010-12-31"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testExtend() {
		TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-01")).
				buildExtended("m-f");
		TravelResult optimum = travelOpt.findOptimum(plan);
		//System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testExtend2() {
		TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-01")).
				buildExtended("m,w,f,sa");
		TravelResult optimum = travelOpt.findOptimum(plan);
        //System.out.println(plan);
		//System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testExtend3() {
		TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addDay(Util.parseDate("2010-12-01")).
				buildExtended("m");
		TravelResult optimum = travelOpt.findOptimum(plan);
        //System.out.println(plan);
		//System.out.println(optimum);
		assertEquals(Money.parse("67.5 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testRange() {

        TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(1).
                addPeriod(Util.parseDate("2010-12-01"), Util.parseDate("2010-12-11"), "m-f").
                build();
		ArrayList<TravelPlanDate> list = new ArrayList<TravelPlanDate>(plan.getDates());
        System.out.println(plan);
		assertEquals(8, list.size());
		assertEquals(Util.parseDay("2010-12-01"), list.get(0).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-02"), list.get(1).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-03"), list.get(2).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-06"), list.get(3).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-07"), list.get(4).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-08"), list.get(5).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-09"), list.get(6).getDayOrdinal());
		assertEquals(Util.parseDay("2010-12-10"), list.get(7).getDayOrdinal());
	}

	@Test
	public void testWeekDaySettings() {
		TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(6).
				addPeriod(Util.parseDate("2010-12-13"), Util.parseDate("2010-12-19"), "m-tu:1, f, sa:2").
				build();
		ArrayList<TravelPlanDate> list = new ArrayList<TravelPlanDate>(plan.getDates());
		assertEquals(4, list.size());

		assertEquals(1, list.get(0).getNumCoupons());
		assertEquals(Util.parseDay("2010-12-13"), list.get(0).getDayOrdinal());

		assertEquals(1, list.get(1).getNumCoupons());
		assertEquals(Util.parseDay("2010-12-14"), list.get(1).getDayOrdinal());

		assertEquals(6, list.get(2).getNumCoupons());
		assertEquals(Util.parseDay("2010-12-17"), list.get(2).getDayOrdinal());

		assertEquals(2, list.get(3).getNumCoupons());
		assertEquals(Util.parseDay("2010-12-18"), list.get(3).getDayOrdinal());
	}

    @Test
    public void testExtend4() {
        TravelPlan plan1 = TravelPlan.builder(locale).
                setCouponsPerDay(4).
                addPeriod(Util.parseDate("2011-01-05"), Util.parseDate("2011-01-15")).
                build();

        TravelPlan plan2 = TravelPlan.builder(locale).
                setCouponsPerDay(4).
                addPeriod(Util.parseDate("2011-01-05"), Util.parseDate("2011-01-15")).
                buildExtended("mon-sun:1");

        TravelResult result1 = travelOpt.findOptimum(plan1);
        TravelResult result2 = travelOpt.findOptimum(plan2);
        assertEquals(result1, result2);
    }

    @Test
    public void testEmptyExtend() {
        TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(0).
                addPeriod(Util.parseDate("2011-01-01"), Util.parseDate("2011-01-01"), "m:1, tu-su:0").
                buildExtended("mon-sun:0");

        TravelResult result = travelOpt.findOptimum(plan);
        assertEquals(Money.ZERO, result.getTotalCost());
    }

    @Test
    public void testNonEmptyExtend() {
        TravelPlan plan = TravelPlan.builder(locale).
                setCouponsPerDay(0).
                addPeriod(Util.parseDate("2011-01-01"), Util.parseDate("2011-01-01"), "m:1, tu-su:0").
                buildExtended("mon-sun:1");

        TravelResult result = travelOpt.findOptimum(plan);
        assertEquals(Money.ZERO, result.getTotalCost());
    }
}
