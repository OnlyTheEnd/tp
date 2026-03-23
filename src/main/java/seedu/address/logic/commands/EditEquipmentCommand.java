package seedu.address.logic.commands;

import static seedu.address.logic.commands.DeleteEquipmentCommand.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EQUIPMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
            + "Parameters: INDEX (must be a positive integer) "
            + "[n/NAME] [c/CATEGORY] [s/STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 s/Booked";

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
        Equipment editedEquipment = createEditedEquipment(equipmentToEdit, editEquipmentDescriptor);

        if (!equipmentToEdit.isSameEquipment(editedEquipment) && model.hasEquipment(editedEquipment)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }

        model.setEquipment(equipmentToEdit, editedEquipment);
        model.updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_EQUIPMENT_SUCCESS, editedEquipment),
                false, false, false, false, true);
    }

    private static Equipment createEditedEquipment(Equipment equipmentToEdit,
                                                   EditEquipmentDescriptor editEquipmentDescriptor) {
        EquipmentName updatedName = editEquipmentDescriptor.getName().orElse(equipmentToEdit.getName());
        String updatedCategory = editEquipmentDescriptor.getCategory().orElse(equipmentToEdit.getCategory());
        EquipmentStatus updatedStatus = editEquipmentDescriptor.getStatus().orElse(equipmentToEdit.getStatus());

        return new Equipment(updatedName, updatedCategory, updatedStatus);
    }

    /**
     * Stores the details to edit the equipment with. Each non-empty field value will replace the
     * corresponding field value of the equipment.
     */
    public static class EditEquipmentDescriptor {
        private EquipmentName name;
        private String category;
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

        public void setCategory(String category) {
            this.category = category;
        }

        public Optional<String> getCategory() {
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
