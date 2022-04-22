package Domain;

import Domain.StoreModule.StoreController;
import Service.iService;

public class Market implements iService {
    private StoreController store_controller;

    public Market() {
        this.store_controller = StoreController.get_instance();
    }


    @Override
    public void init_market() {
        //Tom
        //connect to payment service
        //connect to supply service
    }

    @Override
    public boolean payment(int price) {
        //Tom
        return true;
    }

    @Override
    public boolean supply(int user_id, int purchase_id) {
        //Tom
        return true;

    }

    @Override
    public double guest_login() {
        return 0;
    }

    @Override
    public double login(String username, String password) {
        return 0;
    }

    @Override
    public double logout() {
        return 0;
    }

    @Override
    public double register() {
        return 0;
    }

    @Override
    public String find_store_information(int store_id) {
        String info="";
        try
        {
            info = this.store_controller.find_store_information(store_id);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return info;

    }

    @Override
    public String find_product_information(int product_id) {
        String info="";
        try
        {
            info = this.store_controller.find_product_information(product_id);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return info;
    }

    @Override
    public void find_product_by_name() {
        //Tom
    }

    @Override
    public void find_product_by_category() {
        //Tom
    }

    @Override
    public void find_product_by_keyword() {
        //Tom
    }

    @Override
    public double view_user_cart() {
        return 0;
    }

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
        //Tom
    }

    @Override
    public void delete_product_from_store() {
        //Tom
    }

    @Override
    public void edit_product(int store_id, int product_id) {
        //Tom
    }

    @Override
    public void add_discount_rule() {
        //Tom
    }

    @Override
    public void delete_discount_rule() {
        //Tom
    }

    @Override
    public void add_purchase_rule() {
        //Tom
    }

    @Override
    public void delete_purchase_rule() {
        //Tom
    }

    @Override
    public int add_owner() {
        return 0;
    }

    @Override
    public int add_manager() {
        return 0;
    }

    @Override
    public int delete_owner() {
        return 0;
    }

    @Override
    public int delete_manager() {
        return 0;
    }

    @Override
    public void edit_manager_permissions() {

    }

    @Override
    public void close_store() {

    }

    @Override
    public void open_close_store() {

    }

    @Override
    public void view_store_management_information() {

    }

    @Override
    public void view_store_questions() {

    }

    @Override
    public void manager_answer_question() {

    }

    @Override
    public void view_store_purchases_history() {

    }

    @Override
    public void close_store_permanently() {

    }

    @Override
    public double delete_user_from_system() {
        return 0;
    }

    @Override
    public double view_system_questions() {
        return 0;
    }

    @Override
    public double admin_answer_question() {
        return 0;
    }

    @Override
    public int get_system_statistics() {
        return 0;
    }
}