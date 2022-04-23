package Service;

public class Market //implements IMarket{
{
    private UserController uc;
    private int loggedUser;                  //id or email
    private boolean isLogged;
//    @Override
    public void init_market() {
        this.uc = UserController.getInstance();
        isLogged = false;
    }

//    @Override
    public boolean payment(int price) {
        return false;
    }

//    @Override
    public boolean supply(int user_id, int purchase_id) {
        return false;
    }

//    @Override
    public int guest_login() {
        int logged = uc.guest_login();
        this.loggedUser = logged;
        return logged;
    }

//    @Override
    public double login(String Email, String password) {
        boolean logRes = uc.login(loggedUser, Email, password);
        if(logRes) isLogged = true;
        return 1;
    }

//    @Override
    public void logout() {
        //todo check if logged in user is guest
        this.loggedUser = -1;
    }

//    @Override
    public double register(String Email, String pw, String name, String lastName) {
        uc.register(Email,pw,name,lastName);
        return 1;
    }

//    @Override
    public String find_store_information(int store_id) {
        return null;
    }

//    @Override
    public String find_product_information(int product_id) {
        return null;
    }

//    @Override
    public void find_product_by_name() {

    }

//    @Override
    public void find_product_by_category() {

    }

//    @Override
    public void find_product_by_keyword() {

    }

//    @Override
    public double view_user_cart() {
        uc.view_user_cart(loggedUser);
        return 1;
    }
/*

    @Override
    public double delete_product_from_cart() {
        return 0;
    }

    @Override
    public double add_product_to_cart() {
        return 0;
    }

    @Override
    public double edit_product_from_cart() {
        return 0;
    }

    @Override
    public int buy_cart() {
        return 0;
    }

    @Override
    public int open_store() {
        return 0;
    }

    @Override
    public int add_review(int product_id) {
        return 0;
    }

    @Override
    public int rate_product(int product_id) {
        return 0;
    }

    @Override
    public int rate_store(int store_id) {
        return 0;
    }

    @Override
    public int send_request_to_store(int store_id, String request) {
        return 0;
    }

    @Override
    public double send_complain() {
        return 0;
    }

    @Override
    public double view_user_purchase_history() {
        return 0;
    }

    @Override
    public double view_account_details() {
        return 0;
    }

    @Override
    public double edit_account_details() {
        return 0;
    }

    @Override
    public double add_security_personal_question() {
        return 0;
    }

    @Override
    public void add_product_to_store() {

    }

    @Override
    public void delete_product_from_store() {

    }

    @Override
    public void edit_product(int store_id, int product_id) {

    }

    @Override
    public void add_discount_rule() {

    }

    @Override
    public void delete_discount_rule() {

    }

    @Override
    public void add_purchase_rule() {

    }

    @Override
    public void delete_purchase_rule() {

    }
*/
}
