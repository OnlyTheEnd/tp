package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReserveCommand;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

public class ReserveCommandParserTest {

    private static final String VALID_RESOURCE_ID = "Hall-2";
    private static final String VALID_STUDENT_ID = "a1234567a";
    private static final String VALID_START = "2026-03-01 1400";
    private static final String VALID_END = "2026-03-01 1600";

    private static final String RESOURCE_AND_STUDENT = VALID_RESOURCE_ID + " " + VALID_STUDENT_ID;
    private static final String START_DESC = " f/" + VALID_START;
    private static final String END_DESC = " t/" + VALID_END;

    private final ReserveCommandParser parser = new ReserveCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reservation reservation = new Reservation(VALID_RESOURCE_ID, new StudentId(VALID_STUDENT_ID),
                LocalDateTime.of(2026, 3, 1, 14, 0),
                LocalDateTime.of(2026, 3, 1, 16, 0));

        assertParseSuccess(parser, " " + RESOURCE_AND_STUDENT + START_DESC + END_DESC,
                new ReserveCommand(reservation));
    }

    @Test
    public void parse_missingStartPrefix_failure() {
        assertParseFailure(parser, " " + RESOURCE_AND_STUDENT + END_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingEndPrefix_failure() {
        assertParseFailure(parser, " " + RESOURCE_AND_STUDENT + START_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentId_failure() {
        assertParseFailure(parser, " " + VALID_RESOURCE_ID + " 12345678" + START_DESC + END_DESC,
                StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStartDateTime_failure() {
        assertParseFailure(parser,
                " " + RESOURCE_AND_STUDENT + " f/2026/03/01 1400" + END_DESC,
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_invalidEndDateTime_failure() {
        assertParseFailure(parser,
                " " + RESOURCE_AND_STUDENT + START_DESC + " t/2026/03/01 1600",
                ParserUtil.MESSAGE_INVALID_DATE_TIME);
    }

    @Test
    public void parse_endBeforeStart_failure() {
        assertParseFailure(parser,
                " " + RESOURCE_AND_STUDENT + " f/2026-03-01 1600 t/2026-03-01 1400",
                Reservation.MESSAGE_TIME_RANGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, " " + VALID_RESOURCE_ID + START_DESC + END_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
    }
}
