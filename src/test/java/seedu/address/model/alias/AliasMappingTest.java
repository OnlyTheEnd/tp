package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AliasMapping}.
 */
public class AliasMappingTest {

    @Test
    public void constructor_invalidTargetId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AliasMapping("!invalid", "b1"));
    }

    @Test
    public void constructor_invalidAliasName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException
                .class, () -> new AliasMapping("Wilson-Evolution-Basketball-1", "b-1"));
    }


    @Test
    public void isValidAliasName() {
        assertTrue(AliasMapping.isValidAliasName("b1"));
        assertTrue(AliasMapping.isValidAliasName("court_1"));
        assertFalse(AliasMapping.isValidAliasName("b-1"));
        assertFalse(AliasMapping.isValidAliasName(" "));
    }

    @Test
    public void normalizeTargetId() {
        assertEquals("WILSON-EVOLUTION-BASKETBALL-1",
                AliasMapping.normalizeTargetId("  Wilson-Evolution-Basketball-1  "));
    }

    @Test
    public void normalizeAliasName() {
        assertEquals("b1", AliasMapping.normalizeAliasName("  B1  "));
    }

    @Test
    public void isSameAlias() {
        AliasMapping first = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
        AliasMapping sameAliasDifferentTarget = new AliasMapping("Hall-2", "B1");
        AliasMapping differentAlias = new AliasMapping("Wilson-Evolution-Basketball-1", "court1");

        assertTrue(first.isSameAlias(first));
        assertTrue(first.isSameAlias(sameAliasDifferentTarget));
        assertFalse(first.isSameAlias(differentAlias));
    }

    @Test
    public void equals() {
        AliasMapping first = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
        AliasMapping copy = new AliasMapping("Wilson-Evolution-Basketball-1", "B1");
        AliasMapping different = new AliasMapping("Hall-2", "h2");

        assertTrue(first.equals(copy));
        assertTrue(first.equals(first));
        assertFalse(first.equals(null));
        assertFalse(first.equals(different));
    }
    @Test
    public void isValidTargetId_null_returnsFalse() {
        assertFalse(AliasMapping.isValidTargetId(null));
    }

    @Test
    public void isValidAliasName_null_returnsFalse() {
        assertFalse(AliasMapping.isValidAliasName(null));
    }

    @Test
    public void isSameAlias_nullAliasMapping_throwsNullPointerException() {
        AliasMapping aliasMapping = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
        assertThrows(NullPointerException.class, () -> aliasMapping.isSameAlias(null));
    }

    @Test
    public void toStringMethod() {
        AliasMapping aliasMapping = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
        assertTrue(aliasMapping.toString().contains("WILSON-EVOLUTION-BASKETBALL-1"));
        assertTrue(aliasMapping.toString().contains("b1"));
    }
}
