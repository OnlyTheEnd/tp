package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.model.issue.IssueRecord;

/**
 * Issues an item to a student with a specified due date/time.
 */
public class IssueCommand extends Command {

    public static final String COMMAND_WORD = "issue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Issues an item to a student.\n"
            + "Parameters: ITEM_ID STUDENT_ID DUE_DATE_TIME\n"
            + "Date/time format: yyyy-MM-dd HHmm\n"
            + "Example: " + COMMAND_WORD + " Wilson-Evolution-Basketball-1 A1037637K 2026-03-15 1700";

    public static final String MESSAGE_SUCCESS = "%1$s issued to %2$s due back on %3$s";
    public static final String MESSAGE_INVALID_ITEM = "Error:\n%1$s is not a valid registered item.";
    public static final String MESSAGE_INVALID_STUDENT = "Error:\n%1$s is not a valid student ID in the system.";
    public static final String MESSAGE_ALREADY_ISSUED =
            "Error:\n%1$s is currently issued to %2$s due back on %3$s";
    public static final String MESSAGE_ITEM_NOT_AVAILABLE =
            "Error:\n%1$s cannot be issued because its current status is %2$s.";

    private final IssueRecord issueRecordToAdd;

    /**
     * Creates an IssueCommand to issue the specified {@code IssueRecord}.
     *
     * @param issueRecord The issue record to be added.
     */
    public IssueCommand(IssueRecord issueRecord) {
        requireNonNull(issueRecord);
        this.issueRecordToAdd = issueRecord;
    }

    /**
     * Executes the issue command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result containing the success message.
     * @throws CommandException If the item is invalid, the student is invalid,
     *         or the item has already been issued.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String resolvedItemId = model.resolveAlias(issueRecordToAdd.getItemId());
        IssueRecord resolvedIssueRecord = new IssueRecord(
                resolvedItemId,
                issueRecordToAdd.getStudentId(),
                issueRecordToAdd.getDueDateTime());

        Optional<Equipment> matchingEquipment = model.getAddressBook().getEquipmentList().stream()
                .filter(equipment -> equipment.getName().fullName.equalsIgnoreCase(resolvedIssueRecord.getItemId()))
                .findFirst();

        if (matchingEquipment.isEmpty()) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_ITEM, resolvedIssueRecord.getItemId()));
        }

        Equipment equipment = matchingEquipment.get();
        if (equipment.getStatus() != EquipmentStatus.AVAILABLE) {
            throw new CommandException(String.format(
                    MESSAGE_ITEM_NOT_AVAILABLE,
                    resolvedIssueRecord.getItemId(),
                    equipment.getStatus()));
        }

        if (!model.hasStudentId(resolvedIssueRecord.getStudentId())) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_STUDENT, resolvedIssueRecord.getStudentId()));
        }

        Optional<IssueRecord> existingIssue = model.getIssueRecordByItemId(resolvedIssueRecord.getItemId());
        if (existingIssue.isPresent()) {
            IssueRecord issued = existingIssue.get();
            throw new CommandException(String.format(
                    MESSAGE_ALREADY_ISSUED,
                    issued.getItemId(),
                    issued.getStudentId(),
                    issued.getFormattedDueDateTime()));
        }

        model.addIssueRecord(resolvedIssueRecord);

        model.updatePersonDisplay(resolvedIssueRecord.getStudentId());

        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                resolvedIssueRecord.getItemId(),
                resolvedIssueRecord.getStudentId(),
                resolvedIssueRecord.getFormattedDueDateTime()));
    }

    /**
     * Returns true if both issue commands have the same issue record to add.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IssueCommand)) {
            return false;
        }

        IssueCommand otherIssueCommand = (IssueCommand) other;
        return issueRecordToAdd.equals(otherIssueCommand.issueRecordToAdd);
    }

    /**
     * Returns a string representation of this command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("issueRecordToAdd", issueRecordToAdd)
                .toString();
    }
}
