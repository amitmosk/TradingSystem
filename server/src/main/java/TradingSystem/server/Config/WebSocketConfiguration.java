package TradingSystem.server.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

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
