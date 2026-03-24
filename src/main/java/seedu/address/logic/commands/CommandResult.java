package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean showPersonList;

    private final boolean showRoomList;

    private final boolean showEquipmentList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showPersonList,
                         boolean showRoomList,
                         boolean showEquipmentList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showPersonList = showPersonList;
        this.showRoomList = showRoomList;
        this.showEquipmentList = showEquipmentList;
    }

    /**
     * Update the existing constructor to set defaults for all list toggles.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, false, false);
    }

    /**
     * Constructor for toggling specific views without exit/help flags.
     */
    public CommandResult(String feedbackToUser, boolean showPersonList,
                         boolean showRoomList, boolean showEquipmentList) {
        this(feedbackToUser, false, false, showPersonList, showRoomList, showEquipmentList);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false,
                false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowPersonList() {
        return showPersonList;
    }

    public boolean isShowRoomList() {
        return showRoomList;
    }

    public boolean isShowEquipmentList() {
        return showEquipmentList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showRoomList == otherCommandResult.showRoomList
                && showEquipmentList == otherCommandResult.showEquipmentList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showRoomList, showEquipmentList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showRoomList", showRoomList)
                .add("showEquipmentList", showEquipmentList)
                .toString();
    }

}
