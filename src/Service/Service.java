package Service;

import Domain.Facade.MarketFacade;
import Domain.Facade.iFacade;
import Domain.StoreModule.StorePermission;

import java.util.LinkedList;
import java.util.List;

public class Service {
    private iFacade marketFacade;
    public Service(iFacade market_facade){
        this.marketFacade = market_facade;

    }
    public String handle_query(int opcode, String[] data) {
        String answer = "";
        String email;
        String password;
        String review;
        String question;
        int store_id;
        int product_id;
        int question_id;
        String question_answer = "";
        String store_name;
        String product_name;
        String product_category;
        int quantity;
        int rate;
        String PaymentInfo = "";
        String SupplyInfo = "";
        double price;
        String email_to_appoint = "";
        String new_password = "";
        // @TODO : build keywords & permissions
        List<String> keywords = new LinkedList<>();
        LinkedList<StorePermission> permissions = new LinkedList<>();


        switch (opcode) {
            case 1:
                email = data[0];
                password = data[1];
                answer = marketFacade.login(email, password);
                break;
            case 2:
                answer = marketFacade.logout();
                break;
            case 3:
                email = data[0];
                password = data[1];
                String first_name = data[2];
                String last_name = data[3];
                answer = marketFacade.register(email, password, first_name, last_name);
                break;
            case 4:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.find_store_information(store_id);
                break;
            case 5:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.find_product_information(product_id, store_id);
                break;
            case 6:
                product_name = data[0];
                answer = marketFacade.find_products_by_name(product_name);
                break;
            case 7:
                product_category = data[0];
                answer = marketFacade.find_products_by_category(product_category);
                break;
            case 8:
                // TODO : build keywords
                answer = marketFacade.find_products_by_keywords(data[0]);
                break;
            case 9:
                store_id = Integer.parseInt(data[0]);
                product_id = Integer.parseInt(data[1]);
                quantity = Integer.parseInt(data[2]);
                answer = marketFacade.add_product_to_cart(store_id,product_id,quantity);
                break;
            case 10:
                store_id = Integer.parseInt(data[0]);
                product_id = Integer.parseInt(data[1]);
                answer = marketFacade.remove_product_from_cart(store_id, product_id);
                break;
            case 11:
                answer = marketFacade.view_user_cart();
                break;
            case 12:
                store_id = Integer.parseInt(data[0]);
                product_id = Integer.parseInt(data[1]);
                quantity = Integer.parseInt(data[2]);
                answer = marketFacade.edit_product_quantity_in_cart(store_id,product_id,quantity);
                break;
            case 13:
                answer = marketFacade.buy_cart(PaymentInfo, SupplyInfo);
                break;
            case 14:
                store_name = data[0];
                answer = marketFacade.open_store(store_name);
                break;
            case 15:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                review = data[2];
                answer = marketFacade.add_product_review(product_id, store_id, review);
                break;
            case 16:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                rate = Integer.parseInt(data[2]);
                answer = marketFacade.rate_product(product_id, store_id,rate);
                break;
            case 17:
                store_id = Integer.parseInt(data[0]);
                rate = Integer.parseInt(data[1]);
                answer = marketFacade.rate_store(store_id,rate);
                break;
            case 18:
                store_id = Integer.parseInt(data[0]);
                question = data[1];
                answer = marketFacade.send_question_to_store(store_id, question);
                break;
            case 19:
                question = data[0];
                answer = marketFacade.send_question_to_admin(question);
                break;
            case 20:
                answer = marketFacade.view_user_purchase_history();
                break;
            case 21:
                answer = marketFacade.get_user_email();
                break;
            case 22:
                answer = marketFacade.get_user_name();
                break;
            case 23:
                answer = marketFacade.get_user_last_name();
                break;
            case 24:
                store_id = Integer.parseInt(data[0]);
                quantity = Integer.parseInt(data[1]);
                product_name = data[2];
                price = Double.parseDouble(data[3]);
                product_category = data[4];
                // TODO : build keywords
                answer = marketFacade.add_product_to_store(store_id,quantity,product_name, price, product_category, keywords);
                break;
            case 25:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.delete_product_from_store(product_id, store_id);
                break;
            case 26:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                store_name = data[2];
                answer = marketFacade.edit_product_name(product_id, store_id, store_name);
                break;
            case 27:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                price = Double.parseDouble(data[2]);
                answer = marketFacade.edit_product_price(product_id,store_id,price);
                break;
            case 28:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                product_category = data[2];
                answer = marketFacade.edit_product_category(product_id,store_id,product_category);
                break;
            case 29:
                product_id = Integer.parseInt(data[0]);
                store_id = Integer.parseInt(data[1]);
                // TODO : build keywords
                answer = marketFacade.edit_product_key_words(product_id,store_id, keywords);
                break;
            case 30:
                // TODO : implement this method after talk with amit.g
                //answer = marketFacade.set_store_purchase_rules(1);
                break;
            case 31:
                email_to_appoint = data[0];
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.add_owner(email_to_appoint, store_id);
                break;
            case 32:
                email_to_appoint = data[0];
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.delete_owner(email_to_appoint, store_id);
                break;
            case 33:
                email_to_appoint = data[0];
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.add_manager(email_to_appoint, store_id);
                break;
            case 34:
                email_to_appoint = data[0];
                store_id = Integer.parseInt(data[1]);
                // TODO : build permissions
                answer = marketFacade.edit_manager_permissions(email_to_appoint, store_id, permissions);
                break;
            case 35:
                email_to_appoint = data[0];
                store_id = Integer.parseInt(data[1]);
                answer = marketFacade.delete_manager(email_to_appoint, store_id);
                break;
            case 36:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.close_store_temporarily(store_id);
                break;
            case 37:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.open_close_store(store_id);
                break;
            case 38:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.view_store_management_information(store_id);
                break;
            case 39:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.manager_view_store_questions(store_id);
                break;
            case 40:
                store_id = Integer.parseInt(data[0]);
                question_id = Integer.parseInt(data[1]);
                question_answer = data[2];
                answer = marketFacade.manager_answer_question(store_id,question_id,question_answer);
                break;
            case 41:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.view_store_purchases_history(store_id);
                break;
            case 42:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.close_store_permanently(store_id);
                break;
            case 43:
                email_to_appoint = data[0];
                answer = marketFacade.remove_user(email_to_appoint);
                break;
            case 44:
                answer = marketFacade.admin_view_users_questions();
                break;
            case 45:
                question_id = Integer.parseInt(data[0]);
                question_answer = data[1];
                answer = marketFacade.admin_answer_user_question(question_id,question_answer);
                break;
            case 46:
                answer = marketFacade.view_user_purchase_history();
                break;
            case 47:
                answer = marketFacade.get_market_stats();
                break;
            case 48:
                password = data[0];
                first_name = data[1];
                answer = marketFacade.edit_name(password, first_name);
                break;
            case 49:
                password = data[0];
                last_name = data[1];
                answer = marketFacade.edit_last_name(password, last_name);
                break;
            case 50:
                password = data[0];
                new_password = data[1];
                answer = marketFacade.edit_password(password, new_password);
                break;
            case 51:
                password = data[0];
                answer = marketFacade.unregister(password);
                break;
            case 52:
                email = data[0];
                answer = marketFacade.admin_view_user_purchases_history(email);
                break;
            case 53:
                store_id = Integer.parseInt(data[0]);
                answer = marketFacade.admin_view_store_purchases_history(store_id);
                break;

        }
        return answer;
    }


}
