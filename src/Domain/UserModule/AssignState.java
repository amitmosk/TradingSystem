package Domain.UserModule;

import Domain.Purchase.UserPurchase;
import Domain.Purchase.UserPurchaseHistory;

public abstract class AssignState{
    public boolean login(String pw) throws Exception {throw new Exception("Assign user cannot log in");}

    public void addPurchase(UserPurchase purchase) { }

    public void check_if_user_buy_from_this_store(int store_id) throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public void check_if_user_buy_this_product(int storeID, int productID) throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public UserPurchaseHistory view_user_purchase_history() throws Exception {throw new Exception("Guest Hasn't made any purchases yet.");}

    public String get_user_name() throws Exception {throw new Exception("Guest doesnt have name.");}

    public String get_user_last_name() throws Exception {throw new Exception("Guest doesnt have last name.");}

    public String get_user_email() throws Exception {throw new Exception("Guest doesnt have email.");}

    public void check_admin_permission() throws Exception { throw new Exception("only admin have permissions for this operation.");}

    public void unregister(String password) throws Exception { throw new Exception("guest cant unregister from the system");}

    public void edit_name(String pw, String new_name) throws Exception { throw new Exception("guest cant change is name");}

    public void edit_password(String old_password, String password) throws Exception { throw new Exception("guest cant change is password");}

    public void edit_last_name(String pw, String new_last_name) throws Exception { throw new Exception("guest cant change his last name");}

    public String get_sequrity_question() throws Exception { throw new Exception("guest does not have privacy question");}

    public void verify_answer(String answer) throws Exception { throw new Exception("guest does not have privacy question");}

    public void improve_security(String password, String question, String answer) throws Exception {throw new Exception("guest cannot improve security");}
}
