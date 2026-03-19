package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new StudentId("A1234567A"),
                new Phone("87438807"), new Email("alexyeoh@u.nus.edu"),
                getTagSet()),
            new Person(new Name("Bernice Yu"), new StudentId("A2345678B"),
                new Phone("99272758"), new Email("berniceyu@u.nus.edu"),
                getTagSet()),
            new Person(new Name("Charlotte Oliveiro"), new StudentId("A3456789C"),
                new Phone("93210283"), new Email("charlotte@u.nus.edu"),
                getTagSet()),
            new Person(new Name("David Li"), new StudentId("A4567890D"),
                new Phone("91031282"), new Email("lidavid@u.nus.edu"),
                getTagSet()),
            new Person(new Name("Irfan Ibrahim"), new StudentId("A5678901E"),
                new Phone("92492021"), new Email("irfan@u.nus.edu"),
                getTagSet()),
            new Person(new Name("Roy Balakrishnan"), new StudentId("A6789012F"),
                new Phone("92624417"), new Email("royb@u.nus.edu"),
                getTagSet())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
