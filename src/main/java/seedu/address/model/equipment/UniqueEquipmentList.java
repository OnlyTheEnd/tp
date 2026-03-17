package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.address.model.equipment.exceptions.EquipmentNotFoundException;

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

    /**
     * Removes the equivalent equipment from the list.
     * The equipment must exist in the list.
     */
    public void remove(Equipment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EquipmentNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate equipment.
     */
    public void setEquipments(List<Equipment> replacement) {
        requireNonNull(replacement);
        if (!equipmentsAreUnique(replacement)) {
            throw new DuplicateEquipmentException();
        }

        internalList.setAll(replacement);
    }

    /**
     * Returns true if {@code equipments} contains only unique equipment.
     */
    private boolean equipmentsAreUnique(List<Equipment> equipments) {
        for (int i = 0; i < equipments.size() - 1; i++) {
            for (int j = i + 1; j < equipments.size(); j++) {
                if (equipments.get(i).isSameEquipment(equipments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ObservableList<Equipment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Equipment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueEquipmentList
                && internalList.equals(((UniqueEquipmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
