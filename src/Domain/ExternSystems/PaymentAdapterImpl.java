package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;
// TODO: logger
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
        return externPaymentSystemProxy.connect();
    }
}
