package framework.messages;

public final class Messages {

    public static final String ACTION_PERFORMED_SUCCESS = "Action performed successfully.";
    public static final String ACTION_FULLY_FILLED = "Action fully filled and completed.";

    public static final String ACTION_PERFORMED_WITH_ERROR = "Action performed with an error.";
    public static final String ACTION_NOT_PERFORMED = "Action not performed due to an error.";

    public static final String ACTION_IN_PROGRESS = "Action is currently in progress.";
    public static final String ACTION_PENDING = "Action is pending execution.";

    public static final String STATUS_SUCCESS = "Status: SUCCESS.";
    public static final String STATUS_FAILURE = "Status: FAILURE.";
    public static final String STATUS_INCOMPLETE = "Status: INCOMPLETE.";
    public static final String STATUS_ERROR = "Status: ERROR encountered during execution.";

    private Messages() {
        throw new UnsupportedOperationException("Messages is a utility class and cannot be instantiated.");
    }
}
