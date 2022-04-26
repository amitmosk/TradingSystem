package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternPaymentSystemProxy;

public class PaymentAdapterImpl implements PaymentAdapter {
    private ExternPaymentSystemProxy externPaymentSystemProxy;

    @Override
    public boolean pay() {
        return externPaymentSystemProxy.pay();
    }
}
