package Domain.UserModule;

import Domain.Purchase.UserPurchase;
import Domain.Purchase.UserPurchaseHistory;

public class AssignUser extends AssignState {
    private String email;
    private String pw;
    private String name;
    private String lastName;
    private UserPurchaseHistory userPurchaseHistory;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.lastName = lastName;
        this.userPurchaseHistory = new UserPurchaseHistory();
    }

    @Override
    public boolean login(String pw){
        return pw.equals(this.pw);
    }

    @Override
    public void addPurchase(UserPurchase purchase){
        userPurchaseHistory.addPurchase(purchase);
    }

    public void check_if_user_buy_from_this_store(int store_id) throws Exception {
        this.userPurchaseHistory.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws Exception {
        this.userPurchaseHistory.check_if_user_buy_this_product(storeID,productID);
    }

    @Override
    public UserPurchaseHistory view_user_purchase_history(){
        return this.userPurchaseHistory;
    }

    @Override
    public void unregister(String password) throws Exception {
        if(!password.equals(this.pw)) throw new Exception("cannot sign out from system due to wrong password inserted");
    }

    public void edit_name(String pw, String new_name) throws Exception {
        if(!pw.equals(this.pw)) throw new Exception("password does not match to current password");
        this.name = new_name;
    }

    public void edit_password(String pw, String password) throws Exception {
        if(!pw.equals(this.pw)) throw new Exception("password does not match to current password");
        this.pw = password;
    }

    public void edit_last_name(String pw, String new_last_name) throws Exception {
        if(!pw.equals(this.pw)) throw new Exception("password does not match to current password");
        this.name = new_last_name;
    }
}
