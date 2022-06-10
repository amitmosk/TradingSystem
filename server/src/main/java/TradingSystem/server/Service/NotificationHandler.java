package TradingSystem.server.Service;

import TradingSystem.server.Domain.Utils.Exception.MarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.*;


@Controller
public class NotificationHandler extends TextWebSocketHandler {

    private static NotificationHandler notificationHandler = null;
    protected ApplicationContext context;
    protected MessageController messageController;
    private Map<String, List<String>> users_notifications = new HashMap<>();


    private NotificationHandler(){
        context = MessageController.getAppContext();
        messageController = (MessageController) context.getBean("messageController");
    }


    public static NotificationHandler getInstance() {
        if (notificationHandler == null)
            notificationHandler = new NotificationHandler();
        return notificationHandler;
    }



    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(
            @Payload String message,
            Principal principal) throws Exception {
        System.out.println("got message from client");
        return "goodd";
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
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
     * this method is responsible for sending await notifications when user login / after add notification.
     * @param email of the assign user who just logged in / just got a new message.
     * @return true if we send notifications, false if there is no open connection / no notifications to send.
     */
    public boolean send_waiting_notifications(String email) {
        // step 1 : check that there is a connection
        if (!MessageController.has_open_connection(email))
            return false;
        // step 2 : check that there are notifications to send.
        if (!this.users_notifications.containsKey(email))
            return false;
        List<String> notificationsList = this.users_notifications.get(email);
        if (notificationsList.size() == 0)
            return false;

        // step 3 : sending messages
        List<String> to_remove = new ArrayList<>();
        boolean flag_to_remove = false;
        for (String notification : notificationsList) {
            flag_to_remove = true;
            try{
                this.messageController.sendNotification(email, notification);
            }
            catch (Exception e){
                flag_to_remove = false;
            }
            if (flag_to_remove) {
                to_remove.add(notification);
            }
        }

        for (String noti : to_remove)
            notificationsList.remove(noti);
        return true;

    }
}
