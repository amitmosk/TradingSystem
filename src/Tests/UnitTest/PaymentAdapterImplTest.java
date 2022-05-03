package Tests.UnitTest;

import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentAdapterImplTest {
private PaymentAdapterImpl paymentAdapter;
    @BeforeEach
    void setUp() {
        paymentAdapter = new PaymentAdapterImpl();
        ExternPaymentSystemProxy proxy = new ExternPaymentSystemProxy();
        paymentAdapter.setExternPaymentSystemProxy(proxy);
    }

    @Test
    void payment() {
        boolean answer = paymentAdapter.payment(3434.3,"credit card details");
        Assert.assertTrue(answer);
    }

    @Test
    void connect_to_payment_system() {
        boolean answer = paymentAdapter.connect_to_payment_system();
        Assert.assertTrue(answer);
    }
}