package Service;

import Domain.ExternSystems.PaymentAdapter;
import Domain.ExternSystems.SupplyAdapter;
import Domain.Facade.MarketFacade;
import Domain.Facade.iFacade;
import Domain.Utils.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private iFacade marketFacade;

    // -- Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,
                         PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.marketFacade = (iFacade) new MarketFacade(paymentAdapter, supplyAdapter);
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {
                // receive query from client
                received = dis.readUTF();
                // check valid message
                int opcode = this.get_opcode(received);
                if (opcode == 300) {
                    // exit opcode
                    break;
                }

                toreturn = this.handle_query(opcode, received);
                dos.writeUTF(toreturn);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // closing resources & update stats
        this.close_connection();


    }

    private void close_connection() {
        try {
            this.s.close();
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int get_opcode(String received) {
        return 1;
        // 300 - exit opcode
    }

    private String handle_query(int opcode, String received) {
        String answer = "";
        String password = "";
        switch (opcode) {
            case 1:
                password = Utils.gen_pass(password);
                answer = marketFacade.login(received, password);
                break;
            case 2:
                answer = marketFacade.logout();
                break;
            case 3:
                password = Utils.gen_pass(password);
                answer = marketFacade.register(received, password, received, received);
                break;
            case 4:
                answer = marketFacade.find_store_information(1);
                break;
            case 5:
                answer = marketFacade.find_product_information(1, 1);
                break;
            case 6:
                answer = marketFacade.find_products_by_name("amit");
                break;
            case 7:
                answer = marketFacade.find_products_by_category("amit");
                break;
            case 8:
                answer = marketFacade.find_products_by_keywords("amit");
                break;
            case 9:
                answer = marketFacade.add_product_to_cart(1,1,1);
                break;
            case 10:
                answer = marketFacade.remove_product_from_cart(1, 1);
                break;
            case 11:
                answer = marketFacade.view_user_cart();
                break;
            case 12:
                answer = marketFacade.edit_product_quantity_in_cart(1,1,1);
                break;
            case 13:
                answer = marketFacade.buy_cart(received, received);
                break;
            case 14:
                answer = marketFacade.open_store("1");
                break;
            case 15:
                answer = marketFacade.add_product_review(1, 1, "good");
                break;
            case 16:
                answer = marketFacade.rate_product(1, 5,5);
                break;
            case 17:
                answer = marketFacade.rate_store(1,5);
                break;
            case 18:
                answer = marketFacade.send_question_to_store(1, "why?");
                break;
            case 19:
                answer = marketFacade.send_question_to_admin("why?");
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
                answer = marketFacade.add_product_to_store(1,5,"computer",56.3, "electronic", new LinkedList<>());
                break;
            case 25:
                answer = marketFacade.delete_product_from_store(1, 1);
                break;
            case 26:
                answer = marketFacade.edit_product_name(1, 1, "amit");
                break;
            case 27:
                answer = marketFacade.edit_product_price(1,1,55);
                break;
            case 28:
                answer = marketFacade.edit_product_category(1,1,"amit");
                break;
            case 29:
                answer = marketFacade.edit_product_key_words(1,1, new LinkedList<>());
                break;
            case 30:
                answer = marketFacade.set_store_purchase_rules(1);
                break;
            case 31:
                answer = marketFacade.add_owner("amit@gmail,com", 1);
                break;
            case 32:
                answer = marketFacade.delete_owner("amit@gmail,com", 1);
                break;
            case 33:
                answer = marketFacade.add_manager("amit@gmail,com", 1);
                break;
            case 34:
                answer = marketFacade.edit_manager_permissions("amit@gmail,com", 1,new LinkedList<>());
                break;
            case 35:
                answer = marketFacade.delete_manager("amit@gmail,com", 1);
                break;
            case 36:
                answer = marketFacade.close_store_temporarily(5);
                break;
            case 37:
                answer = marketFacade.open_close_store(5);
                break;
            case 38:
                answer = marketFacade.view_store_management_information(1);
                break;
            case 39:
                answer = marketFacade.manager_view_store_questions(1);
                break;
            case 40:
                answer = marketFacade.manager_answer_question(1,1,"1");
                break;
            case 41:
                answer = marketFacade.view_store_purchases_history(1);
                break;
            case 42:
                answer = marketFacade.close_store_permanently(1);
                break;
            case 43:
                answer = marketFacade.remove_user("amit@gmail,com");
                break;
            case 44:
                answer = marketFacade.admin_view_users_questions();
                break;
            case 45:
                answer = marketFacade.admin_answer_user_question(1,"amit");
                break;
            case 46:
                answer = marketFacade.view_user_purchases_history("amit");
                break;
            case 47:
                answer = marketFacade.get_market_stats();
                break;
            case 48:
                answer = marketFacade.edit_name("amit@gmail,com", "tom");
                break;
            case 49:
                answer = marketFacade.edit_last_name("amit@gmail,com", "tom");
                break;
            case 50:
                String pw = Utils.gen_pass("old_password");
                String password123 = Utils.gen_pass("new_password");
                answer = marketFacade.edit_password(pw, password123);
                break;
            case 51:
                password = Utils.gen_pass(password);
                answer = marketFacade.unregister(password);
                break;
            case 52:
                answer = marketFacade.admin_view_user_purchases_history("amit");
                break;
            case 53:
                answer = marketFacade.admin_view_store_purchases_history(5);
        }
        return answer;
    }

}
