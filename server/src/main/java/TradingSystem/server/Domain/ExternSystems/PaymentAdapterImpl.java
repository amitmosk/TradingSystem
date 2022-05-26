package TradingSystem.server.Domain.ExternSystems;

import TradingSystem.server.Domain.Utils.SystemLogger;
import TradingSystem.server.Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;

public class PaymentAdapterImpl implements PaymentAdapter {
    private ExternPaymentSystemProxy externPaymentSystemProxy;

    public PaymentAdapterImpl() {
        externPaymentSystemProxy = new ExternPaymentSystemProxy();
    }

    public void setExternPaymentSystemProxy(ExternPaymentSystemProxy externPaymentSystemProxy) {
        this.externPaymentSystemProxy = externPaymentSystemProxy;
    }

    @Override
    public boolean payment(double total_price, String paymentInfo) {

        return externPaymentSystemProxy.payment(total_price, paymentInfo);
    }

    @Override
    public boolean can_pay(double total_price, String paymentInfo) {
        return externPaymentSystemProxy.can_pay(total_price, paymentInfo);
    }

    @Override
    public boolean connect_to_payment_system() {
        SystemLogger.getInstance().add_log("system connected to the payment system");
        return externPaymentSystemProxy.connect();
    }
}
