package seedu.address.model.equipment.exceptions;

/**
 * Signals that the operation is unable to find the specified equipment.
 */
public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException() {
        super("Operation unable to find the specified equipment");
    }
}
