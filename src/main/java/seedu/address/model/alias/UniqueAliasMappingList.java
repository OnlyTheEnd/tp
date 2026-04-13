package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

/**
 * A list of alias mappings that enforces uniqueness between alias names.
 */
public class UniqueAliasMappingList implements Iterable<AliasMapping> {

    private final ObservableList<AliasMapping> internalList = FXCollections.observableArrayList();
    private final ObservableList<AliasMapping> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an alias mapping with the same alias name as {@code toCheck}.
     */
    public boolean contains(AliasMapping toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAlias);
    }

    /**
     * Returns true if the list contains {@code aliasName}.
     */
    public boolean hasAliasName(String aliasName) {
        requireNonNull(aliasName);
        String normalizedAlias = AliasMapping.normalizeAliasName(aliasName);
        return internalList.stream().anyMatch(alias -> alias.getAliasName().equals(normalizedAlias));
    }

    /**
     * Returns the alias mapping associated with {@code aliasName}, if any.
     */
    public Optional<AliasMapping> getAliasMappingByName(String aliasName) {
        requireNonNull(aliasName);
        String normalizedAlias = AliasMapping.normalizeAliasName(aliasName);
        return internalList.stream().filter(alias -> alias.getAliasName().equals(normalizedAlias)).findFirst();
    }

    /**
     * Adds an alias mapping to the list.
     *
     * @throws DuplicateAliasException if the alias name already exists in the list.
     */
    public void add(AliasMapping toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAliasException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes all alias mappings that point to {@code targetId}.
     */
    public void removeByTargetId(String targetId) {
        requireNonNull(targetId);
        String normalizedTargetId = AliasMapping.normalizeTargetId(targetId);
        internalList.removeIf(alias -> alias.getTargetId().equals(normalizedTargetId));
    }

    /**
     * Replaces the contents of this list with {@code aliasMappings}.
     */
    public void setAliasMappings(List<AliasMapping> aliasMappings) {
        requireAllNonNull(aliasMappings);
        if (!aliasMappingsAreUnique(aliasMappings)) {
            throw new DuplicateAliasException();
        }
        internalList.setAll(aliasMappings);
    }

    /**
     * Returns an unmodifiable view of the alias mapping list.
     */
    public ObservableList<AliasMapping> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    private boolean aliasMappingsAreUnique(List<AliasMapping> aliasMappings) {
        for (int i = 0; i < aliasMappings.size(); i++) {
            for (int j = i + 1; j < aliasMappings.size(); j++) {
                if (aliasMappings.get(i).isSameAlias(aliasMappings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<AliasMapping> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueAliasMappingList)) {
            return false;
        }
        UniqueAliasMappingList otherList = (UniqueAliasMappingList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
