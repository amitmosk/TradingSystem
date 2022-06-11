package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import TradingSystem.server.Domain.Utils.Utils;

import java.util.HashMap;

import static TradingSystem.server.ConfigurationTests.MarketSystem.external_system_url;

public class ExternPaymentSystemProxy {

    public int payment(double total_price, PaymentInfo paymentInfo)  {
        int payment_success;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "pay");
        postContent.put("card_number", paymentInfo.getCard_number());
        postContent.put("month", paymentInfo.getMonth());
        postContent.put("year", paymentInfo.getYear());
        postContent.put("holder", paymentInfo.getHolder());
        postContent.put("ccv", paymentInfo.getCcv());
        postContent.put("id", paymentInfo.getId());
        postContent.put("total_price", ""+total_price);

        // send the request
        String answer = Utils.send_http_post_request(external_system_url, postContent);
        payment_success = Utils.string_to_int(answer);
        return payment_success;
    }

    public boolean handshake(){
        // build params for post request
        boolean hand_success;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "handshake");
        // send the request
        String answer = Utils.send_http_post_request(external_system_url, postContent);
        hand_success = Utils.string_to_boolean(answer);
        return hand_success;
    }


    public int cancel_payment(int transaction_id)  {
        int cancel_success;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_pay");
        postContent.put("transaction_id", ""+transaction_id);
        // send the request
        String answer = Utils.send_http_post_request(external_system_url, postContent);
        cancel_success = Utils.string_to_int(answer);
        return cancel_success;
    }




}
