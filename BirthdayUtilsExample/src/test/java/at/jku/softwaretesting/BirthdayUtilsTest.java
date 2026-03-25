package at.jku.softwaretesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.MonthDay;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
        try(MockedStatic<BirthdayUtils> mockBirthdayUtils = Mockito.mockStatic(BirthdayUtils.class)) {
            mockBirthdayUtils.when(() -> BirthdayUtils.calcDaysUntilBirthday(birthday))
                    .thenReturn(12);
            int daysUntilBirthday = BirthdayUtils.calcDaysUntilBirthday(birthday);
            assertEquals(12, daysUntilBirthday);
        }
    }
}
