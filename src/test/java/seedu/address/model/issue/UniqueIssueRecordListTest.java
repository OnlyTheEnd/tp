package seedu.address.model.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.issue.exceptions.DuplicateIssueRecordException;
import seedu.address.model.issue.exceptions.ItemAlreadyIssuedException;
import seedu.address.model.person.StudentId;

public class UniqueIssueRecordListTest {

    private static final StudentId STUDENT_ONE = new StudentId("a1234567a");
    private static final StudentId STUDENT_TWO = new StudentId("a2345678b");

    private static final IssueRecord BASKETBALL_ISSUE = new IssueRecord("Wilson-Evolution-Basketball-1", STUDENT_ONE,
            LocalDateTime.now().plusDays(1));

    private static final IssueRecord BASKETBALL_ISSUE_COPY = new IssueRecord("Wilson-Evolution-Basketball-1",
            STUDENT_ONE, BASKETBALL_ISSUE.getDueDateTime());

    private static final IssueRecord BASKETBALL_SAME_ITEM_DIFFERENT_STUDENT = new IssueRecord(
            "Wilson-Evolution-Basketball-1", STUDENT_TWO, LocalDateTime.now().plusDays(2));

    private static final IssueRecord VOLLEYBALL_ISSUE = new IssueRecord("Molten-Volleyball", STUDENT_TWO,
            LocalDateTime.now().plusDays(1));

    private final UniqueIssueRecordList uniqueIssueRecordList = new UniqueIssueRecordList();

    @Test
    public void contains_nullIssueRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIssueRecordList.contains(null));
    }

    @Test
    public void contains_issueRecordNotInList_returnsFalse() {
        assertFalse(uniqueIssueRecordList.contains(BASKETBALL_ISSUE));
    }

    @Test
    public void contains_issueRecordInList_returnsTrue() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        assertTrue(uniqueIssueRecordList.contains(BASKETBALL_ISSUE));
    }

    @Test
    public void hasIssuedItem_itemNotInList_returnsFalse() {
        assertFalse(uniqueIssueRecordList.hasIssuedItem("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void hasIssuedItem_itemInList_returnsTrue() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        assertTrue(uniqueIssueRecordList.hasIssuedItem("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void getIssueRecordByItemId_itemExists_returnsIssueRecord() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        Optional<IssueRecord> issueRecord = uniqueIssueRecordList
                .getIssueRecordByItemId("Wilson-Evolution-Basketball-1");
        assertTrue(issueRecord.isPresent());
        assertEquals(BASKETBALL_ISSUE, issueRecord.get());
    }

    @Test
    public void getIssueRecordByItemId_itemDoesNotExist_returnsEmpty() {
        assertFalse(uniqueIssueRecordList.getIssueRecordByItemId("Wilson-Evolution-Basketball-1").isPresent());
    }

    @Test
    public void add_nullIssueRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIssueRecordList.add(null));
    }

    @Test
    public void add_duplicateIssueRecord_throwsDuplicateIssueRecordException() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        assertThrows(DuplicateIssueRecordException
                .class, () -> uniqueIssueRecordList.add(BASKETBALL_ISSUE_COPY));
    }

    @Test
    public void add_sameItemAlreadyIssued_throwsItemAlreadyIssuedException() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        assertThrows(ItemAlreadyIssuedException
                .class, () -> uniqueIssueRecordList.add(BASKETBALL_SAME_ITEM_DIFFERENT_STUDENT));
    }

    @Test
    public void add_differentItem_success() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        uniqueIssueRecordList.add(VOLLEYBALL_ISSUE);

        UniqueIssueRecordList expectedUniqueIssueRecordList = new UniqueIssueRecordList();
        expectedUniqueIssueRecordList.add(BASKETBALL_ISSUE);
        expectedUniqueIssueRecordList.add(VOLLEYBALL_ISSUE);

        assertEquals(expectedUniqueIssueRecordList, uniqueIssueRecordList);
    }

    @Test
    public void setIssueRecords_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueIssueRecordList.setIssueRecords(null));
    }

    @Test
    public void setIssueRecords_list_replacesOwnListWithProvidedList() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        List<IssueRecord> issueRecords = Collections.singletonList(VOLLEYBALL_ISSUE);
        uniqueIssueRecordList.setIssueRecords(issueRecords);

        UniqueIssueRecordList expectedUniqueIssueRecordList = new UniqueIssueRecordList();
        expectedUniqueIssueRecordList.add(VOLLEYBALL_ISSUE);

        assertEquals(expectedUniqueIssueRecordList, uniqueIssueRecordList);
    }

    @Test
    public void setIssueRecords_listWithDuplicateIssueRecords_throwsDuplicateIssueRecordException() {
        List<IssueRecord> issueRecords = Arrays.asList(BASKETBALL_ISSUE, BASKETBALL_ISSUE_COPY);
        assertThrows(DuplicateIssueRecordException
                .class, () -> uniqueIssueRecordList.setIssueRecords(issueRecords));
    }

    @Test
    public void setIssueRecords_listWithDuplicateItemIds_throwsDuplicateIssueRecordException() {
        List<IssueRecord> issueRecords = Arrays.asList(BASKETBALL_ISSUE, BASKETBALL_SAME_ITEM_DIFFERENT_STUDENT);
        assertThrows(DuplicateIssueRecordException
                .class, () -> uniqueIssueRecordList.setIssueRecords(issueRecords));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueIssueRecordList.add(BASKETBALL_ISSUE);
        assertThrows(UnsupportedOperationException
                .class, () -> uniqueIssueRecordList.asUnmodifiableObservableList().remove(0));
    }
}
