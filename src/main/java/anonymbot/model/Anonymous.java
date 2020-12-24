package anonymbot.model;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Anonymous {

    private static final Logger LOG = LogManager.getLogger(Anonymous.class);
    private static final String USER_CHAT_CANNOT_BE_NULL = "User or chat cannot be null!";

    private final User user;
    private final Chat chat;
    private String displayedName;


    public Anonymous(User user, Chat chat) {
        this.user = user;
        this.chat = chat;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Anonymous && ((Anonymous) obj).getUser().equals(user);
    }

    public User getUser() {
        return user;
    }

    public Chat getChat() {
        return chat;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        displayedName = displayedName;
    }

}
