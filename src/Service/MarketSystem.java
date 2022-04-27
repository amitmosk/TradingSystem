package Service;

import Domain.StoreModule.StoreController;
import Domain.SupplyAdapter;
import Domain.UserModule.PaymentAdapter;
import Domain.UserModule.UserController;

public class MarketSystem {
    private int stats;


    //Requirement 1.1
    public void init_market(){
        System.out.println("start init market");
        this.payment_adapter = PaymentAdapter.connect_to_payment_system();
        this.supply_adapter = SupplyAdapter.connect_to_supply_system();
        UserController.load();
        StoreController.load();
    }

}
