package anonymbot.service.impl;

import anonymbot.model.Anonymous;
import anonymbot.service.AnonymService;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class AnonymServiceImpl implements AnonymService {
    private final Set<Anonymous> anonymouses;

    public AnonymServiceImpl() {
        this.anonymouses = new HashSet<>();
    }

    @Override
    public boolean setUserDisplayedName(User user, String name) {
        if (!isDisplayedNameTaken(name)) {
            anonymouses.stream().filter(a -> a.getUser().equals(user)).forEach(a -> a.setDisplayedName(name));
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAnonymous(User user) {
        return anonymouses.removeIf(a -> a.getUser().equals(user));
    }

    @Override
    public boolean addAnonymous(Anonymous anonymous) {
        return anonymouses.add(anonymous);
    }

    @Override
    public boolean hasAnonymous(User user) {
        return anonymouses.stream().anyMatch(a -> a.getUser().equals(user));
    }

    @Override
    public String getDisplayedName(User user) {
        Anonymous anonymous = anonymouses.stream().filter(a -> a.getUser().equals(user)).findFirst().orElse(null);

        if (anonymous == null) {
            return null;
        }
        return anonymous.getDisplayedName();
    }

    @Override
    public Stream<Anonymous> getAnonymousesStream() {
        return anonymouses.stream();
    }

    private boolean isDisplayedNameTaken(String name) {
        return anonymouses.stream().anyMatch(a -> Objects.equals(a.getDisplayedName(), name));
    }
}
