package Domain;

import Domain.UserModule.Cart;
import Domain.UserModule.UserController;
import Domain.UserModule.UserHistory;

import java.util.Map;

public class Market //implements IMarket{
{
    private UserController uc;
    private int loggedUser;                  //id or email
    private boolean isGuest;                 //represents the state

    public void init_market() {
        this.uc = UserController.getInstance();
        isGuest = true;
    }

    public boolean payment(int price) {
        return false;
    }

    public boolean supply(int user_id, int purchase_id) {
        return false;
    }

    public int guest_login() {
        int logged = uc.guest_login();
        this.loggedUser = logged;
        return logged;
    }

    public double login(String Email, String password) {
        boolean logRes = uc.login(loggedUser, Email, password);
        if(logRes) isGuest = false;
        return 1;
    }


    public void logout() {
        if(isGuest) return; //todo throw error
        this.isGuest = true;
        this.loggedUser = -1;
    }

    public double register(String Email, String pw, String name, String lastName) throws IllegalAccessException {
        if(!isGuest) return 1; //todo throw error
        uc.register(loggedUser,Email,pw,name,lastName);
        return 1;
    }

    public void add_product_to_cart(int storeID,int productID, int quantity) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             p = sc.checkAvailabilityAndGet(storeID,productID,quantity);
//             basket.addProduct(p,quantity);
            uc.addBasket(loggedUser, storeID, storeBasket);
        }
        catch (Exception e){

        }
    }

    public void edit_product_quantity_in_cart(int storeID,int productID, int quantity) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             p = sc.checkAvailabilityAndGet(storeID,productID,quantity);
//             basket.changeQuantity(p,quantity);
        }
        catch (Exception e){

        }
    }

    public void remove_product_from_cart(int storeID,int productID) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             basket.removeProduct(p);
            uc.removeBasketIfNeeded(loggedUser, storeID, storeBasket);
        }
        catch (Exception e){

        }
    }

    public Map<Integer,Basket> view_user_cart() {
        return uc.getBaskets(loggedUser);
    }

    public int buy_cart() {
        // get information about the payment & supply
        Cart cart = this.uc.getCart(this.loggedUser);
//        double total_price = this.store_controller.check_cart_available_products_and_calc_price(cart);
//        this.payment(total_price, paymentInfo);
//        this.supply(supplyInfo);
        // success
        // acquire lock of : edit/delete product, both close_store, discount & purchase policy, delete user from system.
        this.uc.buyCart(this.loggedUser);
//        this.store_controller.update_stores_inventory(cart, purchase_id);
        // failed
        return 0;
    }

    public double send_complain() {
        //todo implement request handler
        return 0;
    }

    public UserHistory view_user_purchase_history() throws Exception { //todo remove throws and catch exception
        return uc.view_user_purchase_history(loggedUser);
    }

    public double view_account_details() {
        return 0;
    }

    public String get_user_name() throws Exception { //todo handle exception in try catch
        return this.uc.get_user_name(loggedUser);
    }

    public String get_user_last_name() throws Exception { //todo handle exception in try catch
        return this.uc.get_user_last_name(loggedUser);
    }
/*
//    @Override
    public int open_store() {
//        sc.open_store();
        uc.open_store();
        return 1;
    }
*/
/*
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
