package TradingSystem.server.Domain.ExternSystems.Proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternPaymentSystemProxyTest {

    private ExternPaymentSystemProxy externPaymentSystemProxy;

    @Test
    void cancel_payment() {
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(10000);
    }
}