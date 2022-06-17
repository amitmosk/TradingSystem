package Acceptance.System.ExternSystemsTests;

public class PaymentAdapterTests implements PaymentAdapter {

    public PaymentAdapterTests() {
    }

    @Override
    public boolean handshake() {
        return true;
    }

    @Override
    public int payment(PaymentInfo paymentInfo, double price) {
        return 55000;
    }

    @Override
    public int cancel_pay(int transaction_id) {
        return 1;
    }
}
