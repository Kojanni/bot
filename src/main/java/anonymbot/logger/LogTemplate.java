package anonymbot.logger;

public enum LogTemplate {
    COMMAND_SUCCESS("COMMAND_SUCCESS"),
    COMMAND_EXCEPTION("COMMAND_EXCEPTION"),
    COMMAND_PROCESSING("COMMAND_PROCESSING"),
    MESSAGE_PROCESSING("MESSAGE_PROCESSING"),
    MESSAGE_RECEIVED("MESSAGE_RECEIVED"),
    MESSAGE_LOST("MESSAGE_LOST"),
    MESSAGE_SENT("MESSAGE_SENT"),
    MESSAGE_EXCEPTION("MESSAGE_EXCEPTION");

    private String template;

    LogTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
