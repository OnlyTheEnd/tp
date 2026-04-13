package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditStudentCommand.
 */
public class EditStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * EP: Valid class — all fields specified, no constraints violated.
     */
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid class — partial fields specified; unspecified fields retain original values.
     * BVA: Uses the last person in the list (upper boundary of valid index).
     */
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditStudentCommand editCommand = new EditStudentCommand(indexLastPerson, descriptor);
        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Invalid class — edit results in a duplicate Student ID.
     */
    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_SECOND_PERSON, descriptor);
        String expectedMessage = EditStudentCommand.MESSAGE_DUPLICATE_FIELDS + " Duplicate Student ID";
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    /**
     * EP: Invalid class — index does not correspond to any person in the list.
     * BVA: Index is exactly one beyond the last valid index (list.size() + 1).
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditStudentCommand editCommand = new EditStudentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * EP: Invalid class — student has an active reservation, edit should be blocked.
     */
    @Test
    public void execute_editNameWithActiveReservation_failure() {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Reservation res = new Reservation("Room-1", personInList.getStudentId(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1));
        model.addReservation(res);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName("New Name").build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditStudentCommand.MESSAGE_HAS_ACTIVE_LOANS);
    }

    /**
     * EP: Invalid class — student has an active loan, edit should be blocked regardless of field edited.
     */
    @Test
    public void execute_editPhoneWithActiveLoan_failure() {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.addIssueRecord(new IssueRecord("Item-1", personInList.getStudentId(),
                LocalDateTime.now().plusDays(1)));

        // Attempt to edit ONLY the phone with active loan
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone("92345678").build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditStudentCommand.MESSAGE_HAS_ACTIVE_LOANS);
    }

    /**
     * EP: Invalid class — phone number conflicts with another existing student's phone.
     */
    @Test
    public void execute_duplicatePhoneOtherStudent_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Try to edit A's phone to match B's phone
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withPhone(secondPerson.getPhone().value).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = EditStudentCommand.MESSAGE_DUPLICATE_FIELDS + " Duplicate Phone Number";
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    /**
     * EP: Invalid class — email conflicts with another existing student's email.
     */
    @Test
    public void execute_duplicateEmailOtherStudent_failure() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Try to edit A's email to match B's email
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withEmail(secondPerson.getEmail().value).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = EditStudentCommand.MESSAGE_DUPLICATE_FIELDS + " Duplicate Email Address";
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditStudentCommand commandWithSameValues = new EditStudentCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListStudentCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * EP: Valid class — verifies correct string format for a command with a known index and empty descriptor.
     */
    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditStudentCommand editCommand = new EditStudentCommand(index, editPersonDescriptor);
        String expected = EditStudentCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
