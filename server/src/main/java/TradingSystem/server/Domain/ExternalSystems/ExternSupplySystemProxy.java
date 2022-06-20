package TradingSystem.server.Domain.ExternalSystems;

import TradingSystem.server.Domain.ExternSystems.SupplyInfo;
import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import TradingSystem.server.Domain.Utils.Utils;

import java.util.HashMap;
import static TradingSystem.server.Service.MarketSystem.external_system_url;

/**
 * Requirement 1.4
 */
public class ExternSupplySystemProxy {

    public int supply(SupplyInfo supplyInfo) {
        int supply_success = -2;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "supply");
        postContent.put("name", supplyInfo.getName());
        postContent.put("address", supplyInfo.getAddress());
        postContent.put("city", supplyInfo.getCity());
        postContent.put("country", supplyInfo.getCountry());
        postContent.put("zip", supplyInfo.getZip());

        // send the request
        try {
            String answer = Utils.send_http_post_request(external_system_url, postContent);
            supply_success = Utils.string_to_int(answer);
        }
        catch (Exception e){
            SystemLogger.getInstance().add_log("Supply External Service Fail: "+e.getMessage());
        }
        return supply_success;
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

    public int cancel_supply(int transaction_id) {
        int cancel_success = -2;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_supply");
        postContent.put("transaction_id", ""+transaction_id);
        // send the request
        try{
            String answer = Utils.send_http_post_request(external_system_url, postContent);
            cancel_success = Utils.string_to_int(answer);
        }
        catch (Exception e){
            SystemLogger.getInstance().add_log("Cancel Supply External Service Fail:" +e.getMessage());
        }
        return cancel_success;
    }

}
