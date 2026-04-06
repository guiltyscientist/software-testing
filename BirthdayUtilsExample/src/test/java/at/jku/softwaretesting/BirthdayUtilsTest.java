package at.jku.softwaretesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;
import java.time.MonthDay;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class BirthdayUtilsTest {

    @Test
    public void testCalcDaysUntilBirthday() {
        MonthDay birthday = MonthDay.of(3, 31);
        int daysUntilBirthday = BirthdayUtils.calcDaysUntilBirthday(birthday);
        assertEquals(12, daysUntilBirthday);
    }

    @Test
    public void testCalcDaysUntilBirthdayMocked() {
        MonthDay birthday = MonthDay.of(3, 31);
        LocalDate fakeToday = LocalDate.of(2026, 3, 21);

        try (MockedStatic<LocalDate> mocked = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDate::now).thenReturn(fakeToday);

            int result = BirthdayUtils.calcDaysUntilBirthday(birthday);

            assertEquals(10, result);
        }
    }
}
