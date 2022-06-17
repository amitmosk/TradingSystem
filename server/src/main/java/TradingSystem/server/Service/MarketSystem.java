package TradingSystem.server.Service;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.ExitException;
import TradingSystem.server.Domain.Utils.Logger.MarketLogger;
import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import TradingSystem.server.Domain.Utils.Response;

import TradingSystem.server.Domain.Utils.Exception.MarketException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class MarketSystem {
    // const vars
    public final static String external_system_url = "http://cs-bgu-wsep.herokuapp.com/";
    public final static String tests_config_file_path = "..\\server\\src\\main\\java\\TradingSystem\\server\\Config\\tests_config.txt";
    public static String instructions_config_path = "..\\server\\src\\main\\java\\TradingSystem\\server\\Config\\instructions_config.txt";
    public final static String system_config_path = "..\\server\\src\\main\\java\\TradingSystem\\server\\Config\\system_config.txt";
    //
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;


    public MarketSystem(String system_config_path, String instructions_config_path1) throws ExitException {
        instructions_config_path = instructions_config_path1;
        this.init_market(system_config_path);
    }


    /**
     * Requirement 1.1
     */
    public void init_market(String config_file_path) throws ExitException{
        SystemLogger.getInstance().add_log("Start Init Market");
        SystemLogger.getInstance().add_log("Configuration File Path: "+config_file_path);
        String[] instructions;
        instructions = read_config_file(config_file_path);
        String external_services_instruction = instructions[0];
        set_external_services(external_services_instruction);
        connect_to_external_services();
        String database_instruction = instructions[1];
        set_database(database_instruction);


    }

    public PaymentAdapter getPayment_adapter() {
        return payment_adapter;
    }
    public SupplyAdapter getSupply_adapter() {
        return supply_adapter;
    }


    /**
     * reading the data from the configuration file.
     * @param config_path the path of the configuration file.
     * @return the 2 config instructions, 1) external services 2) database
     * @throws ExitException if the format file is unmatched.
     */
    public String[] read_config_file(String config_path) throws ExitException {
        String[] to_return = new String[2];
        int counter = 0;
        try {
            File file = new File(config_path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                if (!instruction.equals("")) {
                    if (counter > 1){
                        throw new ExitException("Config File - Illegal Format.");
                    }
                    to_return[counter]  = instruction;
                    counter++;
                }
            }
        }
        catch (FileNotFoundException e) {throw new ExitException("Config File - File Not Found");}
        if (counter != 2) {throw new ExitException("Config File - Format File Unmatched.");}
        return to_return;
    }

    /** Connect the system to the external services after set the services according the configuration file.
     * @throws ExitException if the handshake fail.
     */
    private void connect_to_external_services() throws ExitException {
        SystemLogger.getInstance().add_log("System Start Connect To External Services");
        boolean connect_to_external_systems = payment_adapter.handshake() && supply_adapter.handshake();
        if (!connect_to_external_systems) // have to exit
        {
            throw new ExitException("Cant Connect To The External Systems");
        }
    }

    /**
     * Requirement 1.3 & 1.4
     *
     * this method crate adapters to the external services.
     * @param config - "external_services:demo" or "external_services:real"
     * @throws ExitException if the input is illegal.
     */
    public void set_external_services(String config) throws ExitException {
        if (config.equals("external_services:tests")){
            SystemLogger.getInstance().add_log("Set Tests External Services");
            this.payment_adapter = new PaymentAdapterTests();
            this.supply_adapter = new SupplyAdapterTests();
            NotificationHandler.setTestsHandler();
        }
        else if (config.equals("external_services:fail_tests")){
            SystemLogger.getInstance().add_log("Set Denied Tests External Services");
            this.payment_adapter = new PaymentAdapter() {
                @Override
                public boolean handshake() {
                    return false;
                }

                @Override
                public int payment(PaymentInfo paymentInfo, double price) {
                    return -1;
                }

                @Override
                public int cancel_pay(int transaction_id) {
                    return -1;
                }
            };
            this.supply_adapter = new SupplyAdapterTests();
            NotificationHandler.setTestsHandler();
        }
        else if (config.equals("external_services:real")){
            SystemLogger.getInstance().add_log("Set Real External Services");
            this.payment_adapter = new PaymentAdapterImpl();
            this.supply_adapter = new SupplyAdapterImpl();
        }
        else {
            throw new ExitException("System Config File - Illegal External Services Data.");
        }
    }

    /**
     * this method init system database,
     * if the demo option on, the system will init data from the data config file,
     *      the init can failed and system keep running without data.
     * if the real option on, the method will try to connect the real database.
     * @param config - configuration instruction - "database:demo" or "database:real".
     * @throws ExitException if the connection to DB fail OR wrong format of the config instruction.
     */
    private void set_database(String config) throws ExitException{
        // database:real/demo
        if (config.equals("database:tests")){
            SystemLogger.getInstance().add_log("Init Data For Tests: Empty Database");
            HibernateUtils.set_tests_mode();
        }
        else if (config.equals("database:real")){
            try
            {
                HibernateUtils.set_normal_use();
                SystemLogger.getInstance().add_log("Init Data From Database");
                UserController.load();
                StoreController.load();
            }
            catch (Exception e){
                throw new ExitException("Cant Connect To Database.");
            }
        }
        else if (config.equals(("database:demo"))){
            HibernateUtils.set_demo_use();
            SystemLogger.getInstance().add_log("Init Data From Demo, Data File Path: "+instructions_config_path);
            init_data_to_market(instructions_config_path);
//            this.add_admins();
//            this.init_data_to_market_develop(payment_adapter, supply_adapter);
        }
        else {
            throw new ExitException("System Config File - Illegal Database Data.");
        }
    }

    /**
     * init date from the instructions configuration file.
     * this method should keep the logic order of system instructions.
     * "" is legal input -> the method wouldn't do anything and keep going.
     * @param instructions_config_path - location of the instruction config file.
     * @return true if the system load data successfully.
     *  false if was illegal instructions order OR illegal format instruction.
     */
    public void init_data_to_market(String instructions_config_path){
        HashMap<String, MarketFacade> facades = new HashMap<>();
        try{
            File file = new File(instructions_config_path);
            Scanner scanner = new Scanner(file);
            HibernateUtils.beginTransaction();
            HibernateUtils.setBegin_transaction(false);
            while (scanner.hasNextLine()){
                String instruction = scanner.nextLine();
                if (!instruction.equals("")){
                    String[] instruction_params = instruction.split("#");
                    run_instruction(instruction_params, facades);
                }
            }
            HibernateUtils.setBegin_transaction(true);
            HibernateUtils.commit();
        } catch (Exception e) {
            HibernateUtils.setBegin_transaction(true);
            HibernateUtils.rollback();
            SystemLogger.getInstance().add_log("Init Data Demo Fail, The System Run With No Data :" + e.getMessage());
            // have to reset all the data of the market and stop the method.
            for (MarketFacade marketFacade : facades.values()){
                marketFacade.clear();
            }
            facades.clear();
        }
    }

    /**
     * execute instructions from the init data config file.
     * @param instruction_params - instruction to execute.
     * @param facades - the data structure who managed the initialization of data.
     * @throws Exception in bad format or bad logical order of instructions.
     */
    private void run_instruction(String[] instruction_params, HashMap<String, MarketFacade> facades) throws Exception {
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

        // handle instructions :
        if (instruction.equals("login")){
            Response answer = marketFacade.login(email,instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Login Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("add_admin")){
            try{
                this.add_admin(email);
            }
            catch (Exception e){
                throw new IllegalArgumentException("Add Admin Fail:" + e.getMessage());
            }
        }
        else if (instruction.equals("logout")){
            Response answer = marketFacade.logout();
            if (answer.WasException()){
                throw new IllegalArgumentException("Logout Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("register")){
            Response answer1 = marketFacade.register(email, instruction_params[2], instruction_params[3], instruction_params[4], instruction_params[5]);
            Response answer2 = marketFacade.logout();
            if (answer1.WasException() || answer2.WasException()){
                throw new IllegalArgumentException("Register Failed: " + answer1.getMessage());
            }
        }
        else if (instruction.equals("add_product_to_cart")){
            Response answer = marketFacade.add_product_to_cart(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Add Product To Cart Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("remove_product_from_cart")){
            Response answer = marketFacade.remove_product_from_cart(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Remove Product From Cart Failed: " + answer.getMessage());
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
                throw new IllegalArgumentException("Open Store Failed: " + answer.getMessage());
            }

        }
        else if (instruction.equals("add_bid")){
            Response answer = marketFacade.add_bid(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]), Double.parseDouble(instruction_params[5]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Add Bid Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("rate_product")){
            Response answer = marketFacade.rate_product(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    Integer.parseInt(instruction_params[4]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Rate Product Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("rate_store")){
            Response answer = marketFacade.rate_store(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Rate Store Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("send_question_to_store")){
            Response answer = marketFacade.send_question_to_store(Integer.parseInt(instruction_params[2]),instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Send Question To Store Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("send_question_to_admin")){
            Response answer = marketFacade.send_question_to_admin(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Send Question To Admin Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("edit_name")){
            Response answer = marketFacade.edit_name(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Edit Name Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("edit_last_name")){
            Response answer = marketFacade.edit_last_name(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Edit Last Name Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("edit_password")){
            Response answer = marketFacade.edit_password(instruction_params[2], instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Edit Password Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("improve_security")){
            Response answer = marketFacade.improve_security(instruction_params[2], instruction_params[3], instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Improve Security Failed: " + answer.getMessage());
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
                throw new IllegalArgumentException("Add Product To Store Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("delete_product_from_store")){
            Response answer = marketFacade.delete_product_from_store(Integer.parseInt(instruction_params[2]),Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Remove Product From Store Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("edit_product_name")){
            Response answer = marketFacade.edit_product_name(Integer.parseInt(instruction_params[2]), Integer.parseInt(instruction_params[3]),
                    instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Edit Product Name Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("add_owner")){
            Response answer = marketFacade.add_owner(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Add Owner Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("delete_owner")){
            Response answer = marketFacade.delete_owner(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Remove Owner Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("add_manager")){
            Response answer = marketFacade.add_manager(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Add Manager Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("delete_manager")){
            Response answer = marketFacade.delete_manager(instruction_params[2], Integer.parseInt(instruction_params[3]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Delete Manager Failed: " + answer.getMessage());
            }
        }

        else if (instruction.equals("close_store_temporarily")){
            Response answer = marketFacade.close_store_temporarily(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Close Store Temporarily Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("open_close_store")){
            Response answer = marketFacade.open_close_store(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Open Close Store Failed: " + answer.getMessage());
            }

        }
        else if (instruction.equals("manager_answer_question")){
            Response answer = marketFacade.manager_answer_question(Integer.parseInt(instruction_params[2]),Integer.parseInt(instruction_params[3]),
                    instruction_params[4]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Manager Answer Question Failed: " + answer.getMessage());
            }

        }
        else if (instruction.equals("close_store_permanently")){
            Response answer = marketFacade.close_store_permanently(Integer.parseInt(instruction_params[2]));
            if (answer.WasException()){
                throw new IllegalArgumentException("Close Store Permanently Failed: " + answer.getMessage());
            }

        }
        else if (instruction.equals("buy_cart")){
            PaymentInfo p = new PaymentInfo();
            SupplyInfo s = new SupplyInfo();
            Response answer = marketFacade.buy_cart(p, s);
            if (answer.WasException()){
                throw new IllegalArgumentException("Buy Cart Failed: " + answer.getMessage());
            }

        }
        else if (instruction.equals("remove_user")){
            Response answer = marketFacade.remove_user(instruction_params[2]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Remove User Failed: " + answer.getMessage());
            }
        }
        else if (instruction.equals("admin_answer_user_question")){
            Response answer = marketFacade.admin_answer_user_question( Integer.parseInt(instruction_params[2]), instruction_params[3]);
            if (answer.WasException()){
                throw new IllegalArgumentException("Admin Answer Question Failed: " + answer.getMessage());
            }
        }
        else{
            throw new Exception("Illegal Instruction Input");
        }
    }


    public void add_admin(String email) throws MarketException {
        User user = UserController.get_instance().get_user_by_email(email);
        String name = user.getState().get_user_name();
        String last_name = user.getState().get_user_last_name();

        user.set_admin(user.user_email(), "12345678aA", name, last_name);
        SystemLogger.getInstance().add_log("New Admin In The Market: "+email);
        MarketLogger.getInstance().add_log("New Admin In The Market: "+email);
    }

    public void init_data_to_market_develop(PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        String birth_date = LocalDate.now().minusYears(22).toString();
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        // register
        marketFacade1.register("amit@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade2.register("tom@gmail.com","12345678aA","Tom","moskovitz",birth_date);
        marketFacade3.register("gal@gmail.com","12345678aA","Gal","moskovitz",birth_date);
        marketFacade4.register("grumet@gmail.com","12345678aA","amitG","moskovitz",birth_date);
        marketFacade5.register("eylon@gmail.com","12345678aA","Eylon","moskovitz",birth_date);
        // open store
        marketFacade1.open_store("amit store");
        marketFacade2.open_store("tom store");
        marketFacade3.open_store("gal store");
        marketFacade4.open_store("grumet store");
        marketFacade5.open_store("eylon store");
        // add products to stores
        marketFacade1.add_product_to_store(1,50,"iphoneA",2999.9, "electronic",new LinkedList<>());
        marketFacade2.add_product_to_store(2,50,"iphoneT",2999.9, "electronic",new LinkedList<>());
        marketFacade3.add_product_to_store(3,50,"iphoneG",2999.9, "electronic",new LinkedList<>());
        marketFacade4.add_product_to_store(4,50,"iphoneAG",2999.9, "electronic",new LinkedList<>());
        marketFacade5.add_product_to_store(5,50,"iphoneE",2999.9, "electronic",new LinkedList<>());

        marketFacade5.add_product_to_store(5,50,"iphoneE2",2999.9, "electronic",new LinkedList<>());
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

    public void add_admins() throws MarketException {
        HibernateUtils.beginTransaction();
        UserController.get_instance().add_admin("admin@gmail.com", "12345678aA", "Barak", "Bahar");
        HibernateUtils.commit();
        SystemLogger.getInstance().add_log("admin added");
        MarketLogger.getInstance().add_log("admin added");
    }
}