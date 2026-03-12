package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.alias.AliasMapping;
/**
 * Tests for {@link AliasCommand}.
 */
public class AliasCommandTest {

    private static final AliasMapping VALID_ALIAS_MAPPING =
            new AliasMapping("Wilson-Evolution-Basketball-1", "b1");

    @Test
    public void constructor_nullAliasMapping_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AliasCommand(null));
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        CommandResult commandResult = aliasCommand.execute(modelStub);

        assertEquals(VALID_ALIAS_MAPPING, modelStub.aliasAdded);
        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS,
                        VALID_ALIAS_MAPPING.getTargetId(),
                        VALID_ALIAS_MAPPING.getAliasName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidTarget_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasAliasableTarget(String targetId) {
                return false;
            }

            @Override
            public boolean hasAliasName(String aliasName) {
                return false;
            }
        };

        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        assertThrows(CommandException.class,
                String.format(AliasCommand.MESSAGE_INVALID_TARGET,
                        VALID_ALIAS_MAPPING.getTargetId()), () -> aliasCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateAlias_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasAliasableTarget(String targetId) {
                return true;
            }

            @Override
            public boolean hasAliasName(String aliasName) {
                return true;
            }
        };

        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        assertThrows(CommandException.class,
                String.format(AliasCommand.MESSAGE_DUPLICATE_ALIAS,
                        VALID_ALIAS_MAPPING.getAliasName()), () -> aliasCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AliasCommand aliasFirstCommand = new AliasCommand(VALID_ALIAS_MAPPING);
        AliasCommand aliasSecondCommand = new AliasCommand(new AliasMapping("Hall-2", "h2"));
        AliasCommand aliasFirstCommandCopy = new AliasCommand(VALID_ALIAS_MAPPING);

        assertTrue(aliasFirstCommand.equals(aliasFirstCommand));
        assertTrue(aliasFirstCommand.equals(aliasFirstCommandCopy));

        assertFalse(aliasFirstCommand.equals(1));
        assertFalse(aliasFirstCommand.equals(null));
        assertFalse(aliasFirstCommand.equals(aliasSecondCommand));
    }

    /**
     * A model stub that always accepts the alias being added.
     */
    private static class ModelStubAcceptingAliasAdded extends ModelStub {
        private AliasMapping aliasAdded;

        @Override
        public boolean hasAliasableTarget(String targetId) {
            requireNonNull(targetId);
            return true;
        }

        @Override
        public boolean hasAliasName(String aliasName) {
            requireNonNull(aliasName);
            return false;
        }

        @Override
        public void addAliasMapping(AliasMapping aliasMapping) {
            requireNonNull(aliasMapping);
            aliasAdded = aliasMapping;
        }
    }
}
