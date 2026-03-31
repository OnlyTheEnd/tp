package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows usage information for all commands or for a specific command.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows possible commands and their formats.\n"
            + "Format: help OR help COMMAND\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " tag";

    public static final String MESSAGE_COMMAND_NOT_FOUND = "FAILURE! %1$s COMMAND NOT FOUND";
    public static final String MESSAGE_COMMAND_FOUND = "SUCCESS! %1$s COMMAND FOUND";

    private static final Map<String, String> HELP_ENTRIES = createHelpEntries();

    private final Optional<String> commandTopic;

    /**
     * Creates a {@code HelpCommand} that shows help for all commands.
     */
    public HelpCommand() {
        this.commandTopic = Optional.empty();
    }

    /**
     * Creates a {@code HelpCommand} that shows help for a specific command.
     *
     * @param commandTopic Command word to show help for.
     */
    public HelpCommand(String commandTopic) {
        requireNonNull(commandTopic);
        this.commandTopic = Optional.of(normalizeCommandWord(commandTopic));
    }

    /**
     * Executes the help command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result containing either the full command list or the detailed help
     *         for one command.
     * @throws CommandException If the requested command does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (commandTopic.isEmpty()) {
            return new CommandResult(buildGeneralHelpMessage());
        }

        String topic = commandTopic.get();
        String helpText = HELP_ENTRIES.get(topic);
        if (helpText == null) {
            throw new CommandException(String.format(MESSAGE_COMMAND_NOT_FOUND, topic.toUpperCase(Locale.ROOT)));
        }

        return new CommandResult(String.format(MESSAGE_COMMAND_FOUND, topic.toUpperCase(Locale.ROOT))
                + System.lineSeparator()
                + helpText);
    }

    /**
     * Returns true if both help commands request help for the same topic.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelpCommand)) {
            return false;
        }
        HelpCommand otherHelpCommand = (HelpCommand) other;
        return commandTopic.equals(otherHelpCommand.commandTopic);
    }

    private static String normalizeCommandWord(String commandWord) {
        return commandWord.trim().toLowerCase(Locale.ROOT);
    }

    private static String buildGeneralHelpMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Available commands:").append(System.lineSeparator());

        for (Map.Entry<String, String> entry : HELP_ENTRIES.entrySet()) {
            String firstLine = entry.getValue().split("\\R", 2)[0];
            builder.append("- ").append(entry.getKey()).append(": ").append(firstLine)
                    .append(System.lineSeparator());
        }

        builder.append(System.lineSeparator())
                .append("Use \"help COMMAND\" to view the full format and description of a specific command.");
        return builder.toString();
    }

    private static Map<String, String> createHelpEntries() {
        Map<String, String> entries = new LinkedHashMap<>();

        entries.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);

        entries.put(AddEquipmentCommand.COMMAND_WORD, AddEquipmentCommand.MESSAGE_USAGE);
        entries.put(AddRoomCommand.COMMAND_WORD, AddRoomCommand.MESSAGE_USAGE);
        entries.put(AddStudentCommand.COMMAND_WORD, AddStudentCommand.MESSAGE_USAGE);

        entries.put(EditEquipmentCommand.COMMAND_WORD, EditEquipmentCommand.MESSAGE_USAGE);
        entries.put(EditRoomCommand.COMMAND_WORD, EditRoomCommand.MESSAGE_USAGE);
        entries.put(EditStudentCommand.COMMAND_WORD, EditStudentCommand.MESSAGE_USAGE);

        entries.put(DeleteEquipmentCommand.COMMAND_WORD, DeleteEquipmentCommand.MESSAGE_USAGE);
        entries.put(DeleteRoomCommand.COMMAND_WORD, DeleteRoomCommand.MESSAGE_USAGE);
        entries.put(DeleteStudentCommand.COMMAND_WORD, DeleteStudentCommand.MESSAGE_USAGE);

        entries.put(ListStudentCommand.COMMAND_WORD, ListStudentCommand.MESSAGE_USAGE);
        entries.put(ListRoomCommand.COMMAND_WORD, ListRoomCommand.MESSAGE_USAGE);

        entries.put(CheckStudentLoansCommand.COMMAND_WORD, CheckStudentLoansCommand.MESSAGE_USAGE);

        entries.put(ReserveCommand.COMMAND_WORD, ReserveCommand.MESSAGE_USAGE);
        entries.put(CancelReservationCommand.COMMAND_WORD, CancelReservationCommand.MESSAGE_USAGE);

        entries.put(IssueCommand.COMMAND_WORD, IssueCommand.MESSAGE_USAGE);
        entries.put(ReturnCommand.COMMAND_WORD, ReturnCommand.MESSAGE_USAGE);

        entries.put(AliasCommand.COMMAND_WORD, AliasCommand.MESSAGE_USAGE);
        entries.put(AddTagCommand.COMMAND_WORD, AddTagCommand.MESSAGE_USAGE);
        entries.put(DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.MESSAGE_USAGE);
        entries.put(FilterTagCommand.COMMAND_WORD, FilterTagCommand.MESSAGE_USAGE);

        return entries;
    }
}
