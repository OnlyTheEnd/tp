package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddEquipmentCommand;
import seedu.address.logic.commands.AddRoomCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.CancelReservationCommand;
import seedu.address.logic.commands.CheckStudentLoansCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteEquipmentCommand;
import seedu.address.logic.commands.DeleteRoomCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditEquipmentCommand;
import seedu.address.logic.commands.EditRoomCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IssueCommand;
import seedu.address.logic.commands.ListEquipmentCommand;
import seedu.address.logic.commands.ListRoomCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.commands.ReserveCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not meet the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case AddRoomCommand.COMMAND_WORD:
            return new AddRoomCommandParser().parse(arguments);

        case ListRoomCommand.COMMAND_WORD:
            return new ListRoomCommandParser().parse(arguments);

        case EditRoomCommand.COMMAND_WORD:
            return new EditRoomCommandParser().parse(arguments);

        case DeleteRoomCommand.COMMAND_WORD:
            return new DeleteRoomCommandParser().parse(arguments);

        case AddEquipmentCommand.COMMAND_WORD:
            return new AddEquipmentCommandParser().parse(arguments);

        case ListEquipmentCommand.COMMAND_WORD:
            return new ListEquipmentCommandParser().parse(arguments);

        case EditEquipmentCommand.COMMAND_WORD:
            return new EditEquipmentCommandParser().parse(arguments);

        case DeleteEquipmentCommand.COMMAND_WORD:
            return new DeleteEquipmentCommandParser().parse(arguments);

        case ReserveCommand.COMMAND_WORD:
            return new ReserveCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case IssueCommand.COMMAND_WORD:
            return new IssueCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse("room" + arguments);

        case AddTagCommand.COMMAND_WORD2:
            return new AddTagCommandParser().parse("equipment" + arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse("room" + arguments);

        case DeleteTagCommand.COMMAND_WORD2:
            return new DeleteTagCommandParser().parse("equipment" + arguments);

        case FilterTagCommand.COMMAND_WORD:
            return new FilterTagCommandParser().parse("room" + arguments);

        case FilterTagCommand.COMMAND_WORD2:
            return new FilterTagCommandParser().parse("equipment" + arguments);

        case CheckStudentLoansCommand.COMMAND_WORD:
            return new CheckStudentLoansCommandParser().parse(arguments);

        case ReturnCommand.COMMAND_WORD:
            return new ReturnCommandParser().parse(arguments);

        case CancelReservationCommand.COMMAND_WORD:
            return new CancelReservationCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
