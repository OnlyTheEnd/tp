package seedu.address.storage;

import static seedu.address.logic.commands.AddRoomCommand.MESSAGE_DUPLICATE_ROOM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.Room;

/**
 * An immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_CONFLICTING_RESERVATION =
            "Reservations list contains conflicting reservation(s): %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS =
            "Alias mappings list contains duplicate alias(es).";
    public static final String MESSAGE_DUPLICATE_ISSUE_RECORD =
            "Issue records list contains duplicate or conflicting issue record(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedRoom> rooms = new ArrayList<>();
    private final List<JsonAdaptedReservation> reservations = new ArrayList<>();
    private final List<JsonAdaptedIssueRecord> issueRecords = new ArrayList<>();
    private final List<JsonAdaptedAliasMapping> aliasMappings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given data.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("rooms") List<JsonAdaptedRoom> rooms,
                                       @JsonProperty("reservations") List<JsonAdaptedReservation> reservations,
                                       @JsonProperty("issueRecords") List<JsonAdaptedIssueRecord> issueRecords,
                                       @JsonProperty("aliasMappings") List<JsonAdaptedAliasMapping> aliasMappings) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (rooms != null) { // Add this block
            this.rooms.addAll(rooms);
        }
        if (reservations != null) {
            this.reservations.addAll(reservations);
        }
        if (issueRecords != null) {
            this.issueRecords.addAll(issueRecords);
        }
        if (aliasMappings != null) {
            this.aliasMappings.addAll(aliasMappings);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));

        rooms.addAll(source.getRoomList().stream()
                .map(JsonAdaptedRoom::new)
                .collect(Collectors.toList()));

        reservations.addAll(source.getReservationList().stream()
                .map(JsonAdaptedReservation::new)
                .collect(Collectors.toList()));

        issueRecords.addAll(source.getIssueRecordList().stream()
                .map(JsonAdaptedIssueRecord::new)
                .collect(Collectors.toList()));

        aliasMappings.addAll(source.getAliasMappingList().stream()
                .map(JsonAdaptedAliasMapping::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedRoom jsonAdaptedRoom : rooms) {
            Room room = jsonAdaptedRoom.toModelType();
            if (addressBook.hasRoom(room)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ROOM);
            }
            addressBook.addRoom(room);
        }

        for (JsonAdaptedReservation jsonAdaptedReservation : reservations) {
            Reservation reservation = jsonAdaptedReservation.toModelType();
            if (addressBook.hasConflictingReservation(reservation)) {
                throw new IllegalValueException(String.format(
                        MESSAGE_CONFLICTING_RESERVATION, reservation));
            }
            addressBook.addReservation(reservation);
        }

        for (JsonAdaptedIssueRecord jsonAdaptedIssueRecord : issueRecords) {
            IssueRecord issueRecord = jsonAdaptedIssueRecord.toModelType();
            if (addressBook.hasIssuedItem(issueRecord.getItemId())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ISSUE_RECORD);
            }
            addressBook.addIssueRecord(issueRecord);
        }

        for (JsonAdaptedAliasMapping jsonAdaptedAliasMapping : aliasMappings) {
            AliasMapping aliasMapping = jsonAdaptedAliasMapping.toModelType();
            if (addressBook.hasAliasName(aliasMapping.getAliasName())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ALIAS);
            }
            addressBook.addAliasMapping(aliasMapping);
        }

        return addressBook;
    }
}
