package TradingSystem.server.Domain.ExternalSystems;

public interface SupplyAdapter {

    /**
     * This action type is used for check the availability of the external systems.
     * @return  “OK” message to signify that the handshake has been successful.
     */
    boolean handshake();

    /**
     * This action type is used for dispatching a delivery to a costumer.
     * @param supplyInfo
     * @return transaction id -
     * happy case: transaction number (an integer in the range [10000, 100000]) which indicates transaction success.
     * sad case: -1 if the transaction has failed
     * bad case: -2 for error
     */
    int supply(SupplyInfo supplyInfo);


    /**
     * This action type is used for cancelling a supply transaction.
     * @param transaction_id
     * @return integer who indicates 1 if the cancellation has been successful:
     * happy case: 1 - the cancellation succeed.
     * sad case: -1 if the cancellation has failed.
     * bad case: -2 for error.
     */
    int cancel_supply(int transaction_id);



}
