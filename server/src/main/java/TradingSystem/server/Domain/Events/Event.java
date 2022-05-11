package TradingSystem.server.Domain.Events;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Event {
    // TODO : User -> Assign User
    private List<User> observers;
    private final String notification = "Store is closed by the admin permanently";

    public Event() {
        this.observers = new ArrayList<>();
    }

    public void addListener(User observer) {
        this.observers.add(observer);

    }

    public void removeListener(User observer) {
        this.observers.remove(observer);

    }

    public void notifyListeners(String notification) {
        for (User member : observers) {
            member.add_notification(notification);
        }
    }
}
