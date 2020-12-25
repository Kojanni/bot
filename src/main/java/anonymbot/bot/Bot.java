package anonymbot.bot;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    private static final Logger LOG = LogManager.getLogger(Bot.class);

    private static final String BOT_NAME = "@Kojanni_bot";
    private static final String BOT_TOKEN = "1463505336:AAG1A_3rXS2XjrKdj-YGFz0lYDYdp-sTYQI";

    private long chatId;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public Bot(DefaultBotOptions options) {
        super(options);

        LOG.info("Initializing Bot...");
    }

    /**
     * получение токена бота
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * получение имени бота
     */
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Данная функция выполняется после получения сообщения
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId(); //обновить инфу о пользователе

        chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage().setChatId(chatId);

        String text = update.getMessage().getText(); // принимает текст входящего сообщения
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // установка нашей клавиатуры

        try {
            sendMessage.setText(getMessage(text));
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getMessage(String text) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        //корректируем параметры клавы
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if(text.equalsIgnoreCase("Hello") || text.equalsIgnoreCase("Привет") || text.equalsIgnoreCase("Меню")) {
            keyboard.clear();
            keyboardRow1.clear();
            keyboardRow1.add("Популярное");
            keyboardRow1.add("News\uD83D\uDCF0");
            keyboardRow2.add("Ничего");
            keyboard.add(keyboardRow1);
            keyboard.add(keyboardRow2);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Че хотел?!";
        }
        if (text.equalsIgnoreCase("Ничего")) {
            keyboard.clear();
            keyboardRow1.clear();
            keyboardRow1.add("Популярное");
            keyboardRow1.add("News\uD83D\uDCF0");
            keyboard.add(keyboardRow1);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Все чего-то хотят. Так что не выделывайся";
        }
        if (text.equalsIgnoreCase("Популярное")) {
            keyboard.clear();
            keyboardRow1.clear();
            keyboardRow1.add("Стих\uD83D\uDCDD");
            keyboardRow1.add("Книги\uD83D\uDCDA");
            keyboardRow2.add("Меню");
            keyboard.add(keyboardRow1);
            keyboard.add(keyboardRow2);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбирай...";
        }
        if (text.equalsIgnoreCase("Стих\uD83D\uDCDD") || text.equalsIgnoreCase("Книги\uD83D\uDCDA") || text.equalsIgnoreCase("News\uD83D\uDCF0")) {
            keyboard.clear();
            keyboardRow1.clear();
            keyboardRow1.add("Сегодня");
            keyboardRow1.add("За неделю");
            keyboardRow1.add("За месяц");
            keyboardRow2.add("Меню");
            keyboard.add(keyboardRow1);
            keyboard.add(keyboardRow2);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "и снова выбирай...";
        }

        return text;
    }

//    // обработка сообщения не начинающегося с '/'
//    @Override
//    public void processNonCommandUpdate(Update update) {
//
//        LOG.info("Processing non-command update...");
//
//        if (!update.hasMessage()) {
//            LOG.error("Update doesn't have a body!");
//            throw new IllegalStateException("Update doesn't have a body!");
//        }
//
//        Message msg = update.getMessage();
//        User user = msg.getFrom();
//
//        LOG.info(LogTemplate.MESSAGE_PROCESSING.getTemplate(), user.getId());
//
//        if (!canSendMessage(user, msg)) {
//            return;
//        }
//
//        String clearMessage = msg.getText();
//        String messageForUsers = String.format("%s:\n%s", anonymService.getDisplayedName(user), msg.getText());
//
//        SendMessage answer = new SendMessage();
//
//        // отправка ответа отправителю о том, что его сообщение получено
//        answer.setText(clearMessage);
//        answer.setChatId(String.valueOf(msg.getChatId()));
//        replyToUser(answer, user, clearMessage);
//
//        // отправка сообщения всем остальным пользователям бота
//        answer.setText(messageForUsers);
//        Stream<Anonymous> anonymouses = anonymService.getAnonymousesStream();
//        anonymouses.filter(a -> !a.getUser().equals(user))
//                .forEach(a -> {
//                    answer.setChatId(String.valueOf(a.getChat().getId()));
//                    sendMessageToUser(answer, a.getUser(), user);
//                });
//    }
//
//    // несколько проверок, чтобы можно было отправлять сообщения другим пользователям
//    private boolean canSendMessage(User user, Message msg) {
//
//        SendMessage answer = new SendMessage();
//        answer.setChatId(String.valueOf(msg.getChatId()));
//
//        if (!msg.hasText() || msg.getText().trim().length() == 0) {
//            LOG.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to send empty message!", user.getId());
//            answer.setText("You shouldn't send empty messages!");
//            replyToUser(answer, user, msg.getText());
//            return false;
//        }
//
//        if (!anonymService.hasAnonymous(user)) {
//            LOG.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to send message without starting the bot!", user.getId());
//            answer.setText("Firstly you should start bot! Use /start command!");
//            replyToUser(answer, user, msg.getText());
//            return false;
//        }
//
//        if (anonymService.getDisplayedName(user) == null) {
//            LOG.log(Level.getLevel(LogLevel.STRANGE.getValue()), "User {} is trying to send message without setting a name!", user.getId());
//            answer.setText("You must set a name before sending messages.\nUse '/set_name <displayed_name>' command.");
//            replyToUser(answer, user, msg.getText());
//            return false;
//        }
//
//        return true;
//    }
//
//    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
//        try {
//            execute(message);
//            LOG.log(Level.getLevel(LogLevel.SUCCESS.getValue()), LogTemplate.MESSAGE_RECEIVED.getTemplate(), receiver.getId(), sender.getId());
//        } catch (TelegramApiException e) {
//            LOG.error(LogTemplate.MESSAGE_LOST.getTemplate(), receiver.getId(), sender.getId(), e);
//        }
//    }
//
//    private void replyToUser(SendMessage message, User user, String messageText) {
//        try {
//            execute(message);
//            LOG.log(Level.getLevel(LogLevel.SUCCESS.getValue()), LogTemplate.MESSAGE_SENT.getTemplate(), user.getId(), messageText);
//        } catch (TelegramApiException e) {
//            LOG.error(LogTemplate.MESSAGE_EXCEPTION.getTemplate(), user.getId(), e);
//        }
//    }

}

