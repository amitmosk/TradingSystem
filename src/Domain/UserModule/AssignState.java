package Domain.UserModule;

public abstract class AssignState{
    public boolean login(String pw) throws Exception {throw new Exception("Assign user cannot log in");}

    public void addPurchase(UserPurchase purchase) { }

    public void check_if_user_buy_from_this_store(int store_id) throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public void check_if_user_buy_this_product(int storeID, int productID) throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public UserHistory view_user_purchase_history() throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public String get_user_name() throws Exception {throw new Exception("Guest doesnt have name.");}

    public String get_user_last_name() throws Exception {throw new Exception("Guest doesnt have last name.");}

    public String get_user_email() throws Exception {throw new Exception("Guest doesnt have last name.");}
}
