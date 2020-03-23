package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tatracker.commons.core.index.Index;
import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser implements Parser<DeleteStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * and returns a DeleteStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteStudentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE), pe);
        }
    }

}