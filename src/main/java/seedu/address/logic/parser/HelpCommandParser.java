package seedu.address.logic.parser;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     *
     * @param args User input arguments.
     * @return A HelpCommand for general help or topic-specific help.
     * @throws ParseException Never thrown currently, but kept to match the parser interface.
     */
    @Override
    public HelpCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new HelpCommand();
        }

        return new HelpCommand(trimmedArgs);
    }
}
