package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationConflictException;

public class UniqueReservationListTest {

    private static final StudentId STUDENT_ONE = new StudentId("a1234567a");
    private static final StudentId STUDENT_TWO = new StudentId("a2345678b");

    private static final Reservation HALL_TWO_SLOT_ONE = new Reservation("Hall-2", STUDENT_ONE,
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    private static final Reservation HALL_TWO_SLOT_ONE_COPY = new Reservation("Hall-2", STUDENT_ONE,
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    private static final Reservation HALL_TWO_CONFLICTING = new Reservation("Hall-2", STUDENT_TWO,
            LocalDateTime.of(2026, 3, 1, 15, 0),
            LocalDateTime.of(2026, 3, 1, 17, 0));

    private static final Reservation HALL_TWO_ADJACENT = new Reservation("Hall-2", STUDENT_TWO,
            LocalDateTime.of(2026, 3, 1, 16, 0),
            LocalDateTime.of(2026, 3, 1, 18, 0));

    private static final Reservation MPSH_ONE_SAME_TIME = new Reservation("MPSH-1", STUDENT_TWO,
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    private final UniqueReservationList uniqueReservationList = new UniqueReservationList();

    @Test
    public void contains_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.contains(null));
    }

    @Test
    public void contains_reservationNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(HALL_TWO_SLOT_ONE));
    }

    @Test
    public void contains_reservationInList_returnsTrue() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertTrue(uniqueReservationList.contains(HALL_TWO_SLOT_ONE));
    }

    @Test
    public void contains_equivalentReservationInList_returnsTrue() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertTrue(uniqueReservationList.contains(HALL_TWO_SLOT_ONE_COPY));
    }

    @Test
    public void hasConflict_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.hasConflict(null));
    }

    @Test
    public void hasConflict_conflictingReservation_returnsTrue() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertTrue(uniqueReservationList.hasConflict(HALL_TWO_CONFLICTING));
    }

    @Test
    public void hasConflict_adjacentReservation_returnsFalse() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertFalse(uniqueReservationList.hasConflict(HALL_TWO_ADJACENT));
    }

    @Test
    public void hasConflict_differentResourceSameTime_returnsFalse() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertFalse(uniqueReservationList.hasConflict(MPSH_ONE_SAME_TIME));
    }

    @Test
    public void findConflictingReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.findConflictingReservation(null));
    }

    @Test
    public void findConflictingReservation_conflictingReservation_returnsReservation() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        Optional<Reservation> conflictingReservation =
                uniqueReservationList.findConflictingReservation(HALL_TWO_CONFLICTING);
        assertTrue(conflictingReservation.isPresent());
        assertEquals(HALL_TWO_SLOT_ONE, conflictingReservation.get());
    }

    @Test
    public void findConflictingReservation_nonConflictingReservation_returnsEmpty() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertFalse(uniqueReservationList.findConflictingReservation(HALL_TWO_ADJACENT).isPresent());
    }

    @Test
    public void add_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.add(null));
    }

    @Test
    public void add_duplicateReservation_throwsDuplicateReservationException() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.add(HALL_TWO_SLOT_ONE_COPY));
    }

    @Test
    public void add_conflictingReservation_throwsReservationConflictException() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertThrows(ReservationConflictException.class, () -> uniqueReservationList.add(HALL_TWO_CONFLICTING));
    }

    @Test
    public void add_nonConflictingReservation_success() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        uniqueReservationList.add(HALL_TWO_ADJACENT);

        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(HALL_TWO_SLOT_ONE);
        expectedUniqueReservationList.add(HALL_TWO_ADJACENT);

        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservations(null));
    }

    @Test
    public void setReservations_list_replacesOwnListWithProvidedList() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        List<Reservation> reservationList = Collections.singletonList(HALL_TWO_ADJACENT);
        uniqueReservationList.setReservations(reservationList);

        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(HALL_TWO_ADJACENT);

        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_listWithDuplicateReservations_throwsDuplicateReservationException() {
        List<Reservation> listWithDuplicateReservations =
                Arrays.asList(HALL_TWO_SLOT_ONE, HALL_TWO_SLOT_ONE_COPY);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList
                .setReservations(listWithDuplicateReservations));
    }

    @Test
    public void setReservations_listWithConflictingReservations_throwsDuplicateReservationException() {
        List<Reservation> listWithConflictingReservations =
                Arrays.asList(HALL_TWO_SLOT_ONE, HALL_TWO_CONFLICTING);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList
                .setReservations(listWithConflictingReservations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueReservationList.add(HALL_TWO_SLOT_ONE);
        assertThrows(UnsupportedOperationException.class, () -> uniqueReservationList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueReservationList.asUnmodifiableObservableList().toString(), uniqueReservationList.toString());
    }
}
