package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddStudentCommandTest {

    /**
     * EP: Invalid class — null input to constructor should throw NullPointerException.
     */
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    /**
     * EP: Valid class — valid person accepted by model results in successful add and correct feedback.
     */
    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddStudentCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS,
                validPerson.getName(), validPerson.getStudentId(),
                validPerson.getPhone(), validPerson.getEmail()),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    /**
     * EP: Invalid class — adding an exact duplicate student (same identity) throws CommandException.
     */
    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, () -> addStudentCommand.execute(modelStub));
    }

    /**
     * EP: Invalid class — adding a different student with a duplicate matric number throws CommandException.
     * Verifies that matric number uniqueness is enforced independently of other fields.
     */
    @Test
    public void execute_duplicateStudentId_throwsCommandException() {
        Person alice = new PersonBuilder().withName("Alice").withStudentId("A1234567Z").build();
        // Bob has a different name but the same matric number
        Person bobWithSameId = new PersonBuilder().withName("Bob").withStudentId("A1234567Z").build();

        AddStudentCommand addStudentCommand = new AddStudentCommand(bobWithSameId);
        ModelStub modelStub = new ModelStubWithPerson(alice);

        assertThrows(CommandException.class,
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, () -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private static class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accepts the person being added.
     */
    private static class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * EP: Valid class — verifies correct string format for a command with a known person.
     */
    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder().withName("Alice").build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(alice);

        String expected = AddStudentCommand.class.getCanonicalName() + "{toAdd=" + alice + "}";

        assertEquals(expected, addStudentCommand.toString());
    }
}
