package anonymbot.command;

import anonymbot.logger.LogLevel;
import anonymbot.logger.LogTemplate;
import anonymbot.model.Anonymous;
import anonymbot.service.AnonymService;
import org.apache.logging.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends AnonymCommand{

    private final AnonymService anonymService;
    private final static String command = "start";
     private final static String description ="start using bot\n";

    public StartCommand(AnonymService anonymService) {
        super(command, description);
        this.anonymService = anonymService;
    }

    /**
     * реализованный метод класса BotCommand, в котором обрабатывается команда, введенная пользователем
     * @param absSender - отправляет ответ пользователю
     * @param user - пользователь, который выполнил команду
     * @param chat - чат бота и пользователя
     * @param strings - аргументы, переданные с командой
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.info(LogTemplate.COMMAND_PROCESSING.getTemplate(), user.getId(), getCommand());

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (anonymService.addAnonymous(new Anonymous(user, chat))) {
            log.info("User {} is trying to execute '{}' the first time. Added to users' list.", user.getId(), getCommand());
            sb.append("Hi, ").append(user.getUserName()).append("! You've been added to bot users' list!\n")
                    .append("Please execute command:\n'/set_name <displayed_name>'\nwhere &lt;displayed_name&gt; is the name you want to use to hide your real name.");
        } else {
            log.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} has already executed '{}'. Is he trying to do it one more time?", user.getId(), getCommand());
            sb.append("You've already started bot! You can send messages if you set your name (/set_name).");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    @Override
    public String getCommandIdentifier() {
        return command;
    }
}

