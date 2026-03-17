package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.ReserveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Parses input arguments and creates a new ReserveCommand object.
 */
public class ReserveCommandParser implements Parser<ReserveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReserveCommand
     * and returns a ReserveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReserveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_FROM, PREFIX_TO)
                || argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FROM, PREFIX_TO);

        String[] preambleParts = argMultimap.getPreamble().trim().split("\\s+");
        if (preambleParts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
        }

        String resourceId = preambleParts[0];
        StudentId studentId = ParserUtil.parseStudentId(preambleParts[1]);
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_FROM).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_TO).get());

        try {
            Reservation reservation = new Reservation(resourceId, studentId, startDateTime, endDateTime);
            return new ReserveCommand(reservation);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage(), iae);
        }
    }

    /**
     * Returns true if all prefixes contain non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
