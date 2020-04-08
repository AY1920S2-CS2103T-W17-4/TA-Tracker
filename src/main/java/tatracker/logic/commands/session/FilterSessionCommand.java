package tatracker.logic.commands.session;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.DATE;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.SESSION_TYPE;

import java.time.LocalDate;
import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.session.SessionPredicate;
import tatracker.model.session.SessionType;

/**
 * Filters sessions based on user's inputs.
 */
public class FilterSessionCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.SESSION,
            CommandWords.FILTER_MODEL,
            "Filters all the sessions inside TA-Tracker",
            List.of(),
            List.of(MODULE, DATE, SESSION_TYPE),
            MODULE, DATE, SESSION_TYPE
    );

    public static final String MESSAGE_FILTERED_SESSIONS_SUCCESS = "Filtered Session List: %s";
    public static final String MESSAGE_NO_SESSIONS_IN_MODULE = "There are no sessions"
            + " for the module with the given module code.";

    public static final String MESSAGE_INVALID_DATE = "There are no sessions with the given date";
    public static final String MESSAGE_INVALID_SESSIONTYPE = "There are no sessions with the given session type";

    private final SessionPredicate predicate;

    public FilterSessionCommand(SessionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String returnMsg = "";

        String sessionType = predicate.getSessionType().map(SessionType::toString).orElse("No Filters");
        String date = predicate.getDate().map(LocalDate::toString).orElse("No Filters");
        String moduleCode = predicate.getModuleCode().orElse("No Filters");

        if (!model.hasModule(moduleCode)) {
            returnMsg += "\n" + MESSAGE_NO_SESSIONS_IN_MODULE;
            model.updateFilteredSessionList(predicate);
            returnMsg += "\n" + String.format(MESSAGE_FILTERED_SESSIONS_SUCCESS, sessionType + " " + date);
        } else {

            model.setCurrSessionDateFilter(date);
            model.setCurrSessionModuleFilter(moduleCode);
            model.setCurrSessionTypeFilter(sessionType);

            //String result = buildParams(date, moduleCode, sessionType);
            //model.setCurrSessionFilter(result);
            model.updateFilteredSessionList(predicate);
            returnMsg += "\n" + String.format(MESSAGE_FILTERED_SESSIONS_SUCCESS,
                    date + " " + moduleCode + " " + sessionType);
        }

        return new CommandResult(returnMsg, Action.FILTER_SESSION);
    }

    /**
     *Creates a string consisting of all the params inputted by users.
     */
    public String buildParams(String date, String module, String sessionType) {
        StringBuilder builder = new StringBuilder();
        builder.append("Date: ").append(date);
        builder.append("Module Code: ").append(module);
        builder.append("Session Type: ").append(sessionType);
        String result = builder.toString();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterSessionCommand // instanceof handles nulls
                && predicate.equals(((FilterSessionCommand) other).predicate)); // state check
    }
}
