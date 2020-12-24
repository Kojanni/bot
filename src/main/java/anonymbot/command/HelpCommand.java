package anonymbot.command;

import anonymbot.logger.LogTemplate;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends AnonymCommand{
    private final ICommandRegistry commandRegistry;
    private final static String command = "help";
    private final static String description ="list all known commands\n";


    public HelpCommand(ICommandRegistry commandRegistry) {
        super(command, description);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommand());

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:</b>");

        commandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, user);
    }

    @Override
    public String getCommandIdentifier() {
        return command;
    }
}
