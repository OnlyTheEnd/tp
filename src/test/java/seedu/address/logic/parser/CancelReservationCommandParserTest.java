package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CancelReservationCommand;
import seedu.address.model.person.StudentId;

/**
 * Tests for {@link CancelReservationCommandParser}.
 */
public class CancelReservationCommandParserTest {

    private final CancelReservationCommandParser parser = new CancelReservationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                " Hall-2 a1234567a f/2099-03-15 0900",
                new CancelReservationCommand(
                        "Hall-2",
                        new StudentId("a1234567a"),
                        LocalDateTime.of(2099, 3, 15, 9, 0)));
    }

    @Test
    public void parse_missingStartDateTime_failure() {
        assertParseFailure(parser,
                " Hall-2 a1234567a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelReservationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_oldSyntaxWithEndDateTime_failure() {
        assertParseFailure(parser,
                " Hall-2 a1234567a f/2099-03-15 0900 t/2099-03-15 1100",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelReservationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentId_failure() {
        assertParseFailure(parser,
                " Hall-2 invalid f/2099-03-15 0900",
                StudentId.MESSAGE_CONSTRAINTS);
    }
}
