package TradingSystem.server.Service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

public class NotificationHandler extends TextWebSocketHandler {

    private static NotificationHandler notificationHandler = null;
    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();
    private final Map<String, WebSocketSession> webSocketSessionDict = new HashMap<>();
    private final Map<String, List<String>> users_notifications = new HashMap<>();

    public static NotificationHandler getInstance() {
        if (notificationHandler == null)
            notificationHandler = new NotificationHandler();
        return notificationHandler;
    }

    // TODO : ADD LOGGER, BUILD DICTIONARY
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("new connection opened");
        webSocketSessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        for (WebSocketSession webSocketSession : webSocketSessionList) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionList.remove(session);
    }


    // -----------------------------------------------------------------------------------------------------------


    /**
     * this method is for add notification & try to send it.
     * @param email of the assign user we want to notify.
     * @param notification to send the user.
     */
    public void add_notification(String email, String notification) {
        if (!this.users_notifications.containsKey(email)) {
            this.users_notifications.put(email, new ArrayList<>());
        }
        this.users_notifications.get(email).add(notification);
        this.send_waiting_notifications(email);
    }

    /**
     * this method is for sending await notifications when user login / after add notification.
     * @param email of the assign user who just logged in.
     * @return true if we send notifications, false if there is no open connection / no notifications to send.
     */
    public boolean send_waiting_notifications(String email) {
        // step 1 : check that there is a connection & there are notifications to send.
        if (this.webSocketSessionDict.containsKey(email))
            return false;
        if (!this.users_notifications.containsKey(email))
            return false;
        WebSocketSession session = this.webSocketSessionDict.get(email);
        List<String> notificationsList = this.users_notifications.get(email);
        if (notificationsList.size() == 0)
            return false;

        // step 2 : sending messages
        boolean flag_to_remove;
        for (String notification : notificationsList) {
            flag_to_remove = true;
            try{
                TextMessage message = new TextMessage(notification.getBytes());
                session.sendMessage(message);
            }
            catch (Exception e){
                // TODO: LOGGER
                flag_to_remove = false;
            }

            // check if can remove inside the loop -> semester 1 BUG
            if (flag_to_remove) {
                notificationsList.remove(notification);
            }
        }
        return true;

    }
}
