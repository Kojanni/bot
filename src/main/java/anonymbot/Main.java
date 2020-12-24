package anonymbot;

import anonymbot.bot.AnonymBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    private static final String PROXY_HOST = "xx.xx.xxx.xxx";
    private static final int PROXY_PORT = 9999;

    public static void main(String[] args)  {


        try {
            LOG.info("Initializing API context...");
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        LOG.info("Configuring bot options...");
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

            LOG.info("Registering Anonymizer...");
            botsApi.registerBot(new AnonymBot(botOptions));

            LOG.info("Anonymizer bot is ready for work!");

        } catch (TelegramApiException e) {
            LOG.error("Error while initializing bot!", e);
        }
    }
}