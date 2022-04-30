package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;
import Domain.Utils.SystemLogger;

public class PaymentAdapterImpl implements PaymentAdapter {
    private ExternPaymentSystemProxy externPaymentSystemProxy;

    public void setExternPaymentSystemProxy(ExternPaymentSystemProxy externPaymentSystemProxy) {
        this.externPaymentSystemProxy = externPaymentSystemProxy;
    }

    @Override
    public boolean payment(double total_price, String paymentInfo) {
        return externPaymentSystemProxy.payment(total_price,paymentInfo);
    }

    @Override
    public boolean connect_to_payment_system() {
        SystemLogger.getInstance().add_log("system connected to the payment system");
        return externPaymentSystemProxy.connect();
    }
}
