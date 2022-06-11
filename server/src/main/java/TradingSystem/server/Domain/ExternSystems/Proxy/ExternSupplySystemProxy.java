package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.SupplyInfo;
import TradingSystem.server.Domain.Utils.Utils;

import java.util.HashMap;
import static TradingSystem.server.ConfigurationTests.MarketSystem.external_system_url;

public class ExternSupplySystemProxy {

    public int supply(SupplyInfo supplyInfo) {
        int supply_success;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "supply");
        postContent.put("name", supplyInfo.getName());
        postContent.put("address", supplyInfo.getAddress());
        postContent.put("city", supplyInfo.getCity());
        postContent.put("country", supplyInfo.getCountry());
        postContent.put("zip", supplyInfo.getZip());

        // send the request
        String answer = Utils.send_http_post_request(external_system_url, postContent);
        supply_success = Utils.string_to_int(answer);
        return supply_success;
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

    public int cancel_supply(int transaction_id) {
        int cancel_success;
        // build params for post request
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_supply");
        postContent.put("transaction_id", ""+transaction_id);
        // send the request
        String answer = Utils.send_http_post_request(external_system_url, postContent);
        cancel_success = Utils.string_to_int(answer);
        return cancel_success;
    }

}
