package TradingSystem.server.Domain.Facade.UnitTest;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SupplyAdapterImplTest {
private SupplyAdapterImpl supplyAdapter;
    @BeforeEach
    void setUp() {
        supplyAdapter = new SupplyAdapterImpl();
    }

    @Test
    void supply() {
        boolean answer = supplyAdapter.supply("address");
        assertTrue(answer);
    }

    @Test
    void connect_to_payment_system() {
        boolean answer = supplyAdapter.connect_to_supply_system();
        assertTrue(answer);
    }
}