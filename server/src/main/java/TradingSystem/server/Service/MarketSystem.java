package TradingSystem.server.Service;

import TradingSystem.server.Domain.ExternSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Domain.Utils.SystemLogger;

import TradingSystem.server.Domain.Utils.Exception.MarketException;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class MarketSystem {
    // const vars
    public final static String external_system_url = "http://cs-bgu-wsep.herokuapp.com/";
    public final static String instructions_config_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\" +
            "main\\java\\TradingSystem\\server\\Config\\instructions_config.txt";
    public final static String services_config_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\" +
            "main\\java\\TradingSystem\\server\\Config\\services_config.txt";
    //
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;


    public MarketSystem(String services_config_path, String instructions_config_path) {
        this.init_market(services_config_path, instructions_config_path);
    }


    /**
     * Requirement 1.1
     */
    public void init_market(String services_config_path, String instructions_config_path)  {
        SystemLogger.getInstance().add_log("start init market");
        // set external services according config file -> test or real
        boolean config_external_services = this.config_external_services(services_config_path);
        if (!config_external_services){
            // TODO : logger & Exit
        }
        boolean connect_to_external_systems = payment_adapter.handshake() && supply_adapter.handshake();
        if (!connect_to_external_systems) // have to exit
        {
            // TODO : logger & Exit
            System.out.println("cant connect to the external systems");
        }
        boolean config_instructions = this.config_instructions_data(instructions_config_path);
        if (!config_instructions){
            // TODO : BAR answer
        }


        // TODO : load DB
        UserController.load();
        StoreController.load();

        // TODO : remove this try
        try {
//            this.add_admins();
//            this.init_data_to_market(this.payment_adapter, this.supply_adapter);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public PaymentAdapter getPayment_adapter() {
        return payment_adapter;
    }
    public SupplyAdapter getSupply_adapter() {
        return supply_adapter;
    }

    public boolean config_instructions_data(String instructions_config_path){
        HashMap<String, MarketFacade> facades = new HashMap<>();
        try{
            File file = new File(instructions_config_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String instruction = scanner.nextLine();
                String[] instruction_params = instruction.split("#");
                run_instruction(instruction_params, facades);
            }


        } catch (Exception e) {
            // have to reset all the data of the market and stop the method.
            e.printStackTrace();

            // TODO: logger
            for (MarketFacade marketFacade : facades.values()){
                marketFacade.clear();
            }
            facades.clear();
            return false;

        }
        return true;

    }
    private boolean config_external_services(String services_config_path){
        try{
            File file = new File(services_config_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String instruction = scanner.nextLine();
                set_external_services(instruction);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: logger
            return false;

        }
        return true;
    }
    private void set_external_services(String config) throws Exception {
        if (config.equals("tests")){
            this.payment_adapter = new PaymentAdapter() {
                @Override
                public boolean handshake() {
                    return true;
                }

                @Override
                public int payment(PaymentInfo paymentInfo, double price) {
                    return 55000;
                }

                @Override
                public int cancel_pay(int transaction_id) {
                    return 1;
                }
            };
            this.supply_adapter = new SupplyAdapter() {
                @Override
                public int supply(SupplyInfo supplyInfo) {
                    return 66000;
                }

                @Override
                public int cancel_supply(int transaction_id) {
                    return 1;
                }

                @Override
                public boolean handshake() {
                    return true;
                }
            };
        }
        else if (config.equals("real")){
            this.payment_adapter = new PaymentAdapterImpl();
            this.supply_adapter = new SupplyAdapterImpl();
        }
        else {
            throw new Exception("Illegal External Services Config File.");
        }
    }


    public void add_admins() throws MarketException {
        UserController.getInstance().add_admin("admin@gmail.com", "12345678aA", "Barak", "Bahar");
        SystemLogger.getInstance().add_log("admin added");


    }

    private void run_instruction(String[] instruction_params, HashMap<String, MarketFacade> facades) {
        MarketFacade marketFacade;
        String instruction = instruction_params[0];
        String email = instruction_params[1];
        if (facades.containsKey(email))
            marketFacade = facades.get(email);
        else
        {
            marketFacade = new MarketFacade(this.payment_adapter, this.supply_adapter);
            facades.put(email, marketFacade);
        }

        // handle instruction
        if (instruction.equals("login")){
            Response answer = marketFacade.login(email,instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }

        }
        else if (instruction.equals("logout")){
            Response answer = marketFacade.logout();
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("register")){
            Response answer = marketFacade.register(email, instruction_params[2], instruction_params[3], instruction_params[4], instruction_params[5]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("add_product_to_cart")){
            Response answer = marketFacade.add_product_to_cart(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("remove_product_from_cart")){
            Response answer = marketFacade.remove_product_from_cart(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("buy_cart")){
            // TODO : change String to objects
//            Response answer = marketFacade.buy_cart(instruction_params[2], instruction_params[3]);
//            if (answer.WasException()){
//                throw new IllegalArgumentException("Exception");
//            }
        }
        else if (instruction.equals("open_store")){
            Response answer = marketFacade.open_store(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }

        }
        else if (instruction.equals("add_bid")){
            Response answer = marketFacade.add_bid(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]), Double.parseDouble(instruction_params[5]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("rate_product")){
            Response answer = marketFacade.rate_product(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("rate_store")){
            Response answer = marketFacade.rate_store(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("send_question_to_store")){
            Response answer = marketFacade.send_question_to_store(Integer.parseInt(instruction_params[2]),instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("send_question_to_admin")){
            Response answer = marketFacade.send_question_to_admin(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("edit_name")){
            Response answer = marketFacade.edit_name(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("edit_last_name")){
            Response answer = marketFacade.edit_last_name(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("edit_password")){
            Response answer = marketFacade.edit_password(instruction_params[2], instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("improve_security")){
            Response answer = marketFacade.improve_security(instruction_params[2], instruction_params[3], instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("add_product_to_store")){
            ArrayList<String> keywords = new ArrayList();
            for (String str : instruction_params[7].split(",")){
                keywords.add(str);
            }
            Response answer = marketFacade.add_product_to_store(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    instruction_params[4] ,Double.parseDouble(instruction_params[5]), instruction_params[6],keywords);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("delete_product_from_store")){
            Response answer = marketFacade.delete_product_from_store(Integer.parseInt(instruction_params[2]),Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("edit_product_name")){
            Response answer = marketFacade.edit_product_name(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("add_owner")){
            Response answer = marketFacade.add_owner(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("delete_owner")){
            Response answer = marketFacade.delete_owner(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("add_manager")){
            Response answer = marketFacade.add_manager(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("delete_manager")){
            Response answer = marketFacade.delete_manager(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }

        else if (instruction.equals("close_store_temporarily")){
            Response answer = marketFacade.close_store_temporarily(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("open_close_store")){
            Response answer = marketFacade.open_close_store(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }

        }
        else if (instruction.equals("manager_answer_question")){
            Response answer = marketFacade.manager_answer_question(Integer.parseInt(instruction_params[2]),Integer.parseInt(instruction_params[3]),
                    instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }

        }
        else if (instruction.equals("close_store_permanently")){
            Response answer = marketFacade.close_store_permanently(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }

        }

        else if (instruction.equals("remove_user")){
            Response answer = marketFacade.remove_user(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else if (instruction.equals("admin_answer_user_question")){
            Response answer = marketFacade.admin_answer_user_question( Integer.parseInt(instruction_params[2]), instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Exception");
            }
        }
        else{
            throw new IllegalArgumentException("Illegal Instruction Input");
        }




    }
    public void init_data_to_market(PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        String birth_date = LocalDate.now().minusYears(22).toString();
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        // register
        marketFacade1.register("amit@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade2.register("tom@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade3.register("gal@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade4.register("grumet@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade5.register("eylon@gmail.com","12345678aA","amit","moskovitz",birth_date);
        // open store
        marketFacade1.open_store("amit store");
        marketFacade2.open_store("tom store");
        marketFacade3.open_store("gal store");
        marketFacade4.open_store("grumet store");
        marketFacade5.open_store("eylon store");
        // add products to stores
        marketFacade1.add_product_to_store(1,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade2.add_product_to_store(2,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade3.add_product_to_store(3,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade4.add_product_to_store(4,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade5.add_product_to_store(5,50,"iphoneS",2999.9, "electronic",new LinkedList<>());

        marketFacade5.add_product_to_store(5,50,"iphone",2999.9, "electronic",new LinkedList<>());
        // add products to cart
        marketFacade1.add_product_to_cart(1,1,20);
//        marketFacade1.add_product_to_cart(2,2,20);
//        marketFacade1.add_product_to_cart(3,3,20);
//        marketFacade1.add_product_to_cart(4,4,20);
//        marketFacade1.add_product_to_cart(5,5,20);
//        marketFacade1.add_product_to_cart(5,6,20);

        // buy from store
//        marketFacade1.buy_cart("Payment Info", "Supply Info");

        //enter more products to cart after purchase
//        marketFacade1.add_product_to_cart(1,1,20);
//        marketFacade1.add_product_to_cart(2,2,20);
//        marketFacade1.add_product_to_cart(3,3,20);
//        marketFacade1.add_product_to_cart(4,4,20);


        marketFacade1.add_bid(2,2, 10, 1500);
        marketFacade2.manager_answer_bid(2,1,true,-1);
        PaymentInfo paymentInfo = new PaymentInfo();
        SupplyInfo supplyInfo = new SupplyInfo();
        marketFacade1.buy_cart(paymentInfo, supplyInfo);
        marketFacade2.add_product_to_cart(1,1,1);
        marketFacade2.buy_cart(paymentInfo,supplyInfo);
        marketFacade2.send_question_to_store(1,"why");

        // logout
        marketFacade1.logout();
        marketFacade2.logout();
        marketFacade3.logout();
        marketFacade4.logout();
        marketFacade5.logout();





    }
}
