package TradingSystem.server.Domain.ExternalSystems;

public interface PaymentAdapter {

    /**
     * This action type is used for check the availability of the external systems.
     * @return  “OK” message to signify that the handshake has been successful.
     */

    boolean handshake();


    /**
     * This action type is used for charging a payment for purchases.
     * @param paymentInfo of the payment method
     * @param price of the total cost to charge for the payment
     * @return : transaction id - an integer in the range [10000, 100000] which indicates a
     * transaction number if the transaction succeeds or -1 if the transaction has failed.
     */
    int payment(PaymentInfo paymentInfo, double price);

    /**
     * This action type is used for cancelling a payment transaction.
     * @param transaction_id
     * @return 1 if the cancellation has been successful or -1 if the cancellation has failed.
     */
    int cancel_pay(int transaction_id);


}
