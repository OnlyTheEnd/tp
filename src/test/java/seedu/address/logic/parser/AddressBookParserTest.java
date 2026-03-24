package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EQUIPMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEquipmentCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.CheckStudentLoansCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEquipmentCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEquipmentCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addStudent() throws Exception {
        Person person = new PersonBuilder().withTags().build();
        AddStudentCommand command = (AddStudentCommand) parser.parseCommand(PersonUtil.getAddStudentCommand(person));
        assertEquals(new AddStudentCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditStudentCommand command = (EditStudentCommand) parser.parseCommand(EditStudentCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditStudentCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertEquals(new HelpCommand(), parser.parseCommand(HelpCommand.COMMAND_WORD));
        assertEquals(new HelpCommand("tag"), parser.parseCommand(HelpCommand.COMMAND_WORD + " tag"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_listStudentCommand() throws Exception {
        assertTrue(parser.parseCommand(ListStudentCommand.COMMAND_WORD) instanceof ListStudentCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ListStudentCommand.COMMAND_WORD + " 3"));
        assertEquals(new ListStudentCommand(), parser.parseCommand(ListStudentCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_addEquipment() throws Exception {
        Equipment equipment = new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.AVAILABLE);
        AddEquipmentCommand command = (AddEquipmentCommand) parser.parseCommand(
                AddEquipmentCommand.COMMAND_WORD + " n/Wilson-Evolution c/Basketball s/Available");
        assertEquals(new AddEquipmentCommand(equipment), command);
    }

    @Test
    public void parseCommand_listEquipment() throws Exception {
        assertTrue(parser.parseCommand(ListEquipmentCommand.COMMAND_WORD) instanceof ListEquipmentCommand);
        assertTrue(parser.parseCommand(ListEquipmentCommand.COMMAND_WORD + " 3") instanceof ListEquipmentCommand);
    }

    @Test
    public void parseCommand_deleteEquipment() throws Exception {
        DeleteEquipmentCommand command = (DeleteEquipmentCommand) parser.parseCommand(
                DeleteEquipmentCommand.COMMAND_WORD + " " + INDEX_FIRST_EQUIPMENT.getOneBased());
        assertEquals(new DeleteEquipmentCommand(INDEX_FIRST_EQUIPMENT), command);
    }

    @Test
    public void parseCommand_checkStudentLoans() throws Exception {
        CheckStudentLoansCommand command = (CheckStudentLoansCommand) parser.parseCommand(
                CheckStudentLoansCommand.COMMAND_WORD + " " + ALICE.getStudentId());
        assertEquals(new CheckStudentLoansCommand(ALICE.getStudentId()), command);
    }
}
