package TradingSystem.server.Domain.ExternalSystems;

public interface SupplyAdapter {

    /**
     * This action type is used for dispatching a delivery to a costumer.
     * @param supplyInfo
     * @return transaction id - an integer in the range [10000, 100000] which indicates a
     * transaction number if the transaction succeeds or -1 if the transaction has failed.
     */
    int supply(SupplyInfo supplyInfo);


    /**
     * This action type is used for cancelling a supply transaction.
     * @param transaction_id
     * @return : 1 if the cancellation has been successful or -1 if the cancellation has failed.
     */
    int cancel_supply(int transaction_id);


    /**
     * This action type is used for check the availability of the external systems.
     * @return  “OK” message to signify that the handshake has been successful.
     */
    boolean handshake();
}
