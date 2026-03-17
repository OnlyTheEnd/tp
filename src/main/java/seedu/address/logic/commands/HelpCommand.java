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

        entries.put("help",
                "Shows possible commands and the format for a command.\n"
                        + "Format: help OR help COMMAND\n"
                        + "Example: help\n"
                        + "Example: help tag");

        entries.put("add-e",
                "Adds a new piece of physical equipment to the inventory.\n"
                        + "Format: add-e n/NAME c/CATEGORY s/STATUS\n"
                        + "Example: add-e n/Wilson-Evolution-Basketball c/Basketball s/Available");

        entries.put("add-r",
                "Registers a new room or facility into the system.\n"
                        + "Format: add-r n/NAME l/LOCATION s/STATUS\n"
                        + "Example: add-r n/MPSH-1 l/Sports-and-Recreation-Centre s/Available");

        entries.put("edit-e",
                "Updates details for an existing equipment.\n"
                        + "Format: edit-e INDEX [n/NAME] [c/CATEGORY] [s/STATUS]\n"
                        + "Example: edit-e 1 s/Booked");

        entries.put("edit-r",
                "Updates details for an existing room.\n"
                        + "Format: edit-r INDEX [n/NAME] [l/LOCATION] [s/STATUS]\n"
                        + "Example: edit-r 3 n/Sports-Hall-1 l/University-Town");

        entries.put("delete-e",
                "Deletes equipment from the inventory.\n"
                        + "Format: delete-e INDEX\n"
                        + "Example: delete-e 3");

        entries.put("delete-r",
                "Deletes a room from the system.\n"
                        + "Format: delete-r INDEX\n"
                        + "Example: delete-r 3");

        entries.put("list-e",
                "Displays the equipment inventory list.\n"
                        + "Format: list-e\n"
                        + "Example: list-e");

        entries.put("list-r",
                "Displays the room list.\n"
                        + "Format: list-r\n"
                        + "Example: list-r");

        entries.put("add-s",
                "Registers a new student borrower profile.\n"
                        + "Format: add-s n/NAME m/MATRIC_NUMBER p/PHONE_NUMBER e/EMAIL\n"
                        + "Example: add-s n/John Doe m/a1234567a p/91234567 e/john@u.nus.edu");

        entries.put("edit-s",
                "Edits a student profile field using the student's matric number.\n"
                        + "Format: edit-s MATRIC_NUMBER [n/NEW_NAME] "
                        + "[m/NEW_MATRIC_NUMBER] [p/NEW_PHONE_NUMBER] [e/NEW_EMAIL]\n"
                        + "Example: edit-s a1234567a p/92345678");

        entries.put("delete-s",
                "Deletes a student profile.\n"
                        + "Format: delete-s MATRIC_NUMBER\n"
                        + "Example: delete-s a1234567a");

        entries.put("check-s",
                "Displays the current loans for a student.\n"
                        + "Format: check-s MATRIC_NUMBER\n"
                        + "Example: check-s a1234567a");

        entries.put("list-s",
                "Displays all registered students.\n"
                        + "Format: list-s\n"
                        + "Example: list-s");

        entries.put("reserve",
                "Reserves an item or room for a student during a specified time range.\n"
                        + "Format: reserve ITEM_OR_ROOM_ID STUDENT_ID f/START_DATE_TIME t/END_DATE_TIME\n"
                        + "Example: reserve Hall-2 a1234567a f/2026-03-10 0900 t/2026-03-10 1200");

        entries.put("issue",
                "Issues an item to a student with a due date/time.\n"
                        + "Format: issue ITEM_ID STUDENT_ID DUE_DATE_TIME\n"
                        + "Example: issue Wilson-Evolution-Basketball-1 a1234567a 2026-03-15 1700");

        entries.put("alias",
                "Assigns a short alias to an equipment or room.\n"
                        + "Format: alias ITEM_OR_ROOM_ID ALIAS_NAME\n"
                        + "Example: alias Wilson-Evolution-Basketball-1 b1");

        entries.put("tag",
                "Gives an equipment or room a tag for categorisation.\n"
                        + "Format: tag l/ROOM_NAME t/TAG_DESCRIPTION\n"
                        + "tag l/MPSH-1 t/renovation\n"
                        + "Example: tag c/EQUIPMENT_NAME t/TAG_DESCRIPTION\n"
                        + "Example: tag c/Wilson-Evolution-Basketball-1 t/spoilt");



        entries.put("untag",
                "Removes a tag from an equipment or room.\n"
                        + "Format: untag l/ROOM_NAME t/TAG_DESCRIPTION\n"
                        + "Format: untag l/MPSH-1 t/renovation\n"
                        + "Example: untag c/EQUIPMENT_NAME t/TAG_DESCRIPTION\n"
                        + "Example: untag c/Wilson-Evolution-Basketball-1 t/spoilt");

        entries.put("filter",
                "Filters equipment/room by tag.\n"
                        + "Format: filter c/ t/TAG\n"
                        + "Format: filter l/ t/tag"
                        + "Example: filter c/ t/spoilt\n"
                        + "filter l/ t/renovation\n");




        return entries;
    }
}
