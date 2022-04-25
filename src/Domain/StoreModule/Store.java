package Domain.StoreModule;

import Domain.Utils.Utils;

import java.time.LocalDate;
import java.util.*;


public class Store {
    // -- fields
    private int store_id;
    private int founder_id;
    private HashMap<Integer, Appointment> stuff_ids_and_appointments;
    private String name;
    private LocalDate foundation_date;
    private HashMap<Product, Integer> inventory; // product & quantity
    private int product_ids;
    private boolean active;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private HashMap<Integer, Question> users_questions; // question_id x question
    private StorePurchaseHistory purchases_history;

    // -- constructor

    public Store(int store_id, int founder_id, String name) {
        this.store_id = store_id;
        this.founder_id = founder_id;
        this.name = name;
        this.product_ids = 1;
        this.active = true;
        this.foundation_date = LocalDate.now();
        this.users_questions = new HashMap<>();
    }

    // -- methods
    public boolean close_store_temporarily(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.close_store_temporarily);
        if (!this.is_active())
            return false;
        this.active = false;
        // TODO: 22/04/2022 : send message to all of the managers & owners.
        return true;
    }
    public boolean open_close_store(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.open_close_store);
        if (this.is_active())
            return false;
        this.active = true;
        // TODO: 22/04/2022 : send message to all of the managers & owners.
        return true;
    }
    public StoreManagersInfo view_store_management_information(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.view_permissions);
        return new StoreManagersInfo(this.stuff_ids_and_appointments);
    }
    public void set_permissions(int user_id, int manager_id, LinkedList<StorePermission> permissions) {
        // check that the manager appointed by the user
        if (this.get_appointer(manager_id) != user_id)
            throw new IllegalArgumentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager_id == user_id)
            throw new IllegalArgumentException("User cant change himself permissions");

        Appointment manager_permission = this.stuff_ids_and_appointments.get(manager_id);
        manager_permission.set_permissions(permissions);

    }
    public boolean is_active() {
        return this.active;
    }
    public int get_appointer(int manager_id) {
        return this.stuff_ids_and_appointments.get(manager_id).getAppointer_id();
    }


    public HashMap<Integer, Question> view_store_questions(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.view_users_questions);
        return this.users_questions;
    }

    public void answer_question(int store_id, int user_id, int question_id, String answer) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.view_users_questions);
        Question question = this.users_questions.get(question_id);
        question.setAnswer(answer);
    }

    public String view_store_purchases_history(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.view_purchases_history);
        return this.purchases_history.toString();
    }

    public boolean close_store_permanently(int user_id) {
        if (!this.is_active())
            return false;
        this.active = false;
        // TODO: 22/04/2022 : send message to all of the managers & owners.
        this.founder_id = -1;
        this.stuff_ids_and_appointments = null;
        return true;
    }

    public void check_permission(int user_id, StorePermission permission) throws IllegalAccessException {
        // not just managers - FIX
        if(!(this.stuff_ids_and_appointments.get(user_id).has_permission(permission)))
            throw new IllegalAccessException("User has no permissions!");
    }





// @TODO : we changed a lot of fields, have to match the method
    public String toString() {
        String founder_name = "----------------------";
        StringBuilder info = new StringBuilder();
        info.append("Store info: "+this.name+"\n");
        info.append("\tStore founder: "+ founder_name +"\n");
        info.append("\tStore owners: ");
        for (Integer id : stuff_ids_and_appointments.keySet())
        {
            String name = "";
            info.append(name+", ");
        }
        info.append("\n");
        info.append("\tStore managers: ");
        for (Integer id : stuff_ids_and_appointments.keySet())
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

    public void add_product(int user_id, String name, double price, String category, List<String> key_words, int quantity) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.add_item);
        int product_id = this.getInc_product_id();
        Product product = new Product(name, this.store_id, product_id, price, category, key_words);
        inventory.put(product, quantity);
    }

    public void delete_product(int product_id, int user_id) throws IllegalAccessException {
        Product product_to_remove = this.getProduct_by_product_id(product_id);
        this.check_permission(user_id, StorePermission.remove_item);
        inventory.remove(product_to_remove);
    }

    //------------------------------------------------ edit product - Start ----------------------------------------------

    public void edit_product_name(int user_id, int product_id, String name) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_id, StorePermission.edit_item_name);
        to_edit.setName(name);
    }

    public void edit_product_price(int user_id, int product_id, double price) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_id, StorePermission.edit_item_price);
        to_edit.setPrice(price);
    }

    public void edit_product_category(int user_id, int product_id, String category) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_id, StorePermission.edit_item_category);
        to_edit.setCategory(category);
    }

    public void edit_product_key_words(int user_id, int product_id, List<String> key_words) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_id, StorePermission.edit_item_keywords);
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


    public void add_review(int product_id, int user_id, String review) {
        this.getProduct_by_product_id(product_id).add_review(user_id, review);
    }



    private int getInc_product_id() {
        return this.product_ids++;
    }






















//---------------------------------------------------------------------- Getters - Start ------------------------------------------------------------------------------------

    public LocalDate getFoundation_date() {
        return foundation_date;
    }

    public DiscountPolicy getDiscount_policy() {
        return discountPolicy;
    }

    public HashMap<Integer, Appointment> getManager_ids() {
        return stuff_ids_and_appointments;
    }

    public HashMap<Integer, Appointment> getstuff_ids_and_appointments() {
        return stuff_ids_and_appointments;
    }

    public HashMap<Product, Integer> getInventory() {
        return this.inventory;
    }

    public StorePurchaseHistory getPurchase_history() {
        return purchases_history;
    }

    public int getFounder_id() {
        return founder_id;
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

    public void setFounder_id(int founder_id) {
        this.founder_id = founder_id;
    }

    public void setstuff_ids_and_appointments(HashMap<Integer, Appointment> stuff_ids_and_appointments) {
        this.stuff_ids_and_appointments = stuff_ids_and_appointments;
    }

    public void setManager_ids(HashMap<Integer, Appointment> manager_ids) {
        this.stuff_ids_and_appointments = manager_ids;
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


