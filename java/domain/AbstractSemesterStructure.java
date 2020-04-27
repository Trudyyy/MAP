package domain;

import java.time.LocalDate;
import java.util.Calendar;

public abstract class AbstractSemesterStructure {
    protected LocalDate startSemesterDate;

    protected LocalDate startHolidayDate;
    protected LocalDate endHolidayDate;

    protected LocalDate endSemesterDate;

    protected Calendar calendar;

    public boolean validateDate(LocalDate localDate) {
        if (localDate.isAfter(this.startSemesterDate) && localDate.isBefore(this.endSemesterDate)) {
            return localDate.isBefore(this.startHolidayDate) || localDate.isAfter(this.endHolidayDate);
        }
        return false;
    }

    public abstract int getCurrentWeek();
}
