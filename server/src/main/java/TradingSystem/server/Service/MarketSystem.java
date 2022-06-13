package TradingSystem.server.Service;

import TradingSystem.server.DAL.HibernateUtils;
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

    public MarketSystem() {
        this.init_market();
    }


    /**
     * Requirement 1.1
     */

    public void init_market()  {
        SystemLogger.getInstance().add_log("start init market");
        HibernateUtils.setPersistence_unit("TradingSystem");
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
        HibernateUtils.beginTransaction();
        UserController.get_instance().add_admin("admin@gmail.com", "12345678aA", "Barak", "Bahar");
        HibernateUtils.commit();
        SystemLogger.getInstance().add_log("admin added");
    }
}
