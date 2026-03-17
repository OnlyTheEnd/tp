package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withStudentId("a1234567a")
            .withPhone("94351253")
            .withEmail("alice@u.nus.edu").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withStudentId("a1234567b")
            .withEmail("johnd@u.nus.edu").withPhone("98765432").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withStudentId("a1234567c").withPhone("95352563")
            .withEmail("heinz@u.nus.edu").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withStudentId("a1234567d").withPhone("87652533")
            .withEmail("cornelia@u.nus.edu").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withStudentId("a1234567e").withPhone("94822241")
            .withEmail("werner@u.nus.edu").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withStudentId("a1234567v").withPhone("94824271")
            .withEmail("lydia@u.nus.edu").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withStudentId("a1234567n").withPhone("94824421")
            .withEmail("anna@u.nus.edu").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withStudentId("a1234567p").withPhone("84824241")
            .withEmail("hoon@u.nus.edu").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withStudentId("a1234567y").withPhone("84821311")
            .withEmail("ida@u.nus.edu").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withStudentId(VALID_STUDENT_ID_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withStudentId(VALID_STUDENT_ID_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
