package se.krka.travelopt;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilTest {
    @Test
    public void testWeekday() {
        assertEquals(3, Util.getDayOfWeek(Util.parseDay("1970-01-01")));

        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-1-3")));
        assertEquals(1, Util.getDayOfWeek(Util.parseDay("2000-1-4")));
        assertEquals(2, Util.getDayOfWeek(Util.parseDay("2000-1-5")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-1-10")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-1-17")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-1-24")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-1-31")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-2-7")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-2-14")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-2-21")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-2-28")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-3-6")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-3-13")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2000-12-25")));
        assertEquals(0, Util.getDayOfWeek(Util.parseDay("2010-12-27")));

        assertEquals(6, Util.getDayOfWeek(Util.parseDay("2000-1-2")));

        assertEquals(5, Util.getDayOfWeek(Util.parseDay("2000-1-1")));

        assertEquals(4, Util.getDayOfWeek(Util.parseDay("1999-12-31")));

    }

    @Test
    public void testParseDate() {
        Date date = Util.parseDate("2000-01-01");
        assertEquals(100, date.getYear());
        assertEquals(0, date.getMonth());
        assertEquals(1, date.getDate());
    }

    @Test
    public void testParseDate2() {
        Date date = Util.parseDate("2000-12-31");
        assertEquals(100, date.getYear());
        assertEquals(11, date.getMonth());
        assertEquals(31, date.getDate());
    }

    @Test
    public void testFormat() {
        Date date = Util.parseDate("2011-01-01");
        for (int i = 1; i < 10; i++) {
            String s = Util.format(date);
            assertEquals(s, "2011-01-0" + i);
            date = Util.plusDays(date, 1);
        }
    }

    @Test
    public void testDayDifference() {
        Date x = Util.parseDate("2010-01-01");
        Date y = Util.parseDate("2010-01-01");
        y.setHours(4);
        assertEquals(0, Util.dayDifference(x, y));
    }

    @Test
    public void testDayDifference2() {
        Date x = Util.parseDate("2010-01-01");
        Date y = Util.parseDate("2010-01-02");
        assertEquals(-1, Util.dayDifference(x, y));
    }

    @Test
    public void testDayDifference3() {
        Date x = Util.parseDate("2010-01-01");
        Date y = Util.parseDate("2010-01-02");
        assertEquals(1, Util.dayDifference(y, x));
    }

    @Test
    public void testBefore() {
        Date x = Util.parseDate("2010-01-01");
        Date y = Util.parseDate("2010-01-02");
        assertTrue(Util.before(x, y));
        assertFalse(Util.before(x, x));
        assertFalse(Util.before(y, x));
    }

    @Test
    public void testOrdinal() {
        Date date = Util.parseDate("2010-03-04");
        assertEquals("2010-03-04", Util.format(date));
        int ordinal = Util.toDayOrdinal(date);
        assertEquals(date, Util.fromDayOrdinal(ordinal));

        assertEquals("2010-03-04", Util.formatDay(ordinal));
    }
}
