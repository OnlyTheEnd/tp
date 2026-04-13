package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final StudentId studentId;
    private final Phone phone;
    private final Email email;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, StudentId studentId, Phone phone, Email email) {
        requireAllNonNull(name, studentId, phone, email);
        this.name = name;
        this.studentId = studentId;
        this.phone = phone;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both persons have the same student ID, phone, email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && (otherPerson.getStudentId().equals(getStudentId())
                || otherPerson.getPhone().equals(getPhone())
                || otherPerson.getEmail().value.equalsIgnoreCase(getEmail().value));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && studentId.equals(otherPerson.studentId)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentId, phone, email);
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
