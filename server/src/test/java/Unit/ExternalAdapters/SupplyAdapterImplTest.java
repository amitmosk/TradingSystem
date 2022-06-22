package Unit.ExternalAdapters;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SupplyAdapterImplTest {
private SupplyAdapterImpl supplyAdapter;
    @BeforeEach
    void setUp() {
        supplyAdapter = new SupplyAdapterImpl();
    }

//    @Test
//    void supply() {
//        boolean answer = supplyAdapter.supply("address");
//        assertTrue(answer);
//    }

    @Test
    void connect_to_payment_system() {
        boolean answer = supplyAdapter.handshake();
        assertTrue(answer);
    }
}