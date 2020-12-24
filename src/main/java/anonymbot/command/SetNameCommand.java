package anonymbot.command;

import anonymbot.logger.LogLevel;
import anonymbot.logger.LogTemplate;
import anonymbot.service.AnonymService;
import lombok.NonNull;
import org.apache.logging.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SetNameCommand extends AnonymCommand {
    private final AnonymService anonymService;
    private final static String command = "set_name";
    private final static String description = "set or change name that will be displayed with your messages\n";

    public SetNameCommand(AnonymService anonymService) {
        super(command, description);
        this.anonymService = anonymService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommand());

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!anonymService.hasAnonymous(user)) {
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without starting the bot!", user.getId(), getCommand());
            message.setText("Firstly you should start the bot! Execute '/start' command!");
            execute(absSender, message, user);
            return;
        }

        String displayedName = getName(strings);

        if (displayedName == null) {
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to set empty name.", user.getId());
            message.setText("You should use non-empty name!");
            execute(absSender, message, user);
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (anonymService.setUserDisplayedName(user, displayedName)) {

            if (anonymService.getDisplayedName(user) == null) {
                log.info("User {} set a name '{}'", user.getId(), displayedName);
                sb.append("Your displayed name: '").append(displayedName)
                        .append("'. Now you can send messages to bot!");
            } else {
                log.info("User {} has changed name to '{}'", user.getId(), displayedName);
                sb.append("Your new displayed name: '").append(displayedName).append("'.");
            }
        } else {
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to set taken name '{}'", user.getId(), displayedName);
            sb.append("Name ").append(displayedName).append(" is already in use! Choose another name!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    private String getName(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }

    @Override
    public String getCommandIdentifier() {
        return command;
    }
}


