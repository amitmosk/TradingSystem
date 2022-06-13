package TradingSystem.server.Service;

import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import TradingSystem.server.Domain.Utils.Threads.ConnectThread;
import TradingSystem.server.Domain.Utils.Threads.SendNotificationThread;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MessageController implements ApplicationContextAware {

    @Autowired
    static ApplicationContext myContext;
    @Autowired
    private SimpMessagingTemplate smt;

    public static HashMap<String, String> emails_to_sockSessionMap = new HashMap<>();

    public static boolean has_open_connection(String email) {
        return emails_to_sockSessionMap.containsKey(email);
    }

    @EventListener
    public void sessionDisconnectHandler(SessionDisconnectEvent sessionDisconnectEvent){
        String session_id = sessionDisconnectEvent.getSessionId();
        String email_to_remove = "";
        for (Map.Entry<String, String> email_sessionID : emails_to_sockSessionMap.entrySet())
        {
            if (email_sessionID.getValue().equals(session_id))
                email_to_remove = email_sessionID.getKey();
        }
        if (!email_to_remove.equals(""))
            emails_to_sockSessionMap.remove(email_to_remove);
        SystemLogger.getInstance().add_log("WS Disconnect Event, Session ID: "+session_id + "Email: "+email_to_remove);
    }

    @EventListener
    public void sessionConnectedHandler(SessionConnectedEvent sessionConnectedEvent){
        MessageHeaders m = sessionConnectedEvent.getMessage().getHeaders();
        Object session_id = m.get("simpSessionId");
        GenericMessage connectMessage = (GenericMessage) m.get("simpConnectMessage");
        MessageHeaders headers = connectMessage.getHeaders();
        Object nativeHeaders = headers.get("nativeHeaders");
        String s = nativeHeaders.toString();
        String email = s.split("]")[0].substring(8);
        emails_to_sockSessionMap.put(email, session_id.toString());
        SystemLogger.getInstance().add_log("WS Connect Event, Session ID: "+session_id + "Email: "+email);
        // TODO: solve first notification send problem
//        NotificationHandler.getInstance().send_waiting_notifications(e1);


    }


    public static ApplicationContext getAppContext() {
        return myContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        myContext = context;
    }

    public void sendNotification(String email, String notification){
        // destination according the user email
        String dest = "/topic/"+email;
        // message with the prefix for client knowledge
        String message = "as1:"+notification;
        SendNotificationThread sendNotificationThread = new SendNotificationThread(smt, dest, message);
        Thread t1 = new Thread(sendNotificationThread);
        t1.start();
    }
}