package Domain.StoreModule;

import Domain.Utils.Utils;

import java.time.LocalDate;
import java.util.*;


public class Store {
    // -- fields
    private int store_id;
    private String founder_email;
    private HashMap<String, Appointment> stuff_emails_and_appointments;
    private String name;
    private LocalDate foundation_date;
    private HashMap<Product, Integer> inventory; // product & quantity
    private int product_ids_counter;
    private boolean active;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private HashMap<Integer, Question> users_questions; // question_id x question
    private StorePurchaseHistory purchases_history;
    private StoreReview storeReview;
    private int question_ids_counter;


    public Store(int store_id, String founder_email, String name) {
        this.store_id = store_id;
        this.founder_email = founder_email;
        this.name = name;
        this.product_ids_counter = 1;
        this.question_ids_counter = 1;
        this.active = true;
        this.foundation_date = LocalDate.now();
        this.users_questions = new HashMap<>();
        this.storeReview = new StoreReview();
        this.purchases_history = new StorePurchaseHistory();
    }


    private int getInc_product_id() {
        return this.product_ids_counter++;
    }

    private int getInc_question_id() {
        return this.question_ids_counter++;
    }





    // -- methods

    public void add_review(String user_email, String review) {
        this.storeReview.add_review(user_email, review);
    }
    public void add_rating(String user_email, int rating) {
        this.storeReview.add_rating(user_email, rating);
    }
    public void add_product_rating(String user_email, int product_id, int rate) {
        Product p = this.getProduct_by_product_id(product_id);//throws
        p.add_rating(user_email, rate);
    }


    public void close_store_temporarily(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.close_store_temporarily);
        this.active = false;
        // TODO:  send message to all of the managers & owners.
    }
    public void open_close_store(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.open_close_store);
        if (this.is_active())
            throw new IllegalArgumentException("The store is already open");
        this.active = true;
        // TODO: send message to all of the managers & owners.
    }
    public String view_store_management_information(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_permissions);
        return this.view_management_information();
    }

    private String view_management_information() {
        //@TODO: string builder informatoin
        return "";
    }

    public void set_permissions(String user_email, String manager_email, LinkedList<StorePermission> permissions) {
        // check that the manager appointed by the user
        if (this.get_appointer(manager_email).equals(user_email))
            throw new IllegalArgumentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager_email == user_email)
            throw new IllegalArgumentException("User cant change himself permissions");

        Appointment manager_permission = this.stuff_emails_and_appointments.get(manager_email);
        manager_permission.set_permissions(permissions);

    }
    public boolean is_active() {
        return this.active;
    }
    public String get_appointer(String manager_email) {
        return this.stuff_emails_and_appointments.get(manager_email).getAppointer_email();
    }


    public List<String> view_store_questions(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_users_questions);
        List<String> questionsList_to_return = new LinkedList<String>();
        for (Question question : this.users_questions.values())
        {
            String temp = question.toString();
            questionsList_to_return.add(temp);
        }
        return questionsList_to_return;
    }



    public void add_question(String user_email, String question_message) {
        int question_id = this.getInc_question_id();
        Question question_to_add = new Question(question_id, this.store_id, user_email, question_message);
        this.users_questions.put(question_id, question_to_add);
    }



    public void answer_question(String user_email, int question_id, String answer) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_users_questions);
        if (!this.users_questions.containsKey(question_id))
        {
            throw new IllegalArgumentException("Question does not exist");
        }
        Question question = this.users_questions.get(question_id);
        question.setAnswer(answer);
    }

    public String view_store_purchases_history(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_purchases_history);
        return this.purchases_history.toString();
    }

    public boolean close_store_permanently() {
        if (!this.is_active())
            return false;
        this.active = false;
        // TODO: send message to all of the managers & owners.
        this.founder_email = null;
        this.stuff_emails_and_appointments = null;
        return true;
    }

    public void check_permission(String user_email, StorePermission permission) throws IllegalAccessException {
        // not just managers - FIX
        if(!(this.stuff_emails_and_appointments.get(user_email).has_permission(permission)))
            throw new IllegalAccessException("User has no permissions!");
    }





