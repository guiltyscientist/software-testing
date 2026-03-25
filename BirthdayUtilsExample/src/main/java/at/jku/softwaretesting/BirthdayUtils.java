package at.jku.softwaretesting;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

public class BirthdayUtils {

    public static int calcDaysUntilBirthday(MonthDay birthday) {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), birthday.atYear(LocalDate.now().getYear()));
    }
}