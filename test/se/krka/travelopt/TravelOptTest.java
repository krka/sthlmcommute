package se.krka.travelopt;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 17:57:25
 * To change this template use File | Settings | File Templates.
 */
public class TravelOptTest {

	@Test
	public void testEmpty() {
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);
		TravelPlan travelPlan = TravelPlan.builder().build();
		TravelResult result = travelOpt.findOptimum(travelPlan);
		assertEquals(Money.ZERO, result.getTotalCost());
	}

	@Test
	public void testSingleDay() {
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		assertEquals(Money.parse("1152.5 SEK"), result.getTotalCost());
		assertEquals(5, result.getTickets().size());
		assertEquals(new DateTime("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2011-01-03"), result.getTickets().get(1).getStartDate());
		assertEquals(new DateTime("2011-02-02"), result.getTickets().get(2).getStartDate());
		assertEquals(new DateTime("2011-02-03"), result.getTickets().get(3).getStartDate());
		assertEquals(new DateTime("2011-02-04"), result.getTickets().get(4).getStartDate());
	}

	@Test
	public void testWithGapExtended() {
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
				setTicketsPerDay(6).
				addPeriod(new DateTime("2010-12-18"), new DateTime("2010-12-23"), WeekDays.ALL).
				addPeriod(new DateTime("2011-01-03"), new DateTime("2010-02-01"), new WeekDays("m-f")).
				buildExtended(new WeekDays("m-f"));
		TravelResult result = travelOpt.findOptimum(travelPlan);
		System.out.println(result);
		assertEquals(Money.parse("950 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-18"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2011-01-03"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testSevenDaysGap() {
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);

		TravelPlan travelPlan = TravelPlan.builder().
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
		System.out.println(result);
		assertEquals(Money.parse("757.5 SEK"), result.getTotalCost());
		assertEquals(2, result.getTickets().size());
		assertEquals(new DateTime("2010-12-01"), result.getTickets().get(0).getStartDate());
		assertEquals(new DateTime("2010-12-31"), result.getTickets().get(1).getStartDate());
	}

	@Test
	public void testExtend() {
		TravelPlan plan = TravelPlan.builder().
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				buildExtended(new WeekDays("m-f"));
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);
		TravelResult optimum = travelOpt.findOptimum(plan);
		System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testExtend2() {
		TravelPlan plan = TravelPlan.builder().
				setTicketsPerDay(6).
				addDay(new DateTime("2010-12-01")).
				buildExtended(new WeekDays("m,w,f,sa"));
		TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);
		TravelResult optimum = travelOpt.findOptimum(plan);
		System.out.println(optimum);
		assertEquals(Money.parse("690 SEK"), optimum.getTotalCost());
		assertEquals(1, optimum.getTickets().size());
	}

	@Test
	public void testRange() {
		TravelPlan plan = TravelPlan.builder().
                setTicketsPerDay(1).
                addPeriod(new DateTime("2010-12-01"), new DateTime("2010-12-11"), new WeekDays("m-f")).
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
		TravelPlan plan = TravelPlan.builder().
				setTicketsPerDay(6).
				addPeriod(new DateTime("2010-12-13"), new DateTime("2010-12-19"), new WeekDays("m-tu:1, f, sa:2")).
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
