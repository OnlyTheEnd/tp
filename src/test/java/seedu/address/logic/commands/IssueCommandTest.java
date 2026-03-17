package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;


public class IssueCommandTest {

    private static final String VALID_ITEM_ID = "Wilson-Evolution-Basketball-1";
    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_DUE_DATE_TIME = LocalDateTime.of(2099, 3, 15, 17, 0);
    private static final IssueRecord VALID_ISSUE_RECORD =
            new IssueRecord(VALID_ITEM_ID, VALID_STUDENT_ID, VALID_DUE_DATE_TIME);

    @Test
    public void execute_issueAccepted_addSuccessful() throws Exception {
        ModelStubAcceptingIssueRecordAdded modelStub = new ModelStubAcceptingIssueRecordAdded();
        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        CommandResult commandResult = issueCommand.execute(modelStub);

        assertEquals(VALID_ISSUE_RECORD, modelStub.issueRecordAdded);
        assertEquals(String.format(IssueCommand.MESSAGE_SUCCESS,
                        VALID_ISSUE_RECORD.getItemId(),
                        VALID_ISSUE_RECORD.getStudentId(),
                        VALID_ISSUE_RECORD.getFormattedDueDateTime()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidItem_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return false;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_INVALID_ITEM,
                        VALID_ISSUE_RECORD.getItemId()), () -> issueCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidStudent_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return false;
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_INVALID_STUDENT,
                        VALID_ISSUE_RECORD.getStudentId()), () -> issueCommand.execute(modelStub));
    }

    @Test
    public void execute_itemAlreadyIssued_throwsCommandException() {
        IssueRecord existingIssueRecord = new IssueRecord("Wilson-Evolution-Basketball-1",
                new StudentId("a2345678b"),
                LocalDateTime.of(2099, 3, 12, 12, 0));

        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }

            @Override
            public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
                return Optional.of(existingIssueRecord);
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_ALREADY_ISSUED,
                        existingIssueRecord.getItemId(),
                        existingIssueRecord.getStudentId(),
                        existingIssueRecord
                                .getFormattedDueDateTime()), () -> issueCommand.execute(modelStub));
    }


    /**
     * A model stub that always accepts an issue record.
     */
    private static class ModelStubAcceptingIssueRecordAdded extends ModelStub {
        private IssueRecord issueRecordAdded;

        @Override
        public boolean hasIssuableItem(String itemId) {
            return true;
        }

        @Override
        public boolean hasStudentId(StudentId studentId) {
            return true;
        }

        @Override
        public void addIssueRecord(IssueRecord issueRecord) {
            issueRecordAdded = issueRecord;
        }
    }
}
