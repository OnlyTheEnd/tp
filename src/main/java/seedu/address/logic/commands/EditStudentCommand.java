package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends Command {

    public static final String COMMAND_WORD = "edit-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_STUDENT_ID + "STUDENT_ID] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John"
            + PREFIX_STUDENT_ID + "a7654321b "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "john@nus.edu.sg";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Edit failed. Duplicate fields detected: ";
    public static final String MESSAGE_HAS_ACTIVE_LOANS = "This student still has active loans or"
            + " reservations linked to their current ID.";
    public static final String MESSAGE_NEW_HAS_ACTIVE_LOANS = "The new Student ID is already"
            + " linked to existing loan records.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the student in the filtered person list to
     *                             edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditStudentCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        boolean hasActiveLoans = model.getAddressBook().getIssueRecordList().stream()
                .anyMatch(loan -> loan.getStudentId().equals(personToEdit.getStudentId()));
        boolean hasReservations = model.getAddressBook().getReservationList().stream()
                .anyMatch(res -> res.getStudentId().equals(personToEdit.getStudentId()));

        // Prevent editing if current student has active loans
        if (hasActiveLoans || hasReservations) {
            throw new CommandException(MESSAGE_HAS_ACTIVE_LOANS);
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        // Block if the matric, phone or email already exist
        if (!personToEdit.equals(editedPerson)) {
            Optional<Person> conflictPerson = model.getAddressBook().getPersonList().stream()
                    .filter(p -> !p.equals(personToEdit))
                    .filter(p -> p.getStudentId().equals(editedPerson.getStudentId())
                            || p.getPhone().equals(editedPerson.getPhone())
                            || p.getEmail().equals(editedPerson.getEmail()))
                    .findFirst();

            if (conflictPerson.isPresent()) {
                Person other = conflictPerson.get();
                String specificConflict = "";

                // Identify the duplicated field
                if (other.getStudentId().equals(editedPerson.getStudentId())) {
                    specificConflict = " Duplicate Student ID";
                } else if (other.getPhone().equals(editedPerson.getPhone())) {
                    specificConflict = " Duplicate Phone Number";
                } else {
                    specificConflict = " Duplicate Email Address";
                }

                throw new CommandException(MESSAGE_DUPLICATE_FIELDS + specificConflict);
            }

            // Ensure the 'target' ID is not already associated with active obligations.
            if (!personToEdit.getStudentId().equals(editedPerson.getStudentId())) {
                boolean newIdHasActive = model.getAddressBook().getIssueRecordList().stream()
                        .anyMatch(loan -> loan.getStudentId().equals(editedPerson.getStudentId()))
                        || model.getAddressBook().getReservationList().stream()
                                .anyMatch(res -> res.getStudentId().equals(editedPerson.getStudentId()));

                if (newIdHasActive) {
                    throw new CommandException(MESSAGE_NEW_HAS_ACTIVE_LOANS);
                }
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        StudentId updatedStudentId = editPersonDescriptor.getStudentId().orElse(personToEdit.getStudentId());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());

        return new Person(updatedName, updatedStudentId, updatedPhone, updatedEmail);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        EditStudentCommand otherEditCommand = (EditStudentCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the student with.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private StudentId studentId;
        private Phone phone;
        private Email email;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setStudentId(toCopy.studentId);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, studentId, phone, email);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setStudentId(StudentId studentId) {
            this.studentId = studentId;
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(studentId, otherEditPersonDescriptor.studentId)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("studentId", studentId)
                    .add("phone", phone)
                    .add("email", email)
                    .toString();
        }
    }
}
