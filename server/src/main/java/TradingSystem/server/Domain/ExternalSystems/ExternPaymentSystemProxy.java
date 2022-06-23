package TradingSystem.server.Domain.ExternalSystems;

import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import TradingSystem.server.Domain.Utils.Utils;

import java.util.HashMap;

import static TradingSystem.server.Service.MarketSystem.external_system_url;

/**
 * Requirement 1.3
 */

public class ExternPaymentSystemProxy {

    public int payment(double total_price, PaymentInfo paymentInfo)  {
        int payment_success = -2;
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
        try{
            String answer = Utils.send_http_post_request(external_system_url, postContent);
            payment_success = Utils.string_to_int(answer);
        }
        catch (Exception e){
            SystemLogger.getInstance().add_log("Payment External Service Fail: "+e.getMessage());
        }
        return payment_success;
    }

    public boolean handshake(){
        // build params for post request
        boolean hand_success = false;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "handshake");
        // send the request
        try{
            String answer = Utils.send_http_post_request(external_system_url, postContent);
            hand_success = Utils.string_to_boolean(answer);
        }
        catch (Exception e){
            SystemLogger.getInstance().add_log("Handshake Supply External Service Fail:" +e.getMessage());
        }
        return hand_success;
    }


    public int cancel_payment(int transaction_id)  {
        int cancel_success = -2;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_pay");
        postContent.put("transaction_id", ""+transaction_id);
        // send the request
        try{
            String answer = Utils.send_http_post_request(external_system_url, postContent);
            cancel_success = Utils.string_to_int(answer);
        }
        catch (Exception e){
            SystemLogger.getInstance().add_log("Cancel Payment External Service Fail:" +e.getMessage());
        }
        return cancel_success;
    }




}
