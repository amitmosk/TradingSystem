package TradingSystem.server.Config;

import TradingSystem.server.Service.NotificationHandler;
import TradingSystem.server.Service.NotificationHandler123;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        System.out.println("new connection broker");
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        System.out.println("new connection endpoint");
        registry.addEndpoint( "/chat").setAllowedOriginPatterns("*").withSockJS();


    }
//
//    @Bean
//    public WebSocketHandler getChatWebSocketHandler()
//    {
//        return new NotificationHandler();
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//        registry
//                .addEndpoint("/greeting")
//                .setHandshakeHandler(new DefaultHandshakeHandler() {
//
//                    public boolean beforeHandshake(
//                            ServerHttpRequest request,
//                            ServerHttpResponse response,
//                            WebSocketHandler wsHandler,
//                            Map attributes) throws Exception {
//
//                        if (request instanceof ServletServerHttpRequest) {
//                            ServletServerHttpRequest servletRequest
//                                    = (ServletServerHttpRequest) request;
//                            HttpSession session = servletRequest
//                                    .getServletRequest().getSession();
//                            attributes.put("sessionId", session.getId());
//                        }
//                        return true;
//                    }}).withSockJS();
//    }

}
