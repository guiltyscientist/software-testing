package at.jku.softwaretesting;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

public class BirthdayUtilsRefactored {
    public static int calcDaysUntilBirthday(MonthDay birthday, LocalDate today) {
        return (int) ChronoUnit.DAYS.between(today, birthday.atYear(today.getYear()));
    }
}
