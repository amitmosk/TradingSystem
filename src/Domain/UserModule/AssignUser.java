package Domain.UserModule;

public class AssignUser extends AssignState {
    private String email;
    private String pw;
    private String name;
    private String lastName;
    private UserHistory userHistory;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.lastName = lastName;
        this.userHistory = new UserHistory();
    }

    @Override
    public boolean login(String pw){
        return pw.equals(this.pw);
    }

    @Override
    public void addPurchase(UserPurchase purchase){
        userHistory.addPurchase(purchase);
    }

    public void check_if_user_buy_from_this_store(int store_id) throws Exception {
        this.userHistory.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws Exception {
        this.userHistory.check_if_user_buy_this_product(storeID,productID);
    }

    @Override
    public UserHistory view_user_purchase_history() throws Exception {
        return this.userHistory;
    }
}
