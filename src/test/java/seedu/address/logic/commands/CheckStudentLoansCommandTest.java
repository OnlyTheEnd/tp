package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CheckStudentLoansCommand.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;

public class CheckStudentLoansCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * EP: Valid class — student exists in the system and has an active loan record.
     * Logic: Verifies that the command correctly retrieves the student's name and
     * displays the correct status for their borrowed items.
     */
    @Test
    public void execute_loanStatusComparison_success() throws Exception {
        // Use ALICE's ID from TypicalPersons so the command can find the student name
        StudentId aliceId = ALICE.getStudentId();
        LocalDateTime futureDue = LocalDateTime.now().plusDays(1);

        // Add the loan to the model linked to ALICE
        IssueRecord activeLoan = new IssueRecord("MPSH-1", aliceId, futureDue);
        model.addIssueRecord(activeLoan);

        CheckStudentLoansCommand command = new CheckStudentLoansCommand(aliceId);
        CommandResult result = command.execute(model);
        String feedback = result.getFeedbackToUser();

        // Assertions
        assertTrue(feedback.contains(ALICE.getName().toString()));
        assertTrue(feedback.contains("[BORROWED] MPSH-1"));
    }

    /**
     * EP: Invalid class — student ID does not exist in the model's address book.
     * Logic: Ensures the command fails gracefully with a "Student Not Found" message
     * when querying a non-existent ID.
     */
    @Test
    public void execute_studentNotFound_throwsCommandException() {
        CheckStudentLoansCommand command = new CheckStudentLoansCommand(new StudentId("A9999999Z"));
        assertCommandFailure(command, model, MESSAGE_STUDENT_NOT_FOUND);
    }
}
