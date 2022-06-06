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
    public boolean handshake() {
        SystemLogger.getInstance().add_log("system connected to the payment system");
        return externPaymentSystemProxy.handshake();
    }

    @Override
    public int payment(PaymentInfo paymentInfo, double total_price) {
        return externPaymentSystemProxy.payment(total_price, paymentInfo);
    }

    @Override
    public int cancel_pay(int transaction_id) {
        SystemLogger.getInstance().add_log("cancel payment of transaction : " + transaction_id);
        return externPaymentSystemProxy.cancel_payment(transaction_id);
    }

}
