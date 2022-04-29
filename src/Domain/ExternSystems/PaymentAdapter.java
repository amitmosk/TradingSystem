package Domain.ExternSystems;

public interface PaymentAdapter {
    boolean payment(double total_price, String paymentInfo);
    boolean connect();
}
