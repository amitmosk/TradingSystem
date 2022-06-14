package TradingSystem.server.Config;

import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        SystemLogger.getInstance().add_log("WS : Configure Message Broker");
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        SystemLogger.getInstance().add_log("WS : Register Stomp End Points");
        registry.addEndpoint( "/chat").setAllowedOriginPatterns("*").withSockJS();


    }
}
