package anonymbot.logger;

public enum LogLevel {
    SUCCESS("SUCCESS"),
    STRANGE("STRANGE");

    private String value;

    LogLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
