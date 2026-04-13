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

    /**
     * EP: Valid class — valid Student ID format (1 letter, 7 digits, 1 letter).
     * BVA: Input with surrounding whitespace and different casing should be handled correctly.
     */
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

    /**
     * EP: Invalid class — non-alphanumeric, missing components, or incorrect length.
     * BVA: Empty/blank strings and IDs with extra digits are tested at the boundaries.
     */
    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE);

        // EP: Pure alphabetic input
        assertParseFailure(parser, "abc", expectedMessage);

        // EP: Pure numeric input
        assertParseFailure(parser, "12345678", expectedMessage);

        // BVA: Empty input
        assertParseFailure(parser, "  ", expectedMessage);

        // EP: Invalid Matric format (missing ending alphabet)
        assertParseFailure(parser, "A1234567", expectedMessage);

        // EP: Invalid Matric format (missing starting alphabet)
        assertParseFailure(parser, "10123456X", expectedMessage);

        // BVA: Too many digits
        assertParseFailure(parser, "A012345678X", expectedMessage);

        // EP: Internal whitespace
        assertParseFailure(parser, "A012 3456X", expectedMessage);

        // EP: Special characters
        assertParseFailure(parser, "A0123456!", expectedMessage);
    }
}