// @TODO : we changed a lot of fields, have to match the method
    public String toString() {
        String founder_name = "----------------------";
        StringBuilder info = new StringBuilder();
        info.append("Store info: "+this.name+"\n");
        info.append("\tStore founder: "+ founder_name +"\n");
        info.append("\tStore owners: ");
        for (String email : stuff_emails_and_appointments.keySet())
        {
            String name = "";
            info.append(name+", ");
        }
        info.append("\n");
        info.append("\tStore managers: ");
        for (String email : stuff_emails_and_appointments.keySet())
        {
            String name = "";
            info.append(name+", ");
        }
        info.append("\n");
        info.append("\tfoundation date: "+ Utils.DateToString(this.foundation_date)+"\n");


        //products


        String is_active;
        if (active)
            is_active="Yes";
        else
            is_active="No";

        info.append("\tactive: "+ is_active+"\n");
        info.append("\tpurchase policy: "+ this.purchasePolicy+"\n");
        info.append("\tdiscount policy: "+ this.discountPolicy+"\n");

        return info.toString();

    }

    public boolean is_product_exist(int product_id, int store_id) {
        for(Product p: this.inventory.keySet())
        {
            if(p.getProduct_id() == product_id)
            {
                return true;
            }
        }
        return false;
    }

    //------------------------------------------------find product by - Start ----------------------------------------------------
    public String get_product_information(int product_id) {
        Product p = this.getProduct_by_product_id(product_id); //throws exception
        return p.toString();
    }


    public List<Product> find_products_by_name(String product_name) {
        List<Product> products = new ArrayList<>();
        for (Product p:inventory.keySet()) {
            if (p.getName().equals(name))
            {
                products.add(p);
            }
        }
        return products;

    }

    public List<Product> find_products_by_category(String category) {
        List<Product> products = new ArrayList<>();
        for (Product p:inventory.keySet()) {
            if (p.getCategory().equals(category))
            {
                products.add(p);
            }
        }
        return products;
    }

    public List<Product> find_products_by_key_words(String key_words) {
        List<Product> products = new ArrayList<>();
        for (Product p:inventory.keySet()) {
            if (p.getKey_words().equals(key_words))
            {
                products.add(p);
            }
        }
        return products;
    }
