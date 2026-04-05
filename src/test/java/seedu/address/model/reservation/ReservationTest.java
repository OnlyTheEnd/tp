package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.StudentId;

public class ReservationTest {

    private static final StudentId STUDENT_ONE = new StudentId("a1234567a");
    private static final StudentId STUDENT_TWO = new StudentId("a2345678b");

    @Test
    public void constructor_invalidResourceId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation("!invalid", STUDENT_ONE,
                        LocalDateTime.of(2099, 3, 1, 14, 0),
                        LocalDateTime.of(2099, 3, 1, 16, 0)));
    }

    @Test
    public void constructor_endNotAfterStart_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation("Hall-2", STUDENT_ONE,
                        LocalDateTime.of(2099, 3, 1, 16, 0),
                        LocalDateTime.of(2099, 3, 1, 16, 0)));

        assertThrows(IllegalArgumentException.class, () -> new Reservation("Hall-2", STUDENT_ONE,
                        LocalDateTime.of(2099, 3, 1, 16, 0),
                        LocalDateTime.of(2099, 3, 1, 15, 0)));
    }

    @Test
    public void conflictsWith_sameResourceOverlappingTime_returnsTrue() {
        Reservation first = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation second = new Reservation("Hall-2", STUDENT_TWO,
                LocalDateTime.of(2099, 3, 1, 15, 0),
                LocalDateTime.of(2099, 3, 1, 17, 0));

        assertTrue(first.conflictsWith(second));
        assertTrue(second.conflictsWith(first));
    }

    @Test
    public void conflictsWith_sameResourceAdjacentTime_returnsFalse() {
        Reservation first = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation second = new Reservation("Hall-2", STUDENT_TWO,
                LocalDateTime.of(2099, 3, 1, 16, 0),
                LocalDateTime.of(2099, 3, 1, 18, 0));

        assertFalse(first.conflictsWith(second));
        assertFalse(second.conflictsWith(first));
    }

    @Test
    public void conflictsWith_differentResourceSameTime_returnsFalse() {
        Reservation first = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation second = new Reservation("MPSH-1", STUDENT_TWO,
                LocalDateTime.of(2099, 3, 1, 14, 30),
                LocalDateTime.of(2099, 3, 1, 15, 30));

        assertFalse(first.conflictsWith(second));
        assertFalse(second.conflictsWith(first));
    }

    @Test
    public void equals() {
        Reservation first = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation copy = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation different = new Reservation("Hall-2", STUDENT_TWO,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));

        assertTrue(first.equals(copy));
        assertTrue(first.equals(first));
        assertFalse(first.equals(null));
        assertFalse(first.equals(different));
    }

    @Test
    public void conflictsWith_sameStudentOverlappingDifferentResource_returnsTrue() {
        Reservation first = new Reservation("Hall-2", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 14, 0),
                LocalDateTime.of(2099, 3, 1, 16, 0));
        Reservation second = new Reservation("MPSH-1", STUDENT_ONE,
                LocalDateTime.of(2099, 3, 1, 15, 0),
                LocalDateTime.of(2099, 3, 1, 17, 0));

        assertTrue(first.conflictsWith(second));
        assertTrue(second.conflictsWith(first));
    }
}
