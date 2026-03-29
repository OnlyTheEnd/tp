package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.CancelReservationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Parses input arguments and creates a new CancelReservationCommand object.
 */
public class CancelReservationCommandParser implements Parser<CancelReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelReservationCommand
     * and returns a CancelReservationCommand object for execution.
     *
     * @param args Full user input arguments.
     * @return A CancelReservationCommand containing the parsed reservation identity.
     * @throws ParseException If the user input does not conform to the expected format,
     *         or if any parsed value violates the constraints of the model.
     */
    @Override
    public CancelReservationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_FROM)
                || argMultimap.getValue(PREFIX_TO).isPresent()
                || argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CancelReservationCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FROM, PREFIX_TO);

        String[] preambleParts = argMultimap.getPreamble().trim().split("\\s+");
        if (preambleParts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CancelReservationCommand.MESSAGE_USAGE));
        }

        String resourceId = preambleParts[0];
        if (!Reservation.isValidResourceId(resourceId)) {
            throw new ParseException(Reservation.MESSAGE_RESOURCE_ID_CONSTRAINTS);
        }

        StudentId studentId = ParserUtil.parseStudentId(preambleParts[1]);
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_FROM).get());

        return new CancelReservationCommand(resourceId, studentId, startDateTime);
    }

    /**
     * Returns true if all prefixes contain non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
