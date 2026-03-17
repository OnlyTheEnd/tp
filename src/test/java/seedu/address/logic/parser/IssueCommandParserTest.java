package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.IssueCommand;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;

public class IssueCommandParserTest {

    private static final String VALID_ITEM_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_STUDENT_ID = "a1234567a";
    private static final String VALID_DUE_DATE = "2099-03-15";
    private static final String VALID_DUE_TIME = "1700";

    private final IssueCommandParser parser = new IssueCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        IssueRecord issueRecord = new IssueRecord(VALID_ITEM_ID, new StudentId(VALID_STUDENT_ID),
                LocalDateTime.of(2099, 3, 15, 17, 0));

        assertParseSuccess(parser,
                " " + VALID_ITEM_ID + " " + VALID_STUDENT_ID + " " + VALID_DUE_DATE + " " + VALID_DUE_TIME,
                new IssueCommand(issueRecord));
    }

    @Test
    public void parse_missingFields_failure() {
        assertParseFailure(parser,
                " " + VALID_ITEM_ID + " " + VALID_STUDENT_ID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentId_failure() {
        assertParseFailure(parser,
                " " + VALID_ITEM_ID + " 12345678 " + VALID_DUE_DATE + " " + VALID_DUE_TIME,
                StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDueDateTime_failure() {
        assertParseFailure(parser,
                " " + VALID_ITEM_ID + " " + VALID_STUDENT_ID + " 2099/03/15 1700",
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_dueDateTimeInPast_failure() {
        assertParseFailure(parser,
                " " + VALID_ITEM_ID + " " + VALID_STUDENT_ID + " 2000-03-15 1700",
                IssueRecord.MESSAGE_DUE_DATE_TIME_CONSTRAINTS);
    }
}
