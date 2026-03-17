package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CheckStudentLoansCommand;
import seedu.address.model.person.StudentId;

public class CheckStudentLoansCommandParserTest {

    private static final String VALID_STUDENT_ID = "a1234567a";
    private final CheckStudentLoansCommandParser parser = new CheckStudentLoansCommandParser();

    @Test
    public void parse_validArgs_returnsCheckStudentLoansCommand() {
        // Test standard valid input
        assertParseSuccess(parser, VALID_STUDENT_ID,
                new CheckStudentLoansCommand(new StudentId(VALID_STUDENT_ID)));

        // Test case insensitivity and whitespace handling
        assertParseSuccess(parser, "  A1234567A  ",
                new CheckStudentLoansCommand(new StudentId(VALID_STUDENT_ID)));
    }

    @Test
    public void parse_invalidStudentId_failure() {
        // Missing starting alphabet
        assertParseFailure(parser, "1234567A", StudentId.MESSAGE_CONSTRAINTS);

        // Missing trailing alphabet
        assertParseFailure(parser, "A1234567", StudentId.MESSAGE_CONSTRAINTS);

        // Incorrect number of digits
        assertParseFailure(parser, "A123456A", StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyStudentId_failure() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckStudentLoansCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckStudentLoansCommand.MESSAGE_USAGE));
    }
}
