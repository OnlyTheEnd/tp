package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EQUIPMENTS;

import seedu.address.model.Model;

/**
 * Lists all equipment in the address book to the user.
 */
public class ListEquipmentCommand extends Command {

    public static final String COMMAND_WORD = "list-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all equipment.\n"
            + "Format: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all equipment";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
        return new CommandResult(MESSAGE_SUCCESS,
                false, false, true, true, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListEquipmentCommand);
    }

    @Override
    public int hashCode() {
        return ListEquipmentCommand.class.hashCode();
    }
}
