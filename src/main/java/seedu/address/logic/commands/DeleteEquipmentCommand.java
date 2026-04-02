package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.equipment.Equipment;

/**
 * Deletes a equipment identified using it's displayed index from the address book.
 */
public class DeleteEquipmentCommand extends Command {
    public static final String COMMAND_WORD = "delete-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the equipment identified by the index number used in the displayed equipment list.\n"
            + "Parameters: INDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EQUIPMENT_SUCCESS = "Deleted Equipment: %1$s";
    public static final String MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX = "The equipment index provided is invalid";

    private final Index targetIndex;

    public DeleteEquipmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Equipment> lastShownList = model.getFilteredEquipmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
        }

        Equipment equipmentToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!equipmentToDelete.getStatus().toString().equalsIgnoreCase("Available")) {
            throw new CommandException(String.format(
                    "Equipment is currently %1$s. Only allowed to be deleted when it is 'Available'.",
                    equipmentToDelete.getStatus().toString()));
        }

        model.deleteEquipment(equipmentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EQUIPMENT_SUCCESS, equipmentToDelete),
                false, false, true, true, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteEquipmentCommand
                && targetIndex.equals(((DeleteEquipmentCommand) other).targetIndex));
    }
}
