package seedu.address.testutil;

import seedu.address.logic.commands.EditEquipmentCommand.EditEquipmentDescriptor;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * A utility class to help with building EditEquipmentDescriptor objects.
 */
public class EditEquipmentDescriptorBuilder {

    private EditEquipmentDescriptor descriptor;

    public EditEquipmentDescriptorBuilder() {
        descriptor = new EditEquipmentDescriptor();
    }

    public EditEquipmentDescriptorBuilder(EditEquipmentDescriptor descriptor) {
        this.descriptor = new EditEquipmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEquipmentDescriptor} with fields containing {@code equipment}'s details
     */
    public EditEquipmentDescriptorBuilder(Equipment equipment) {
        descriptor = new EditEquipmentDescriptor();
        descriptor.setName(equipment.getName());
        descriptor.setCategory(equipment.getCategory());
        descriptor.setStatus(equipment.getStatus());
    }

    /**
     * Sets the {@code EquipmentName} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withName(String name) {
        descriptor.setName(new EquipmentName(name));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(category);
        return this;
    }

    /**
     * Sets the {@code EquipmentStatus} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withStatus(EquipmentStatus status) {
        descriptor.setStatus(status);
        return this;
    }

    public EditEquipmentDescriptor build() {
        return descriptor;
    }
}
