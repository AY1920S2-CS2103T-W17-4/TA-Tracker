package tatracker.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_WELCOME = "Welcome to TA-Tracker!\n\n";
    public static final String MESSAGE_HELP = "Enter help to view the list of commands";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid command format!\n\n";

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid";
    public static final String MESSAGE_INVALID_SESSION_DISPLAYED_INDEX = "The session index provided is invalid";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_SESSIONS_LISTED_OVERVIEW = "%1$d sessions listed!";
    public static final String MESSAGE_INVALID_STUDENTS = "No students created in this module!";

    public static String getInvalidCommandMessage(String commandUsage) {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, commandUsage);
    }
}
