package seedu.address.model.issue;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.StudentId;

/**
 * Represents a record of an issued item in the address book.
 * Guarantees: details are present and not null, and field values are validated.
 */
public class IssueRecord {
    public static final String MESSAGE_ITEM_ID_CONSTRAINTS =
        "Item ID should start with a letter and contain only letters, digits, and hyphens.";

    public static final String MESSAGE_DUE_DATE_TIME_CONSTRAINTS =
        "Due date/time must not be in the past.";

    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z0-9-]*";
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final String itemId;
    private final StudentId studentId;
    private final LocalDateTime dueDateTime;

    /**
     * Constructs an {@code IssueRecord}.
     *
     * @param itemId      The item identifier.
     * @param studentId   The student identifier of the borrower.
     * @param dueDateTime The due date/time for returning the item.
     */
    public IssueRecord(String itemId, StudentId studentId, LocalDateTime dueDateTime) {
        requireNonNull(itemId);
        requireNonNull(studentId);
        requireNonNull(dueDateTime);

        checkArgument(isValidItemId(itemId), MESSAGE_ITEM_ID_CONSTRAINTS);
        checkArgument(!dueDateTime.isBefore(LocalDateTime.now()), MESSAGE_DUE_DATE_TIME_CONSTRAINTS);

        this.itemId = normalizeItemId(itemId);
        this.studentId = studentId;
        this.dueDateTime = dueDateTime;
    }

    /**
     * Returns the normalized item ID.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Returns the student ID of the borrower.
     */
    public StudentId getStudentId() {
        return studentId;
    }

    /**
     * Returns the due date/time.
     */
    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    /**
     * Returns the due date/time formatted as {@code yyyy-MM-dd HHmm}.
     */
    public String getFormattedDueDateTime() {
        return dueDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid item ID.
     */
    public static boolean isValidItemId(String test) {
        return test != null && normalizeItemId(test).matches(VALIDATION_REGEX);
    }

    /**
     * Returns a normalized version of the given item ID.
     * Leading and trailing whitespace is removed and letters are converted to
     * uppercase.
     */
    public static String normalizeItemId(String itemId) {
        requireNonNull(itemId);
        return itemId.trim().toUpperCase();
    }

    /**
     * Returns true if both issue records have the same item.
     */
    public boolean hasSameItem(IssueRecord other) {
        requireNonNull(other);
        return itemId.equals(other.itemId);
    }

    /**
     * Returns true if both issue records have the same item ID, student ID, and due
     * date/time.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IssueRecord)) {
            return false;
        }
        IssueRecord otherIssueRecord = (IssueRecord) other;
        return itemId.equals(otherIssueRecord.itemId)
                && studentId.equals(otherIssueRecord.studentId)
                && dueDateTime.equals(otherIssueRecord.dueDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, studentId, dueDateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("itemId", itemId)
                .add("studentId", studentId)
                .add("dueDateTime", getFormattedDueDateTime())
                .toString();
    }
}
