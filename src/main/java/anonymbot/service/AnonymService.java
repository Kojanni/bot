package anonymbot.service;

import anonymbot.model.Anonymous;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.stream.Stream;

public interface AnonymService {

    public boolean setUserDisplayedName(User user, String name);

    public boolean removeAnonymous(User user);

    public boolean addAnonymous(Anonymous anonymous);

    public static boolean hasAnonymous(User user);

    public static String getDisplayedName(User user);

    public Stream<Anonymous> getAnonymousesStream();

}