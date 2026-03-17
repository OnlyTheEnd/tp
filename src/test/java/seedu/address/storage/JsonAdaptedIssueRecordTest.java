package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;

public class JsonAdaptedIssueRecordTest {

    private static final String VALID_ITEM_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_STUDENT_ID = "a1234567a";
    private static final String VALID_DUE_DATE_TIME = "2099-03-15 1700";
    private static final String INVALID_ITEM_ID = "!invalid";
    private static final String INVALID_STUDENT_ID = "12345678";
    private static final String INVALID_DATE_TIME = "2099/03/15 1700";

    private static final IssueRecord VALID_ISSUE_RECORD = new IssueRecord(VALID_ITEM_ID,
            new StudentId(VALID_STUDENT_ID),
            LocalDateTime.of(2099, 3, 15, 17, 0));

    @Test
    public void toModelType_validIssueRecordDetails_returnsIssueRecord() throws Exception {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(VALID_ITEM_ID, VALID_STUDENT_ID, VALID_DUE_DATE_TIME);

        assertEquals(VALID_ISSUE_RECORD, issueRecord.toModelType());
    }

    @Test
    public void toModelType_invalidItemId_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(INVALID_ITEM_ID, VALID_STUDENT_ID, VALID_DUE_DATE_TIME);

        assertThrows(IllegalValueException.class, IssueRecord.MESSAGE_ITEM_ID_CONSTRAINTS,
                issueRecord::toModelType);
    }

    @Test
    public void toModelType_nullItemId_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(null, VALID_STUDENT_ID, VALID_DUE_DATE_TIME);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedIssueRecord.MISSING_FIELD_MESSAGE_FORMAT, "itemId"),
                issueRecord::toModelType);
    }

    @Test
    public void toModelType_invalidStudentId_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(VALID_ITEM_ID, INVALID_STUDENT_ID, VALID_DUE_DATE_TIME);

        assertThrows(IllegalValueException.class, StudentId.MESSAGE_CONSTRAINTS,
                issueRecord::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(VALID_ITEM_ID, null, VALID_DUE_DATE_TIME);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedIssueRecord.MISSING_FIELD_MESSAGE_FORMAT,
                        StudentId.class.getSimpleName()),
                issueRecord::toModelType);
    }

    @Test
    public void toModelType_invalidDueDateTime_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(VALID_ITEM_ID, VALID_STUDENT_ID, INVALID_DATE_TIME);

        assertThrows(IllegalValueException.class,
                "Issue record date/time must be in yyyy-MM-dd HHmm format.",
                issueRecord::toModelType);
    }

    @Test
    public void toModelType_nullDueDateTime_throwsIllegalValueException() {
        JsonAdaptedIssueRecord issueRecord =
                new JsonAdaptedIssueRecord(VALID_ITEM_ID, VALID_STUDENT_ID, null);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedIssueRecord.MISSING_FIELD_MESSAGE_FORMAT, "dueDateTime"),
                issueRecord::toModelType);
    }
}
