package Domain.UserModule;

import Domain.StoreModule.Purchase.UserPurchase;
import Domain.StoreModule.Purchase.UserPurchaseHistory;

public class AssignUser extends AssignState {
    private String email;
    private Security security;
    private String name;
    private String lastName;
    private UserPurchaseHistory userPurchaseHistory;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.security = new Security(pw);
        this.name = name;
        this.lastName = lastName;
        this.userPurchaseHistory = new UserPurchaseHistory();
    }

    @Override
    public boolean login(String pw) throws Exception {
        security.check_correct_password(pw);
        return true;
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
    public String get_user_name() throws Exception {
        return name;
    }

    @Override
    public String get_user_last_name() throws Exception {
        return lastName;
    }

    @Override
    public String get_user_email() throws Exception {
        return email;
    }

    @Override
    public UserPurchaseHistory view_user_purchase_history(){
        return this.userPurchaseHistory;
    }

    @Override
    public void unregister(String password) throws Exception {
        security.check_correct_password(password);
    }

    public void edit_name(String pw, String new_name) throws Exception {
        security.check_correct_password(pw);
        this.name = new_name;
    }

    public void edit_password(String old_password, String password) throws Exception {
        this.security.edit_password(old_password,password);
    }

    public void edit_last_name(String pw, String new_last_name) throws Exception {
        security.check_correct_password(pw);
        this.name = new_last_name;
    }

    public String get_sequrity_question() throws Exception {
        return security.get_question();
    }

    public void verify_answer(String answer) throws Exception {
        security.verify_answer(answer);
    }

    public void improve_security(String password, String question, String answer) throws Exception {
        security.check_correct_password(password);
        security.check_improvable();
        security = new PremiumSecurity(password, question, answer);
    }
}