package Tests;

import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;
import Domain.ExternSystems.Proxy.ExternSupplySystemProxy;
import Domain.ExternSystems.SupplyAdapterImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyAdapterImplTest {
private SupplyAdapterImpl supplyAdapter;
    @BeforeEach
    void setUp() {
        supplyAdapter = new SupplyAdapterImpl();
    }

    @Test
    void supply() {
        boolean answer = supplyAdapter.supply("address");
        Assert.assertTrue(answer);
    }

    @Test
    void connect_to_payment_system() {
        boolean answer = supplyAdapter.connect_to_supply_system();
        Assert.assertTrue(answer);
    }
}