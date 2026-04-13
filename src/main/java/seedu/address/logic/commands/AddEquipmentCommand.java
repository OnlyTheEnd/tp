package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.equipment.Equipment;

/**
 * Adds a equipment to the address book.
 */
public class AddEquipmentCommand extends Command {
    public static final String COMMAND_WORD = "add-e";
    public static final String MESSAGE_SUCCESS = "New Equipment Added:\nName: %1$s | Category: %2$s | Status: %3$s";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "This equipment already exists in the inventory. "
            + "Please use a unique name (e.g., Wilson-Evolution-1).";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a piece of equipment to the inventory. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Wilson-Evolution "
            + PREFIX_CATEGORY + "Basketball";

    private final Equipment toAdd;

    /**
     * Creates an AddEquipmentCommand to add the specified {@code Equipment}
     */
    public AddEquipmentCommand(Equipment equipment) {
        requireNonNull(equipment);
        toAdd = equipment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasEquipment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }
        model.addEquipment(toAdd);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd.getName().toString(), toAdd.getCategory().toString(),
                        toAdd.getStatus().toString()),
                false, false, true, true, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEquipmentCommand)) {
            return false;
        }

        AddEquipmentCommand otherAddEquipmentCommand = (AddEquipmentCommand) other;
        return toAdd.equals(otherAddEquipmentCommand.toAdd);
    }

    @Override
    public int hashCode() {
        return toAdd.hashCode();
    }
}
