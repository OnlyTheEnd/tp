package seedu.address.model.equipment.exceptions;

/**
 * Signals that the operation will result in duplicate Equipment.
 */
public class DuplicateEquipmentException extends RuntimeException {
    public DuplicateEquipmentException() {
        super("Operation would result in duplicate equipment");
    }
}
