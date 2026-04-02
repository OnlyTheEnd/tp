package seedu.address.logic.commands;

import static seedu.address.logic.commands.DeleteEquipmentCommand.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EQUIPMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.equipment.Category;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * Edits the details of an existing equipment.
 */
public class EditEquipmentCommand extends Command {

    public static final String COMMAND_WORD = "edit-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the equipment identified "
            + "by the index number used in the displayed equipment list. "
            + "At least one field to edit must be provided.\n"
            + "Parameters: INDEX(must be a positive integer) "
            + "[n/NAME] [c/CATEGORY] [s/STATUS (Available, Maintenance or Damaged only)]\n"
            + "Example: " + COMMAND_WORD + " 1 s/Maintenance";

    public static final String MESSAGE_EDIT_EQUIPMENT_SUCCESS = "Updated Equipment:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "This equipment already exists in the inventory.";

    private final Index index;
    private final EditEquipmentDescriptor editEquipmentDescriptor;

    /**
     * @param index of the equipment in the filtered equipment list to edit
     * @param editEquipmentDescriptor details to edit the equipment with
     */
    public EditEquipmentCommand(Index index, EditEquipmentDescriptor editEquipmentDescriptor) {
        this.index = index;
        this.editEquipmentDescriptor = new EditEquipmentDescriptor(editEquipmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Equipment> lastShownList = model.getFilteredEquipmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
        }

        Equipment equipmentToEdit = lastShownList.get(index.getZeroBased());

        if (editEquipmentDescriptor.getStatus().isPresent()) {
            EquipmentStatus currentStatus = equipmentToEdit.getStatus();
            EquipmentStatus requestedStatus = editEquipmentDescriptor.getStatus().get();

            if (currentStatus.equals(requestedStatus)) {
                editEquipmentDescriptor.setStatus(null);
            } else {
                validateStatusTransition(currentStatus, requestedStatus);
            }
        }

        Equipment editedEquipment = createEditedEquipment(equipmentToEdit, editEquipmentDescriptor);

        if (equipmentToEdit.equals(editedEquipment)) {
            throw new CommandException("No changes detected (the information provided matches current records).");
        }

        if (!equipmentToEdit.isSameEquipmentName(editedEquipment) && model.hasEquipment(editedEquipment)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }

        model.setEquipment(equipmentToEdit, editedEquipment);
        model.updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_EQUIPMENT_SUCCESS, editedEquipment),
                false, false, true, true, true);
    }

    private static Equipment createEditedEquipment(Equipment equipmentToEdit,
                                                   EditEquipmentDescriptor editEquipmentDescriptor) {
        EquipmentName updatedName = editEquipmentDescriptor.getName().orElse(equipmentToEdit.getName());
        Category updatedCategory = editEquipmentDescriptor.getCategory().orElse(equipmentToEdit.getCategory());
        EquipmentStatus updatedStatus = editEquipmentDescriptor.getStatus().orElse(equipmentToEdit.getStatus());

        return new Equipment(updatedName, updatedCategory, updatedStatus);
    }

    /**
     * Validates the equipment status transition.
     */
    private void validateStatusTransition(EquipmentStatus current, EquipmentStatus requested)
            throws CommandException {
        String currentVal = current.toString().toUpperCase();
        String requestedVal = requested.toString().toUpperCase();

        if (currentVal.equals(requestedVal)) {
            throw new CommandException("Equipment is already in " + current.toString() + ", no status change.");
        }

        if (currentVal.equals("BOOKED")) {
            throw new CommandException("Equipment is currently 'Booked' and its status cannot be edited as it is "
                    + "currently issued or reserved");
        }

        if (currentVal.equals("AVAILABLE")) {
            if (!requestedVal.equals("MAINTENANCE") && !requestedVal.equals("DAMAGED")) {
                throw new CommandException("Equipment in 'Available' status can only be edited to 'Maintenance' or "
                        + "'Damaged'.");
            }
        } else if (currentVal.equals("MAINTENANCE") || currentVal.equals("DAMAGED")) {
            if (!requestedVal.equals("AVAILABLE")) {
                throw new CommandException("Equipment in 'Maintenance' or 'Damaged' status can only be edited to "
                        + "'Available'.");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditEquipmentCommand)) {
            return false;
        }

        // state check
        EditEquipmentCommand e = (EditEquipmentCommand) other;
        return index.equals(e.index)
                && editEquipmentDescriptor.equals(e.editEquipmentDescriptor);
    }

    /**
     * Stores the details to edit the equipment with. Each non-empty field value will replace the
     * corresponding field value of the equipment.
     */
    public static class EditEquipmentDescriptor {
        private EquipmentName name;
        private Category category;
        private EquipmentStatus status;

        public EditEquipmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEquipmentDescriptor(EditEquipmentDescriptor toCopy) {
            setName(toCopy.name);
            setCategory(toCopy.category);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, category, status);
        }

        public void setName(EquipmentName name) {
            this.name = name;
        }

        public Optional<EquipmentName> getName() {
            return Optional.ofNullable(name);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setStatus(EquipmentStatus status) {
            this.status = status;
        }

        public Optional<EquipmentStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditEquipmentDescriptor)) {
                return false;
            }

            EditEquipmentDescriptor e = (EditEquipmentDescriptor) other;

            return getName().equals(e.getName())
                    && getCategory().equals(e.getCategory())
                    && getStatus().equals(e.getStatus());
        }
    }
}
