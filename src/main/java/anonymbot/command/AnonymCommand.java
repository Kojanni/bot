package anonymbot.command;

import anonymbot.logger.LogLevel;
import anonymbot.logger.LogTemplate;
import lombok.NonNull;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AnonymCommand extends BotCommand {
    final Logger log = LogManager.getLogger(getClass());

    public AnonymCommand(@NonNull String command, @NonNull String description) {
        super(command, description);
    }

    /**
     * Uспользуется для обработки команд пользователей.
     * После того как мы обработаем команду пользователя,
     * мы можем послать ему ответ, используя метод execute класса AbsSender.
     *
     * @param sender
     * @param user
     * @param message
     */
    public void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
            log.log(Level.getLevel(LogLevel.SUCCESS.getValue()), LogTemplate.COMMAND_SUCCESS.getTemplate(), user.getId(), getCommandIdentifier());
        } catch (TelegramApiException e) {
            log.error(LogTemplate.COMMAND_EXCEPTION.getTemplate(), user.getId(), getCommandIdentifier(), e);
        }
    }

    /**
     * реализованный метод класса BotCommand, в котором обрабатывается команда, введенная пользователем
     * @param absSender - отправляет ответ пользователю
     * @param user - пользователь, который выполнил команду
     * @param chat - чат бота и пользователя
     * @param strings - аргументы, переданные с командой
     */
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {}

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

    }
}

