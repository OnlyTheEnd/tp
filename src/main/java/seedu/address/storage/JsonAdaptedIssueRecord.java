package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;

/**
 * Jackson-friendly version of {@link IssueRecord}.
 */
class JsonAdaptedIssueRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue record's %s field is missing!";

    private final String itemId;
    private final String studentId;
    private final String dueDateTime;

    /**
     * Constructs a {@code JsonAdaptedIssueRecord} with the given issue record details.
     */
    @JsonCreator
    public JsonAdaptedIssueRecord(@JsonProperty("itemId") String itemId,
                                  @JsonProperty("studentId") String studentId,
                                  @JsonProperty("dueDateTime") String dueDateTime) {
        this.itemId = itemId;
        this.studentId = studentId;
        this.dueDateTime = dueDateTime;
    }

    /**
     * Converts a given {@code IssueRecord} into this class for Jackson use.
     */
    public JsonAdaptedIssueRecord(IssueRecord source) {
        itemId = source.getItemId();
        studentId = source.getStudentId().value;
        dueDateTime = source.getFormattedDueDateTime();
    }

    /**
     * Converts this Jackson-friendly adapted issue record object into the model's {@code IssueRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted issue record.
     */
    public IssueRecord toModelType() throws IllegalValueException {
        if (itemId == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "itemId"));
        }
        if (!IssueRecord.isValidItemId(itemId)) {
            throw new IllegalValueException(IssueRecord.MESSAGE_ITEM_ID_CONSTRAINTS);
        }

        if (studentId == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }

        if (dueDateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "dueDateTime"));
        }

        try {
            StudentId modelStudentId = new StudentId(studentId);
            LocalDateTime modelDueDateTime =
                    LocalDateTime.parse(dueDateTime, IssueRecord.DATE_TIME_FORMATTER);
            return new IssueRecord(itemId, modelStudentId, modelDueDateTime);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Issue record date/time must be in yyyy-MM-dd HHmm format.");
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(iae.getMessage());
        }
    }
}
