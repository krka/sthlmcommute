package se.krka.travelopt;

import org.joda.time.DateTime;
import org.junit.Test;
import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.SwedishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TravelOptTest {

    TravelOptLocale locale = new EnglishLocale();
    private TravelOpt travelOpt = new TravelOpt(Prices.createSLFullPrice(locale));

    @Test
	public void testEmpty() {
		TravelPlan travelPlan = TravelPlan.builder(locale).build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		assertEquals(Money.ZERO, result.getTotalCost());
	}

	@Test
	public void testSingleDay() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-19")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		assertEquals(Money.parse("67.5 SEK"), result.getTotalCost());
		assertEquals(1, result.getTickets().size());
		assertEquals(new DateTime("2010-12-19"), result.getTickets().get(0).getStartDate());
	}

	@Test
	public void testTwoDays() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-19")).
				addDay(new DateTime("2010-12-20")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("135 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-19"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2010-12-20"), result.getTickets().get(1).getStartDate());
	}


	@Test
	public void testFourDays() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-19")).
				addDay(new DateTime("2010-12-20")).
				addDay(new DateTime("2010-12-21")).
				addDay(new DateTime("2010-12-22")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("260 SEK"), result.getTotalCost());
		assertEquals(1, result.getTickets().size());
		assertEquals(new DateTime("2010-12-19"), result.getTickets().get(0).getStartDate());
	}

	@Test
	public void testWithGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-18")).
				addDay(new DateTime("2010-12-19")).
				addDay(new DateTime("2010-12-20")).
				addDay(new DateTime("2010-12-21")).
				addDay(new DateTime("2010-12-22")).
				addDay(new DateTime("2010-12-23")).

				addDay(new DateTime("2011-01-03")).
				addDay(new DateTime("2011-01-04")).
				addDay(new DateTime("2011-01-05")).
				addDay(new DateTime("2011-01-06")).

				addDay(new DateTime("2011-01-10")).
				addDay(new DateTime("2011-01-11")).
				addDay(new DateTime("2011-01-12")).
				addDay(new DateTime("2011-01-13")).
				addDay(new DateTime("2011-01-14")).

				addDay(new DateTime("2011-01-17")).
				addDay(new DateTime("2011-01-18")).
				addDay(new DateTime("2011-01-19")).
				addDay(new DateTime("2011-01-20")).
				addDay(new DateTime("2011-01-21")).

				addDay(new DateTime("2011-01-24")).
				addDay(new DateTime("2011-01-25")).
				addDay(new DateTime("2011-01-26")).
				addDay(new DateTime("2011-01-27")).
				addDay(new DateTime("2011-01-28")).

				addDay(new DateTime("2011-01-31")).
				addDay(new DateTime("2011-02-01")).
				addDay(new DateTime("2011-02-02")).
				addDay(new DateTime("2011-02-03")).
				addDay(new DateTime("2011-02-04")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("1150 SEK"), result.getTotalCost());
		assertEquals(3, result.getTickets().size());
		assertEquals(new DateTime("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2011-01-03"), result.getTickets().get(1).getStartDate());
		assertEquals(new DateTime("2011-02-02"), result.getTickets().get(2).getStartDate());
	}

	@Test
	public void testWithGapExtended() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addPeriod(new DateTime("2010-12-18"), new DateTime("2010-12-23")).
				addPeriod(new DateTime("2011-01-03"), new DateTime("2010-02-01"), "m-f").
				buildExtended("m-f");
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("950 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2011-01-03"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testSevenDaysGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				addDay(new DateTime("2010-12-02")).
				addDay(new DateTime("2010-12-03")).
				addDay(new DateTime("2010-12-07")).
				addDay(new DateTime("2010-12-08")).
				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("327.5 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-01"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2010-12-08"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void test16DaysWithGap() {
		TravelPlan travelPlan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				addDay(new DateTime("2010-12-02")).
				addDay(new DateTime("2010-12-03")).
				addDay(new DateTime("2010-12-04")).

				addDay(new DateTime("2010-12-09")).
				addDay(new DateTime("2010-12-10")).
				addDay(new DateTime("2010-12-11")).
				addDay(new DateTime("2010-12-12")).

				addDay(new DateTime("2010-12-16")).
				addDay(new DateTime("2010-12-17")).
				addDay(new DateTime("2010-12-18")).
				addDay(new DateTime("2010-12-19")).

				addDay(new DateTime("2010-12-28")).
				addDay(new DateTime("2010-12-29")).
				addDay(new DateTime("2010-12-30")).
				addDay(new DateTime("2010-12-31")).

				build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
        System.out.println(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("757.5 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-01"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2010-12-31"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testExtend() {
		TravelPlan plan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				buildExtended("m-f");
		TravelResult optimum = travelOpt.findOptimum(plan);
		System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testExtend2() {
		TravelPlan plan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				buildExtended("m,w,f,sa");
		TravelResult optimum = travelOpt.findOptimum(plan);
        //System.out.println(plan);
		System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testExtend3() {
		TravelPlan plan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				buildExtended("m");
		TravelResult optimum = travelOpt.findOptimum(plan);
        //System.out.println(plan);
		System.out.println(optimum);
		assertEquals(Money.parse("67.5 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testRange() {
		TravelPlan plan = TravelPlan.builder(locale).
                setTicketsPerDay(1).
                addPeriod(new DateTime("2010-12-01"), new DateTime("2010-12-11"), "m-f").
                build();
		ArrayList<TravelPlanDate> list = new ArrayList<TravelPlanDate>(plan.getDates());
		assertEquals(8, list.size());
		assertEquals(new DateTime("2010-12-01"), list.get(0).getDate());
		assertEquals(new DateTime("2010-12-02"), list.get(1).getDate());
		assertEquals(new DateTime("2010-12-03"), list.get(2).getDate());
		assertEquals(new DateTime("2010-12-06"), list.get(3).getDate());
		assertEquals(new DateTime("2010-12-07"), list.get(4).getDate());
		assertEquals(new DateTime("2010-12-08"), list.get(5).getDate());
		assertEquals(new DateTime("2010-12-09"), list.get(6).getDate());
		assertEquals(new DateTime("2010-12-10"), list.get(7).getDate());
	}

	@Test
	public void testWeekDaySettings() {
		TravelPlan plan = TravelPlan.builder(locale).
				setTicketsPerDay(6).
				addPeriod(new DateTime("2010-12-13"), new DateTime("2010-12-19"), "m-tu:1, f, sa:2").
				build();
		ArrayList<TravelPlanDate> list = new ArrayList<TravelPlanDate>(plan.getDates());
		assertEquals(4, list.size());

		assertEquals(1, list.get(0).getNumTickets());
		assertEquals(new DateTime("2010-12-13"), list.get(0).getDate());

		assertEquals(1, list.get(1).getNumTickets());
		assertEquals(new DateTime("2010-12-14"), list.get(1).getDate());

		assertEquals(6, list.get(2).getNumTickets());
		assertEquals(new DateTime("2010-12-17"), list.get(2).getDate());

		assertEquals(2, list.get(3).getNumTickets());
		assertEquals(new DateTime("2010-12-18"), list.get(3).getDate());
	}
}
