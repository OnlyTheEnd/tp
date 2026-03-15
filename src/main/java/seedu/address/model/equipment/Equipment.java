package seedu.address.model.equipment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Equipment in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Equipment {
    private final EquipmentName name;
    private final String category;
    private final EquipmentStatus status;

    /**
     * Every field must be present and not null.
     */
    public Equipment(EquipmentName name, String category, EquipmentStatus status) {
        requireAllNonNull(name, category, status);
        this.name = name;
        this.category = category;
        this.status = status;
    }

    /**
     * Returns true if both Equipment have the same name.
     * This defines a weaker notion of equality between two equipments (used for duplicate checking).
     */
    public boolean isSameEquipment(Equipment otherEquipment) {
        if (otherEquipment == this) {
            return true;
        }

        return otherEquipment != null
                && otherEquipment.getName().equals(getName()) // EquipmentName.equals handles case
                && otherEquipment.getCategory().equalsIgnoreCase(getCategory());
    }

    public EquipmentName getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Equipment)) {
            return false;
        }

        Equipment otherEquipment = (Equipment) other;
        return name.equals(otherEquipment.getName())
                && category.equals(otherEquipment.getCategory())
                && status.equals(otherEquipment.getStatus());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, category, status);
    }
}
