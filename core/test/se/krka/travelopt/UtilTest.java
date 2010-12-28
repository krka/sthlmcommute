package se.krka.travelopt;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class UtilTest {
    @Test
    public void testWeekday() {
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-1-3")));
        assertEquals(1, Util.getDayOfWeek(Util.parse("2000-1-4")));
        assertEquals(2, Util.getDayOfWeek(Util.parse("2000-1-5")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-1-10")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-1-17")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-1-24")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-1-31")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-2-7")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-2-14")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-2-21")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-2-28")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-3-6")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-3-13")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2000-12-25")));
        assertEquals(0, Util.getDayOfWeek(Util.parse("2010-12-27")));

        assertEquals(6, Util.getDayOfWeek(Util.parse("2000-1-2")));

        assertEquals(5, Util.getDayOfWeek(Util.parse("2000-1-1")));

        assertEquals(4, Util.getDayOfWeek(Util.parse("1999-12-31")));

    }

    @Test
    public void testParseDate() {
        Date date = Util.parse("2000-01-01");
        assertEquals(2000, date.getYear());
        assertEquals(0, date.getMonth());
        assertEquals(0, date.getDay());
    }

    @Test
    public void testParseDate2() {
        Date date = Util.parse("2000-12-31");
        assertEquals(2000, date.getYear());
        assertEquals(11, date.getMonth());
        assertEquals(30, date.getDay());
    }
}
