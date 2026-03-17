package seedu.address.model.issue;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.issue.exceptions.DuplicateIssueRecordException;
import seedu.address.model.issue.exceptions.ItemAlreadyIssuedException;

/**
 * A list of issue records that enforces uniqueness between its elements and ensures
 * that a single item cannot be issued more than once at the same time.
 *
 * <p>An issue record is considered a duplicate if it is equal to an existing record.
 * An item is considered already issued if another record in the list refers to the same item ID.</p>
 */
public class UniqueIssueRecordList implements Iterable<IssueRecord> {

    private final ObservableList<IssueRecord> internalList = FXCollections.observableArrayList();
    private final ObservableList<IssueRecord> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an issue record equal to {@code toCheck}.
     */
    public boolean contains(IssueRecord toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the given item is currently issued.
     *
     * @param itemId The item ID to check.
     * @return True if an issue record for the item exists in the list.
     */
    public boolean hasIssuedItem(String itemId) {
        requireNonNull(itemId);
        String normalizedItemId = IssueRecord.normalizeItemId(itemId);
        return internalList.stream().anyMatch(record -> record.getItemId().equals(normalizedItemId));
    }

    /**
     * Returns the issue record associated with the given item ID, if any.
     *
     * @param itemId The item ID to search for.
     * @return An {@code Optional} containing the matching issue record, or empty if none exists.
     */
    public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
        requireNonNull(itemId);
        String normalizedItemId = IssueRecord.normalizeItemId(itemId);
        return internalList.stream().filter(record -> record.getItemId().equals(normalizedItemId)).findFirst();
    }

    /**
     * Adds an issue record to the list.
     *
     * @param toAdd The issue record to add.
     * @throws DuplicateIssueRecordException If the exact issue record already exists in the list.
     * @throws ItemAlreadyIssuedException If the item in the issue record is already issued.
     */
    public void add(IssueRecord toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIssueRecordException();
        }
        if (hasIssuedItem(toAdd.getItemId())) {
            throw new ItemAlreadyIssuedException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code issueRecords}.
     *
     * @param issueRecords The new list of issue records.
     * @throws DuplicateIssueRecordException If the given list contains duplicate records
     *         or multiple records for the same item.
     */
    public void setIssueRecords(List<IssueRecord> issueRecords) {
        requireAllNonNull(issueRecords);
        if (!issueRecordsAreUniqueAndNoDuplicateItems(issueRecords)) {
            throw new DuplicateIssueRecordException();
        }
        internalList.setAll(issueRecords);
    }

    /**
     * Returns an unmodifiable view of the internal issue record list.
     */
    public ObservableList<IssueRecord> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if the given list contains only unique issue records and no duplicate item IDs.
     */
    private boolean issueRecordsAreUniqueAndNoDuplicateItems(List<IssueRecord> issueRecords) {
        for (int i = 0; i < issueRecords.size(); i++) {
            for (int j = i + 1; j < issueRecords.size(); j++) {
                IssueRecord first = issueRecords.get(i);
                IssueRecord second = issueRecords.get(j);
                if (first.equals(second) || first.hasSameItem(second)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns an iterator over the issue records in this list.
     */
    @Override
    public Iterator<IssueRecord> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if both lists contain the same issue records in the same order.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueIssueRecordList)) {
            return false;
        }
        UniqueIssueRecordList otherList = (UniqueIssueRecordList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns a string representation of this issue record list.
     */
    @Override
    public String toString() {
        return internalList.toString();
    }
}
