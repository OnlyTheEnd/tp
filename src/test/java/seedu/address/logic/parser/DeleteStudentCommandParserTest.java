package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.model.person.StudentId;

/**
 * Test class for DeleteStudentCommandParser.
 * Validates that matric numbers are parsed correctly and invalid formats are
 * rejected.
 */
public class DeleteStudentCommandParserTest {

    private DeleteStudentCommandParser parser = new DeleteStudentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteStudentCommand() {
        // Valid matric number format: Start alphabet, 7 digits, end alphabet
        StudentId expectedId = AMY.getStudentId(); // a1234567a
        assertParseSuccess(parser, expectedId.value, new DeleteStudentCommand(expectedId));

        // Valid input with surrounding whitespace
        assertParseSuccess(parser, "  a1234567a  ", new DeleteStudentCommand(expectedId));

        // Case insensitivity check
        assertParseSuccess(parser, "A1234567A", new DeleteStudentCommand(expectedId));
        assertParseSuccess(parser, "A1234567a", new DeleteStudentCommand(expectedId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE);

        // Test Case 1: Pure alphabetic input
        assertParseFailure(parser, "abc", expectedMessage);

        // Test Case 2: Pure numeric input
        assertParseFailure(parser, "12345678", expectedMessage);

        // Test Case 3: Empty input
        assertParseFailure(parser, "  ", expectedMessage);

        // Test Case 4: Invalid Matric format (missing ending alphabet)
        assertParseFailure(parser, "A1234567", expectedMessage);

        // Test Case 5: Invalid Matric format (missing starting alphabet)
        assertParseFailure(parser, "10123456X", expectedMessage);

        // Test Case 6: Too many digits
        assertParseFailure(parser, "A012345678X", expectedMessage);

        // Test Case 7: Internal whitespace
        assertParseFailure(parser, "A012 3456X", expectedMessage);

        // Test Case 8: Special characters
        assertParseFailure(parser, "A0123456!", expectedMessage);
    }
}
