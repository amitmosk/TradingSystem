package TradingSystem.server.Domain.ExternalSystems;

import TradingSystem.server.Domain.Utils.Logger.SystemLogger;

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
        SystemLogger.getInstance().add_log("System Connected To The Payment System");
        return externPaymentSystemProxy.handshake();
    }

    @Override
    public int payment(PaymentInfo paymentInfo, double total_price) {
        SystemLogger.getInstance().add_log("Payment Of: " + total_price);
        return externPaymentSystemProxy.payment(total_price, paymentInfo);
    }

    @Override
    public int cancel_pay(int transaction_id) {
        SystemLogger.getInstance().add_log("Cancel Payment Of Transaction : " + transaction_id);
        return externPaymentSystemProxy.cancel_payment(transaction_id);
    }

}
