package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EQUIPMENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all equipment in the address book to the user.
 */
public class ListEquipmentCommand extends Command {

    public static final String COMMAND_WORD = "list-e";

    public static final String MESSAGE_SUCCESS = "Listed all equipment";
    public static final String MESSAGE_EMPTY_LIST = "Equipment List has not been created. Please proceed to add "
            + "equipment first.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);

        if (model.getFilteredEquipmentList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                false, false, false, false, true);
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
