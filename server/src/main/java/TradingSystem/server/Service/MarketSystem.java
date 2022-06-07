package TradingSystem.server.Service;

import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.SystemLogger;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.Utils.Exception.MarketException;


public class MarketSystem {
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;
    public final static String external_system_url = "http://cs-bgu-wsep.herokuapp.com/";


    public MarketSystem() {
        this.init_market();
    }


    /**
     * Requirement 1.1
     */

    public void init_market()  {
        SystemLogger.getInstance().add_log("start init market");
        this.payment_adapter = new PaymentAdapterImpl();
        this.supply_adapter = new SupplyAdapterImpl();
        boolean connect_to_external_systems = payment_adapter.handshake() && supply_adapter.handshake();
        if (!connect_to_external_systems)
            System.out.println("cant connect to the external systems");

        // load DB
        UserController.load();
        StoreController.load();
        try {
            this.add_admins();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public PaymentAdapter getPayment_adapter() {
        return payment_adapter;
    }

    public SupplyAdapter getSupply_adapter() {
        return supply_adapter;
    }

    public void add_admins() throws MarketException {
        UserController.getInstance().add_admin("admin@gmail.com", "12345678aA", "Barak", "Bahar");
        SystemLogger.getInstance().add_log("admin added");


    }
}
