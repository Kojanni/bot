package anonymbot.command;

import anonymbot.logger.LogLevel;
import anonymbot.logger.LogTemplate;
import anonymbot.service.AnonymService;
import org.apache.logging.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class MyNameCommand extends AnonymCommand {
    private final AnonymService anonymService;
    private final static String command = "my_name";
    private final static String description = "show your current name that will be displayed with your messages\n";

    public MyNameCommand(AnonymService anonymService) {
        super(command, description);
        this.anonymService = anonymService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommand());

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!anonymService.hasAnonymous(user)) {

            sb.append("You are not in bot users' list! Send /start command!");
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without starting the bot.", user.getId(), getCommand());

        } else if (anonymService.getDisplayedName(user) == null) {

            sb.append("Currently you don't have a name.\nSet it using command:\n'/set_name &lt;displayed_name&gt;'");
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to execute '{}' without having a name.", user.getId(), getCommand());

        } else {

            log.info("User {} is executing '{}'. Name is '{}'.", user.getId(), getCommand(), anonymService.getDisplayedName(user));
            sb.append("Your current name: ").append(anonymService.getDisplayedName(user));
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    @Override
    public String getCommandIdentifier() {
        return command;
    }
}

