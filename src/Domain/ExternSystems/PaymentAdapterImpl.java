package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;
// TODO: logger
public class PaymentAdapterImpl implements PaymentAdapter {
    private ExternPaymentSystemProxy externPaymentSystemProxy;

    public void setExternPaymentSystemProxy(ExternPaymentSystemProxy externPaymentSystemProxy) {
        this.externPaymentSystemProxy = externPaymentSystemProxy;
    }

    @Override
    public boolean pay() {
        return externPaymentSystemProxy.pay();
    }

    @Override
    public boolean connect() {
        return externPaymentSystemProxy.connect();
    }
}