//------------------------------------------------find product by - End ----------------------------------------------------


    public Product getProduct_by_product_id(int product_id)
    {
        for (Product product : this.inventory.keySet()){
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new IllegalArgumentException("StoreController:getProduct_by_product_id Product is not exist - product id ");
    }

    public void add_product(String user_email, String name, double price, String category, List<String> key_words, int quantity) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_item);
        int product_id = this.getInc_product_id();
        Product product = new Product(name, this.store_id, product_id, price, category, key_words);
        inventory.put(product, quantity);
    }

    public void delete_product(int product_id, String user_email) throws IllegalAccessException {
        Product product_to_remove = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.remove_item);
        inventory.remove(product_to_remove);
    }

    //------------------------------------------------ edit product - Start ----------------------------------------------

    public void edit_product_name(String user_email, int product_id, String name) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_name);
        to_edit.setName(name);
    }

    public void edit_product_price(String user_email, int product_id, double price) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_price);
        to_edit.setPrice(price);
    }

    public void edit_product_category(String user_email, int product_id, String category) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_category);
        to_edit.setCategory(category);
    }

    public void edit_product_key_words(String user_email, int product_id, List<String> key_words) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_keywords);
        to_edit.setKey_words(key_words);
    }

    //------------------------------------------------ edit product - End ----------------------------------------------





    public double check_available_products_and_calc_price(Basket basket) {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for (Product p : products_and_quantities.keySet())
        {
            this.checkAvailablityAndGet(p.getProduct_id(), products_and_quantities.get(p));
        }

        return basket.getTotal_price();
    }

    public Product checkAvailablityAndGet(int product_id, int quantity) {
        Product p = this.getProduct_by_product_id(product_id);
        if (p == null)
        {
            throw new IllegalArgumentException("Store.checkAvailablityAndGet: Product is not exist , product id: "+product_id);
            //not suppose to happen
            //add to logger
        }
        int product_quantity = this.inventory.get(p);
        if (quantity <= product_quantity)
        {
            return p;
        }
        throw new IllegalArgumentException("Store.checkAvailablityAndGet: Product is not available , product id: "+product_id+" quantity: "+quantity);
    }


    public void remove_basket_products_from_store(Basket basket, int purchase_id) {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();

        for (Product p : products_and_quantities.keySet())
        {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove < 0)
                throw new IllegalArgumentException("Store.remove_basket_products_from_store: product quantity :" + quantity_to_remove + "" +
                        " is more then available for product id :"+p.getProduct_id());
        }
        for (Product p : products_and_quantities.keySet())
        {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove == 0)
                this.inventory.remove(p);
            else
                this.inventory.put(p, first_quantity - quantity_to_remove);
        }
        StorePurchase purchase = new StorePurchase(basket.getBuyer_id(), purchase_id, basket.getTotal_price(),
                basket.get_productsIds_and_quantity(), this.get_product_ids_and_total_price(basket));
        this.purchases_history.insert(purchase);

    }

    private Map<Integer, Double> get_product_ids_and_total_price(Basket basket) {
        Map<Integer, Double> productsIds_and_totalPrice = new HashMap<>();
        Map <Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for(Product p: products_and_quantities.keySet())
        {
            int quantity = products_and_quantities.get(p);
            productsIds_and_totalPrice.put(p.getProduct_id(), this.calc_product_price(p, quantity));
        }
        return productsIds_and_totalPrice;
    }

    private Double calc_product_price(Product product, int quantity) {
        //TODO :discount policy
        return product.getPrice() * quantity;
    }


    public void add_review(int product_id, String user_email, String review) {
        Product p = this.getProduct_by_product_id(product_id); //throws
        p.add_review(user_email, review);
    }




    public void add_owner(String user_email, String user_email_to_appoint) {
        if(!this.stuff_emails_and_appointments.containsKey(user_email))
        {
            throw new IllegalArgumentException("User is not stuff member of this store");
        }
        if (!this.stuff_emails_and_appointments.get(user_email).is_owner())
        {
            throw new IllegalArgumentException("User is not owner of this store");
        }
        if (this.stuff_emails_and_appointments.get(user_email_to_appoint).is_owner())
        {
            throw new IllegalArgumentException("User is already owner/founder");

        }
        Appointment appointment = new Appointment(user_email, user_email_to_appoint, this.store_id, StoreManagerType.store_owner);
        this.stuff_emails_and_appointments.put(user_email, appointment);
    }

    public void remove_owner(String user_email, String user_email_to_delete_appointment) {
        if (this.get_number_of_owners() <= 1)
        {
            throw new IllegalArgumentException("Can not removed this owner - store must have at least one owner");
        }
        if(!this.stuff_emails_and_appointments.containsKey(user_email))
        {
            throw new IllegalArgumentException("User is not stuff member of this store");
        }
        if (!this.stuff_emails_and_appointments.get(user_email).is_owner())
        {
            throw new IllegalArgumentException("User is not owner of this store");
        }
        if (!this.stuff_emails_and_appointments.containsKey(user_email_to_delete_appointment))
        {
            throw new IllegalArgumentException("User to be removed is not stuff member of this store");
        }
        if (!this.stuff_emails_and_appointments.get(user_email_to_delete_appointment).is_owner())
        {
            throw new IllegalArgumentException("User to be removed is not owner/founder");
        }
        Appointment appointment = this.stuff_emails_and_appointments.get(user_email_to_delete_appointment);
        if (!appointment.getAppointer_email().equals(user_email))
        {
            throw new IllegalArgumentException("User can not remove stuff member that is not appoint by him");
        }
    }

    private int get_number_of_owners()
    {
        int num = 0;
        for (Appointment appointment:this.stuff_emails_and_appointments.values())
        {
            if (appointment.is_owner()) {
                num++;
            }
        }
        return num;
    }
























//---------------------------------------------------------------------- Getters - Start ------------------------------------------------------------------------------------

    public LocalDate getFoundation_date() {
        return foundation_date;
    }

    public DiscountPolicy getDiscount_policy() {
        return discountPolicy;
    }





    public HashMap<Product, Integer> getInventory() {
        return this.inventory;
    }

    public StorePurchaseHistory getPurchase_history() {
        return purchases_history;
    }

    public String getFounder_email() {
        return founder_email;
    }

    public int getStore_id() {
        return store_id;
    }

    public PurchasePolicy getPurchase_policy() {
        return purchasePolicy;
    }

    public String getName() {
        return name;
    }

//---------------------------------------------------------------------- Getters - End ------------------------------------------------------------------------------------


//---------------------------------------------------------------------- Setters - Start ------------------------------------------------------------------------------------

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setFounder_email(String founder_email) {
        this.founder_email = founder_email;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setFoundation_date(LocalDate foundation_date) {
        this.foundation_date = foundation_date;
    }

    public void setInventory(HashMap<Product, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }




//---------------------------------------------------------------------- Setters - End ------------------------------------------------------------------------------------


}


