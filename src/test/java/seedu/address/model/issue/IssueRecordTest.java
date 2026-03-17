package seedu.address.model.issue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.StudentId;

public class IssueRecordTest {

    private static final StudentId STUDENT_ONE = new StudentId("a1234567a");
    private static final StudentId STUDENT_TWO = new StudentId("a2345678b");

    @Test
    public void constructor_invalidItemId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException
                .class, () -> new IssueRecord("!invalid", STUDENT_ONE, LocalDateTime.now().plusDays(1)));
    }

    @Test
    public void constructor_dueDateTimeInPast_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException
                .class, () -> new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE,
                        LocalDateTime.now().minusDays(1)));
    }

    @Test
    public void hasSameItem_sameItemId_returnsTrue() {
        IssueRecord first = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE,
                LocalDateTime.now().plusDays(1));
        IssueRecord second = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_TWO,
                LocalDateTime.now().plusDays(2));

        assertTrue(first.hasSameItem(second));
    }

    @Test
    public void hasSameItem_differentItemId_returnsFalse() {
        IssueRecord first = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE,
                LocalDateTime.now().plusDays(1));
        IssueRecord second = new IssueRecord("Molten-Volleyball", STUDENT_TWO,
                LocalDateTime.now().plusDays(2));

        assertFalse(first.hasSameItem(second));
    }

    @Test
    public void equals() {
        LocalDateTime dueDateTime = LocalDateTime.now().plusDays(1);

        IssueRecord first = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE, dueDateTime);
        IssueRecord copy = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE, dueDateTime);
        IssueRecord different = new IssueRecord("Molten-Volleyball", STUDENT_TWO, dueDateTime.plusDays(1));

        assertTrue(first.equals(copy));
        assertTrue(first.equals(first));
        assertFalse(first.equals(null));
        assertFalse(first.equals(different));
    }
}
