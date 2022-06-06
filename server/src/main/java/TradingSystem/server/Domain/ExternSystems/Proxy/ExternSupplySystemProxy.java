package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.SupplyInfo;

import java.util.HashMap;

public class ExternSupplySystemProxy {

    public int supply(SupplyInfo supplyInfo) {
        int answer = 1;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "supply");
        postContent.put("name", supplyInfo.getName());
        postContent.put("address", supplyInfo.getAddress());
        postContent.put("city", supplyInfo.getCity());
        postContent.put("country", supplyInfo.getCountry());
        postContent.put("zip", supplyInfo.getZip());


        this.send_http_post_request(postContent);
        return answer;
    }

    public boolean handshake(){
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "handshake");
        this.send_http_post_request(postContent);
        return true;
    }

    public int cancel_supply(int transaction_id) {
        int answer = 1;
        HashMap<String, String> postContent = new HashMap();
        postContent.put("action_type", "cancel_supply");
        postContent.put("transaction_id", ""+transaction_id);
        this.send_http_post_request(postContent);
        return answer;
    }


    private void send_http_post_request(HashMap postContent){
        String url = "https://cs-bgu-wsep.herokuapp.com/";

    }
}
