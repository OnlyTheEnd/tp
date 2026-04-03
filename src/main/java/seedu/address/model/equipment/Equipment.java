package seedu.address.model.equipment;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;




/**
 * Represents a Equipment in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Equipment extends Taggable {
    private final EquipmentName name;
    private final Category category;
    private final EquipmentStatus status;


    /**
     * Every field must be present and not null.
     */
    public Equipment(EquipmentName name, Category category, EquipmentStatus status) {
        requireAllNonNull(name, category, status);
        this.name = name;
        this.category = category;
        this.status = status;
    }

    /**
     * For checking a equipmentName is inside the equipmentList
     * @param name A valid room name.
     */
    public Equipment(EquipmentName name) {
        this.name = name;
        this.category = Category.java_parse("placeholder");
        this.status = EquipmentStatus.java_parse("Available");
    }

    /**
     * For modelEquipment creation
     * @param name
     * @param category
     * @param status
     * @param tags
     */
    public Equipment(EquipmentName name, Category category, EquipmentStatus status, Set<Tag> tags) {
        requireAllNonNull(name, category, status);
        this.name = name;
        this.category = category;
        this.status = status;
        this.tags.addAll(tags);
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
                && otherEquipment.getName().equals(getName()); // EquipmentName.equals handles case
    }

    /**
     * Returns true if both Equipment have the same name.
     * This defines a weaker notion of equality between two equipments (used for duplicate checking).
     */
    public boolean isSameEquipmentName(Equipment otherEquipment) {
        if (otherEquipment == this) {
            return true;
        }
        return otherEquipment != null && otherEquipment.getName().equals(getName());
    }


    public EquipmentName getName() {
        return name;
    }

    public Category getCategory() {
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

    /**
     * Adds tag from tagList on Equipment and on TaggedEntries record
     * @param tag A valid tag
     */
    public void addTag(Tag tag) {
        tags.add(tag);
        registerTag(this.getClass().getSimpleName(), this.getName().toString(), tag.toString());
    }

    /**
     * Removes tag from tagList on Equipment and on TaggedEntries record
     * @param otherTag A valid Tag
     */
    public void deleteTag(Tag otherTag) {
        tags.removeIf(tag -> tag.equals(otherTag));
        removeTag(this.getClass().getSimpleName(), this.getName().toString(), otherTag.toString());
    }


    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, category, status);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]",
                name.toString(),
                category,
                status.toString());
    }

    @Override
    public String getNameString() {
        return this.name.toString();
    }
}
