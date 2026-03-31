package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.issue.IssueRecord;

/**
 * Returns an issued equipment item back to the inventory.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Returns an issued equipment item.\n"
            + "Parameters: ITEM_ID\n"
            + "Example: " + COMMAND_WORD + " Wilson-Evolution-Basketball-1";

    public static final String MESSAGE_SUCCESS = "%1$s returned successfully from %2$s";
    public static final String MESSAGE_NOT_ISSUED = "Error:\n%1$s is not currently issued.";

    private final String itemId;

    /**
     * Creates a ReturnCommand to return the specified item.
     *
     * @param itemId The item ID to return.
     */
    public ReturnCommand(String itemId) {
        requireNonNull(itemId);
        this.itemId = itemId;
    }

    /**
     * Executes the return command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result containing the success message.
     * @throws CommandException If the item is not currently issued.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String resolvedItemId = model.resolveAlias(itemId);
        Optional<IssueRecord> existingIssue = model.getIssueRecordByItemId(resolvedItemId);

        if (existingIssue.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NOT_ISSUED, resolvedItemId));
        }

        IssueRecord issueRecord = existingIssue.get();
        model.removeIssueRecord(issueRecord);

        model.updatePersonDisplay(issueRecord.getStudentId());

        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                issueRecord.getItemId(),
                issueRecord.getStudentId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReturnCommand)) {
            return false;
        }

        ReturnCommand otherReturnCommand = (ReturnCommand) other;
        return itemId.equals(otherReturnCommand.itemId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("itemId", itemId)
                .toString();
    }
}
