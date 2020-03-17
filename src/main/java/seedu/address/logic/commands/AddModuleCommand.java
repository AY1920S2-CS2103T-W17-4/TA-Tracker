package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandWords.ADD_MODEL;
import static seedu.address.logic.commands.CommandWords.MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a module to the TA-Tracker.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = MODULE;
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ADD_MODEL + ": Adds a module to the TA-Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "MODULE NAME "
            + PREFIX_MODULE + "MODULE CODE "
            + "Example: " + COMMAND_WORD + " " + ADD_MODEL + " "
            + PREFIX_NAME + "Introduction to AI "
            + PREFIX_MODULE + "CS3243 ";

    public static final String MESSAGE_SUCCESS = "New Module added: %s";
    public static final String MESSAGE_DUPLICATE_SESSION = "This module already exists in the TATracker";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
