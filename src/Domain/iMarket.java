package Domain;

public interface iMarket {

    /**
     * Requirement 2.1.3
     */
    String register(String Email, String pw, String name, String lastName);


    void remove_user();
    void unregister();

    /**
     * Requirement 2.3.2
     */
    String open_store(String store_name);


    /**
     * Requirement 2.2.5
     */

    String buy_cart(String paymentInfo, String supplyInfo);
}
