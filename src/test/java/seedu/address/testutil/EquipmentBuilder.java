package seedu.address.testutil;

import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * A utility class to help with building Equipment objects.
 */
public class EquipmentBuilder {

    public static final String DEFAULT_NAME = "Wilson-Evolution";
    public static final String DEFAULT_CATEGORY = "Baksetball";
    public static final EquipmentStatus DEFAULT_STATUS = EquipmentStatus.AVAILABLE;

    private EquipmentName name;
    private String category;
    private EquipmentStatus status;

    /**
     * Creates a {@code EquipmentBuilder} with the default details.
     */
    public EquipmentBuilder() {
        name = new EquipmentName(DEFAULT_NAME);
        category = DEFAULT_CATEGORY;
        status = DEFAULT_STATUS;
    }

    /**
     * Initializes the EquipmentBuilder with the data of {@code equipmentToCopy}.
     */
    public EquipmentBuilder(Equipment equipmentToCopy) {
        name = equipmentToCopy.getName();
        category = equipmentToCopy.getCategory();
        status = equipmentToCopy.getStatus();
    }

    /**
     * Sets the {@code EquipmentName} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withName(String name) {
        this.name = new EquipmentName(name);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the {@code EquipmentStatus} of the {@code Equipment} that we are building.
     */
    public EquipmentBuilder withStatus(EquipmentStatus status) {
        this.status = status;
        return this;
    }

    public Equipment build() {
        return new Equipment(name, category, status);
    }
}
