package Service;

import Domain.ExternSystems.PaymentAdapter;
import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.SupplyAdapter;
import Domain.ExternSystems.SupplyAdapterImpl;
import Domain.StoreModule.StoreController;
import Domain.UserModule.Admin;
import Domain.UserModule.User;
import Domain.UserModule.UserController;

public class MarketSystem {
    private Object stats;
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;

    public MarketSystem() {
        this.stats = new Object();
    }

    //Requirement 1.1
    public void init_market(){
        System.out.println("start init market");
        this.payment_adapter = new PaymentAdapterImpl();
        this.supply_adapter = new SupplyAdapterImpl();
        try
        {
            payment_adapter.connect_to_payment_system();
            supply_adapter.connect_to_supply_system();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        // load DB
        UserController.load();
        StoreController.load();
        // TODO: when init system we should create new admin.
//        UserController.getInstance().add_admin("barak_bahar@haifa.com", "12345678", "Barak", "Bahar");
    }

    public PaymentAdapter getPayment_adapter() {
        return payment_adapter;
    }

    public SupplyAdapter getSupply_adapter() {
        return supply_adapter;
    }
}
