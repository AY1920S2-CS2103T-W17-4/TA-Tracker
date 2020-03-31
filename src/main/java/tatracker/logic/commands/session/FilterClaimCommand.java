package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.DoneSessionPredicate;

/**
 * Filter Module based on user's inputs.
 * Used under TSS view.
 */
public class FilterClaimCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.CLAIM,
            CommandWords.FILTER_MODEL,
            "Filters claim by module.",
            List.of(MODULE),
            List.of(),
            MODULE
    );

    public static final String MESSAGE_SUCCESS = "Filtered claim List";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no sessions with the given module code.";

    private final DoneSessionPredicate predicate;

    public FilterClaimCommand(DoneSessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDoneSessionList(predicate, predicate.getModuleCode());
        return new CommandResult(
                String.format(Messages.MESSAGE_SESSIONS_LISTED_OVERVIEW,
                                model.getFilteredDoneSessionList().size()),
                Action.FILTER_CLAIMS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterClaimCommand // instanceof handles nulls
                && predicate.equals(((FilterClaimCommand) other).predicate)); // state check
    }
}
