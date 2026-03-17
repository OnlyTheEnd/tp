package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.alias.exceptions.DuplicateAliasException;

/**
 * Tests for {@link UniqueAliasMappingList}.
 */
public class UniqueAliasMappingListTest {

    private static final AliasMapping BASKETBALL_ALIAS =
            new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
    private static final AliasMapping BASKETBALL_ALIAS_COPY =
            new AliasMapping("Wilson-Evolution-Basketball-1", "B1");
    private static final AliasMapping SAME_ALIAS_DIFFERENT_TARGET =
            new AliasMapping("Hall-2", "b1");
    private static final AliasMapping VOLLEYBALL_ALIAS =
            new AliasMapping("Molten-Volleyball", "v1");

    private final UniqueAliasMappingList uniqueAliasMappingList = new UniqueAliasMappingList();

    @Test
    public void contains_nullAliasMapping_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAliasMappingList.contains(null));
    }

    @Test
    public void contains_aliasMappingNotInList_returnsFalse() {
        assertFalse(uniqueAliasMappingList.contains(BASKETBALL_ALIAS));
    }

    @Test
    public void contains_aliasMappingInList_returnsTrue() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertTrue(uniqueAliasMappingList.contains(BASKETBALL_ALIAS));
    }

    @Test
    public void contains_sameAliasName_returnsTrue() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertTrue(uniqueAliasMappingList.contains(SAME_ALIAS_DIFFERENT_TARGET));
    }

    @Test
    public void hasAliasName_aliasNotInList_returnsFalse() {
        assertFalse(uniqueAliasMappingList.hasAliasName("b1"));
    }

    @Test
    public void hasAliasName_aliasInList_returnsTrue() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertTrue(uniqueAliasMappingList.hasAliasName("B1"));
    }

    @Test
    public void getAliasMappingByName_aliasExists_returnsAliasMapping() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);

        Optional<AliasMapping> aliasMapping = uniqueAliasMappingList.getAliasMappingByName("B1");

        assertTrue(aliasMapping.isPresent());
        assertEquals(BASKETBALL_ALIAS, aliasMapping.get());
    }

    @Test
    public void getAliasMappingByName_aliasDoesNotExist_returnsEmpty() {
        assertFalse(uniqueAliasMappingList.getAliasMappingByName("b1").isPresent());
    }

    @Test
    public void add_nullAliasMapping_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAliasMappingList.add(null));
    }

    @Test
    public void add_duplicateAlias_throwsDuplicateAliasException() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertThrows(DuplicateAliasException.class, () -> uniqueAliasMappingList.add(BASKETBALL_ALIAS_COPY));
    }

    @Test
    public void add_differentAlias_success() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        uniqueAliasMappingList.add(VOLLEYBALL_ALIAS);

        UniqueAliasMappingList expectedUniqueAliasMappingList = new UniqueAliasMappingList();
        expectedUniqueAliasMappingList.add(BASKETBALL_ALIAS);
        expectedUniqueAliasMappingList.add(VOLLEYBALL_ALIAS);

        assertEquals(expectedUniqueAliasMappingList, uniqueAliasMappingList);
    }

    @Test
    public void setAliasMappings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAliasMappingList.setAliasMappings(null));
    }

    @Test
    public void setAliasMappings_list_replacesOwnListWithProvidedList() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        List<AliasMapping> aliasMappings = Collections.singletonList(VOLLEYBALL_ALIAS);

        uniqueAliasMappingList.setAliasMappings(aliasMappings);

        UniqueAliasMappingList expectedUniqueAliasMappingList = new UniqueAliasMappingList();
        expectedUniqueAliasMappingList.add(VOLLEYBALL_ALIAS);

        assertEquals(expectedUniqueAliasMappingList, uniqueAliasMappingList);
    }

    @Test
    public void setAliasMappings_listWithDuplicateAliases_throwsDuplicateAliasException() {
        List<AliasMapping> aliasMappings =
                Arrays.asList(BASKETBALL_ALIAS, SAME_ALIAS_DIFFERENT_TARGET);

        assertThrows(DuplicateAliasException.class, () -> uniqueAliasMappingList.setAliasMappings(aliasMappings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);

        assertThrows(UnsupportedOperationException
                .class, () -> uniqueAliasMappingList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertEquals(uniqueAliasMappingList.asUnmodifiableObservableList().toString(),
                uniqueAliasMappingList.toString());
    }
    @Test
    public void hasAliasName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAliasMappingList.hasAliasName(null));
    }

    @Test
    public void getAliasMappingByName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAliasMappingList.getAliasMappingByName(null));
    }

    @Test
    public void iterator() {
        uniqueAliasMappingList.add(BASKETBALL_ALIAS);
        assertTrue(uniqueAliasMappingList.iterator().hasNext());
    }
}
