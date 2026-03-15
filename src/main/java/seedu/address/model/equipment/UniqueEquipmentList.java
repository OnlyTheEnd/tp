package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;

/**
 * A list of equipments that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueEquipmentList implements Iterable<Equipment> {

    private final ObservableList<Equipment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Equipment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent equipment as the given argument.
     */
    public boolean contains(Equipment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEquipment);
    }

    /**
     * Adds an equipment to the list.
     * The equipment must not already exist in the list.
     */
    public void add(Equipment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEquipmentException();
        }
        internalList.add(toAdd);
    }

    public ObservableList<Equipment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Equipment> iterator() {
        return internalList.iterator();
    }
}
