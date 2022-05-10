package TradingSystem.server.Domain.ExternSystems;

public interface PaymentAdapter {
    boolean payment(double total_price, String paymentInfo);
    boolean can_pay(double total_price, String paymentInfo);
    boolean connect_to_payment_system();
}
