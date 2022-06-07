package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternPaymentSystemProxyTest {

    private ExternPaymentSystemProxy externPaymentSystemProxy;

    @Test
    void cancel_payment() {
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(10000);
        boolean answer1 = this.externPaymentSystemProxy.handshake();
        int answer2 = this.externPaymentSystemProxy.cancel_payment(50000);
        int answer3 = this.externPaymentSystemProxy.payment(500, payment_info);


    }
}