package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import TradingSystem.server.Domain.Utils.HttpUtility;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ExternPaymentSystemProxy {

    public int payment(double total_price, PaymentInfo paymentInfo)  {
        int answer = 1;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "pay");
        postContent.put("card_number", paymentInfo.getCard_number());
        postContent.put("month", paymentInfo.getMonth());
        postContent.put("year", paymentInfo.getYear());
        postContent.put("holder", paymentInfo.getHolder());
        postContent.put("ccv", paymentInfo.getCcv());
        postContent.put("id", paymentInfo.getId());
        postContent.put("total_price", ""+total_price);

        this.send_http_post_request(postContent);
        return answer;
    }

    public boolean handshake(){
        boolean answer = true;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "handshake");
        this.send_http_post_request(postContent);
        return answer;
    }

    public int cancel_payment(int transaction_id) {
        int answer = 1;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_pay");
        postContent.put("transaction_id", ""+transaction_id);
        this.send_http_post_request(postContent);
        return answer;
    }

    public void send_http_post_request(HashMap postContent){
        String url = "http://cs-bgu-wsep.herokuapp.com/";

        //server url

        // static class "HttpUtility" with static method "newRequest(url,method,callback)"
        HttpUtility.newRequest(url,HttpUtility.METHOD_POST,postContent, new HttpUtility.Callback() {
            @Override
            public void OnSuccess(String response) {
                // on success
                System.out.println("Server OnSuccess response="+response);
            }
            @Override
            public void OnError(int status_code, String message) {
                // on error
                System.out.println("Server OnError status_code="+status_code+" message="+message);
            }
        });

    }
}
