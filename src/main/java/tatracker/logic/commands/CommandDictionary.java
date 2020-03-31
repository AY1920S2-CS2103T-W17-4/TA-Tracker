package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores a list of all the commands.
 */
public class CommandDictionary {
    private static final Map<String, CommandEntry> FULL_COMMAND_WORDS = Arrays
            .stream(CommandEntry.values())
            .collect(Collectors.toUnmodifiableMap(CommandEntry::getFullCommandWord, entry -> entry));

    private static final Map<String, CommandEntry> COMMAND_WORDS = Arrays
            .stream(CommandEntry.values())
            .collect(Collectors
                    .toUnmodifiableMap(CommandEntry::getCommandWord, entry -> entry, (first, second) -> first));

    private static final String HELP_MESSAGE = Arrays
            .stream(CommandEntry.values())
            .map(CommandDictionary::formatHelpMessage)
            .collect(Collectors.joining("\n\n"));

    /**
     * Returns true if the {@code fullCommandWord} is valid.
     */
    public static boolean hasFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.containsKey(fullCommandWord);
    }

    /**
     * Returns the matching CommandEntry based on the {@code fullCommandWord}.
     */
    public static CommandEntry getEntryWithFullCommandWord(String fullCommandWord) {
        requireNonNull(fullCommandWord);
        return FULL_COMMAND_WORDS.get(fullCommandWord);
    }

    /**
     * Returns true if the {@code commandWord} is valid.
     */
    public static boolean hasCommandWord(String commandWord) {
        requireNonNull(commandWord);
        return COMMAND_WORDS.containsKey(commandWord);
    }

    /**
     * Returns the matching CommandEntry based on the {@code commandWord}.
     */
    public static CommandEntry getEntryWithCommandWord(String commandWord) {
        requireNonNull(commandWord);
        return COMMAND_WORDS.get(commandWord);
    }

    /**
     * Returns the message information of all the commands.
     */
    public static String getHelpMessage() {
        return HELP_MESSAGE;
    }

    /**
     * Formats the help info of a command entry.
     */
    private static String formatHelpMessage(CommandEntry entry) {
        return String.format("%s: %s", entry.getFullCommandWord(), entry.getInfo());
    }
}
