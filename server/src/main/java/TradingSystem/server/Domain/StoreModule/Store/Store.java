package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.*;
import TradingSystem.server.Domain.StoreModule.Bid.Bid;
import TradingSystem.server.Domain.StoreModule.Bid.BidInformation;
import TradingSystem.server.Domain.StoreModule.Bid.BidStatus;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountPolicy;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent.OrCompositePredict;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent.xorDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent.andCompsoitePredict;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.maxDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.plusDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByCategory;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByProduct;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByStore;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.*;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.Utils.Observable;
import TradingSystem.server.Domain.Utils.Utils;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Store implements Observable {


    // -- fields
    @Id
    //generated
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int store_id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AssignUser founder;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "stuff_and_appointments",
            joinColumns = {@JoinColumn(name = "store_id", referencedColumnName = "store_id")},
            inverseJoinColumns = {@JoinColumn(name = "appointment", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "email")
    private Map<AssignUser, Appointment> stuffs_and_appointments;
    private String name;
    public String foundation_date;


    @ElementCollection
    @Column(name = "quantity")
    @MapKeyClass(value = Product.class)
//    @MapKeyColumn(name = "product_id") // the key column
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, Integer> inventory; // product & quantity
    private boolean active;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DiscountPolicy discountPolicy;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private PurchasePolicy purchasePolicy;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private StorePurchaseHistory purchases_history;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private StoreReview storeReview;
    private AtomicInteger product_ids_counter;
    @Transient
    private Object owners_lock;
    @Transient
    private Object managers_lock;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "predicts",
            joinColumns = {@JoinColumn(name = "predict_id", referencedColumnName = "store_id")})
    @MapKeyColumn(name = "name") // the key column
    private Map<String, Ipredict> predictList;

    @Transient
    private HashMap<Integer, Bid> bids;

    // -- constructors
    public Store(int store_id, String name, AssignUser founder, AtomicInteger ai) {
        this.discountPolicy = new DiscountPolicy();
        this.purchasePolicy = new PurchasePolicy();
        this.store_id = store_id;
        this.founder = founder;
        this.name = name;
        this.product_ids_counter = ai;
        this.active = true;
        this.foundation_date = LocalDate.now().toString();
        this.storeReview = new StoreReview();
        this.purchases_history = new StorePurchaseHistory(this.name);
        this.inventory = new ConcurrentHashMap<>();
        this.stuffs_and_appointments = new ConcurrentHashMap<>();
        this.owners_lock = new Object();
        this.managers_lock = new Object();
        this.predictList = new HashMap<>();
        this.bids = new HashMap<>();

        HibernateUtils.persist(this);
    }

    public Store() {
        this.owners_lock = new Object();
        this.managers_lock = new Object();
    }

    // ------------------------------ getters ------------------------------

    public Map<AssignUser, Appointment> getStuffs_and_appointments() {
        return stuffs_and_appointments;
    }

    public boolean isActive() {
        return active;
    }

    public StorePurchaseHistory getPurchases_history() {
        return purchases_history;
    }

    public AtomicInteger getProduct_ids_counter() {
        return product_ids_counter;
    }

    public Object getOwners_lock() {
        return owners_lock;
    }

    public Object getManagers_lock() {
        return managers_lock;
    }

    // ------------------------------ setters ------------------------------

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public void setFounder(AssignUser founder) {
        this.founder = founder;
    }

    public void setStuffs_and_appointments(Map<AssignUser, Appointment> stuffs_and_appointments) {
        this.stuffs_and_appointments = stuffs_and_appointments;
    }

    public void setFoundation_date(String foundation_date) {
        this.foundation_date = foundation_date;
    }

    public void setInventory(Map<Product, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setPurchases_history(StorePurchaseHistory purchases_history) {
        this.purchases_history = purchases_history;
    }

    public void setStoreReview(StoreReview storeReview) {
        this.storeReview = storeReview;
    }

    public void setProduct_ids_counter(AtomicInteger product_ids_counter) {
        this.product_ids_counter = product_ids_counter;
    }

    public void setOwners_lock(Object owners_lock) {
        this.owners_lock = owners_lock;
    }

    public void setManagers_lock(Object managers_lock) {
        this.managers_lock = managers_lock;
    }


    // -- public methods

    public AssignUser getFounder() {
        return founder;
    }

    public int get_store_rating() {
        return this.storeReview.getAvgRating();
    }

    public void add_product_review(int product_id, String user_email, String review) throws ObjectDoesntExsitException {
        Product p = this.getProduct_by_product_id(product_id); //throws
        p.add_review(user_email, review);
    }

    public AndPurchaseRule add_and_purchase_rule(String nameOfRule, String left, String right) throws WrongPermterException {
        PurchaseRule purchaseright = purchasePolicy.getPolicy(left);
        PurchaseRule purchaseleft = purchasePolicy.getPolicy(right);
        if (purchaseleft == purchaseleft)
            throw new WrongPermterException("the polices are the same");
        AndPurchaseRule and = new AndPurchaseRule(purchaseleft, purchaseright);
        purchasePolicy.addRule(nameOfRule, and);
        purchasePolicy.removeRule(left);
        purchasePolicy.removeRule(right);
        return and;
    }

    public OrPurchaseRule add_or_purchase_rule(String name, String left, String right) throws WrongPermterException {
        PurchaseRule purchaseRight = purchasePolicy.getPolicy(left);
        PurchaseRule purchaseLeft = purchasePolicy.getPolicy(right);
        if (purchaseLeft == purchaseRight)
            throw new WrongPermterException("the polices are the same");
        OrPurchaseRule or = new OrPurchaseRule(purchaseLeft, purchaseRight);
        purchasePolicy.removeRule(left);
        purchasePolicy.removeRule(right);
        purchasePolicy.addRule(name, or);
        return or;
    }

    public void add_store_rating(AssignUser user, int rating) throws MarketException {
        if (this.stuffs_and_appointments.containsKey(user))
            throw new NoPremssionException("store members can't rate their store");
        this.storeReview.add_rating(user.get_user_email(), rating);
    }

    public Predict addPredict(String catgorey, int product_id, boolean above, boolean equql,
                              int num, boolean price, boolean quantity, boolean age, boolean time, int year,
                              int month, int day, String name) throws WrongPermterException, ObjectDoesntExsitException {
        Product product;
        if (product_id != -1)
            product = getProduct_by_product_id(product_id);
        else
            product = null;
        Predict predict = new Predict(catgorey, product, above, equql, num, price, quantity, age, time, year, month, day);
        checkUniqName(name, this.predictList);
        predictList.put(name, predict);
        return predict;
    }


    public String remove_predict(String name) throws WrongPermterException {
        if (predictList.get(name) != null)
            predictList.remove(name);
        else throw new WrongPermterException("no predict with this name");
        return "predict " + name + "removed" + "from store";
    }

    //start of discount policy
    public String remove_discount_rule(String name) throws WrongPermterException {
        discountPolicy.removeRule(name);
        return "the rule was removed";
    }

    public String remove_purchase_rule(String name) throws WrongPermterException {
        purchasePolicy.removeRule(name);
        return "the rule was removed";
    }

    private void checkUniqName(String name, Map map) throws WrongPermterException {
        if (map.keySet().contains(name))
            throw new WrongPermterException("there is a predict with this name in the store,please choose another name");
    }


    public List<String> getDiscountPolicyNames() {
        List<String> list = new LinkedList<>();
        for (String s : discountPolicy.getPolicyNames())
            list.add(s);
        return list;
    }

    public List<String> getPurchasePolicyNames() {
        List<String> list = new LinkedList<>();
        for (String s : purchasePolicy.getPolicyNames())
            list.add(s);
        return list;

    }

    public List<String> getPredicts() {
        List<String> list = new LinkedList<>();
        for (String s : predictList.keySet())
            list.add(s);
        return list;

    }

    public List<String> getSimplePredicts() {
        List<String> list = new LinkedList<>();
        for (String s : predictList.keySet())
            if (predictList.get(s) instanceof Predict)
                list.add(s);
        return list;
    }


    public andCompsoitePredict CreateAndDisocuntCompnent(String name, String left, String right) throws WrongPermterException {
        Ipredict leftPredict = predictList.get(left);
        Ipredict rightPredict = predictList.get(right);
        andCompsoitePredict toreturn = new andCompsoitePredict(leftPredict, rightPredict);
        checkUniqName(name, predictList);
        this.predictList.put(name, toreturn);
        return toreturn;
    }

    public OrCompositePredict CreateOrDisocuntCompnent(String name, String left, String right) throws WrongPermterException {
        Ipredict leftPredict = predictList.get(left);
        Ipredict rightPredict = predictList.get(right);
        OrCompositePredict toreturn = new OrCompositePredict(leftPredict, rightPredict);
        checkUniqName(name, predictList);
        this.predictList.put(name, toreturn);
        return toreturn;
    }

    public xorDiscountComponent CreateXorDisocuntCompnent(String name, String left, String right) throws WrongPermterException {
        DiscountComponent leftdiscount = discountPolicy.getDiscountCompnentByName(left);
        DiscountComponent rifhtdiscount = discountPolicy.getDiscountCompnentByName(right);
        xorDiscountComponent toreturn = new xorDiscountComponent(leftdiscount, rifhtdiscount);
        if (leftdiscount == rifhtdiscount)
            throw new WrongPermterException("the discounts are the same");
        discountPolicy.removeRule(left);
        discountPolicy.removeRule(right);
        discountPolicy.addRule(name, toreturn);
        return toreturn;
    }

    public maxDiscountComponent CreateMaxDisocuntCompnent(String name, String left, String right) throws WrongPermterException {
        DiscountComponent leftdiscount = discountPolicy.getDiscountCompnentByName(left);
        DiscountComponent rifhtdiscount = discountPolicy.getDiscountCompnentByName(right);
        maxDiscountComponent toreturn = new maxDiscountComponent(leftdiscount, rifhtdiscount);
        if (leftdiscount == rifhtdiscount)
            throw new WrongPermterException("the discounts are the same");
        discountPolicy.removeRule(left);
        discountPolicy.removeRule(right);
        discountPolicy.addRule(name, toreturn);
        return toreturn;
    }

    public plusDiscountComponent CreateplusDisocuntCompnent(String name, String left, String right) throws WrongPermterException {
        DiscountComponent leftdiscount = discountPolicy.getDiscountCompnentByName(left);
        DiscountComponent rifhtdiscount = discountPolicy.getDiscountCompnentByName(right);
        plusDiscountComponent toreturn = new plusDiscountComponent(leftdiscount, rifhtdiscount);
        if (leftdiscount == rifhtdiscount)
            throw new WrongPermterException("the discounts are the same");
        discountPolicy.removeRule(left);
        discountPolicy.removeRule(right);
        discountPolicy.addRule(name, toreturn);
        return toreturn;
    }


    public simpleDiscountComponent add_simple_product_discount(String name, int id, double precent) throws WrongPermterException, ObjectDoesntExsitException {
        simpleDiscountComponent simpleDiscountComponent;
        Product p = getProduct_by_product_id(id);
        simpleDiscountComponent = new simpleDiscountComponentByProduct(p, precent);
        this.discountPolicy.addRule(name, simpleDiscountComponent);
        return simpleDiscountComponent;
    }


    public simpleDiscountComponent add_simple_discount(String name, String type, double precent, String nameOfCategorey) throws WrongPermterException {
        simpleDiscountComponent simpleDiscountComponent;
        if (type == "c")
            simpleDiscountComponent = new simpleDiscountComponentByCategory(nameOfCategorey, precent);
        else
            simpleDiscountComponent = new simpleDiscountComponentByStore(precent);
        this.discountPolicy.addRule(name, simpleDiscountComponent);
        return simpleDiscountComponent;
    }

    public ComplexDiscountComponent add_complex_discount(String name, String nameOFPredict, String nameOfPolicy) throws WrongPermterException {
        Ipredict predict = getPredictByName(nameOFPredict);
        DiscountComponent simpleDiscountComponent = discountPolicy.getDiscountCompnentByName(nameOfPolicy);
        if (!(simpleDiscountComponent instanceof simpleDiscountComponent))
            throw new WrongPermterException("this discount is not of type simple");
        ComplexDiscountComponent toreturn = new ComplexDiscountComponent(simpleDiscountComponent, predict);
        discountPolicy.removeRule(nameOfPolicy);
        this.discountPolicy.addRule(name, toreturn);
        return toreturn;

    }


    //end of discount policy

    //purchase policy

    private Predict getPredictByName(String name) throws WrongPermterException {
        Ipredict p = predictList.get(name);
        if (p == null)
            throw new WrongPermterException("no predict with this name");
        return (Predict) p;
    }

    public SimplePurchaseRule addsimplePorchaseRule(String nameOfrule, String NameOfPredict) throws WrongPermterException {
        Predict p = getPredictByName(NameOfPredict);
        SimplePurchaseRule Toreturn = new SimplePurchaseRule(p);
        this.purchasePolicy.addRule(nameOfrule, Toreturn);
        return Toreturn;
    }


    public void add_product_rating(String user_email, int product_id, int rate) throws MarketException {
        Product p = this.getProduct_by_product_id(product_id);//throws
        p.add_rating(user_email, rate);
    }

    public Appointment appoint_founder() throws MarketException {
        Appointment appointment = new Appointment(this.founder, this.founder, this, StoreManagerType.store_founder);
        this.stuffs_and_appointments.put(founder, appointment);
        this.founder.add_founder(this, appointment);
        return appointment;
    }

    public void close_store_permanently() throws MarketException {
        this.active = false;
        String message = "Store was closed permanently at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message, "");
        for (AssignUser user : stuffs_and_appointments.keySet()) {
            user.remove_appointment(this);
        }
        this.stuffs_and_appointments = null;
    }


    public void close_store_temporarily(AssignUser user) throws MarketException {
        this.check_permission(user, StorePermission.close_store_temporarily);
        this.active = false;
        String email = user.get_user_email();
        String message = "Store was closed close_store_temporarily at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message, email);
    }


    public void open_close_store(AssignUser user) throws MarketException {
        this.check_permission(user, StorePermission.open_close_store);
        if (this.is_active())
            throw new StoreMethodException("The store is already open");
        this.active = true;
        String email = user.get_user_email();
        String message = "Store was re-open by : " + email + " at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message, email);
    }

    public StoreManagersInfo view_store_management_information(AssignUser user) throws MarketException {
        this.check_permission(user, StorePermission.view_permissions);
        //TODO: added parse to emails - appointment map
        List<AppointmentInformation> answer = new LinkedList<>();
        for (Appointment appointment : stuffs_and_appointments.values()) {
            AppointmentInformation temp = new AppointmentInformation(appointment.getMember().get_user_email(), appointment.getAppointer().get_user_email(),
                    appointment.getType().toString());
            answer.add(temp);
        }
        //end
        return new StoreManagersInfo(this.name, answer);
    }

    public boolean is_active() {
        return this.active;
    }


    public void set_permissions(AssignUser user_who_set_permission, AssignUser manager, List<StorePermission> permissions) throws MarketException {
        // check that the manager appointed by the user
        this.check_permission(user_who_set_permission, StorePermission.edit_permissions);
        if (!this.get_appointer(manager).equals(user_who_set_permission))
            throw new AppointmentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager.equals(user_who_set_permission))
            throw new NoPremssionException("User cant change himself permissions");

        Appointment manager_permission = this.stuffs_and_appointments.get(manager);
        manager_permission.set_permissions(permissions);

    }

    public List<String> view_store_questions(AssignUser user) throws MarketException {
        this.check_permission(user, StorePermission.view_users_questions);
        return QuestionController.getInstance().view_buyers_to_store_questions(store_id);
    }

    public void add_question(AssignUser sender, String question_message) {
        QuestionController.getInstance().add_buyer_question(question_message, sender, store_id);
        this.send_message_to_the_store_stuff("new user question from :" + sender.get_user_email() + " in store " + name, sender.get_user_email());
    }

    public void answer_question(AssignUser user, int question_id, String answer) throws MarketException {
        this.check_permission(user, StorePermission.view_users_questions);
        QuestionController.getInstance().answer_buyer_question(question_id, answer);
    }

    public StorePurchaseHistory view_store_purchases_history(AssignUser user) throws MarketException {
        this.check_permission(user, StorePermission.view_purchases_history);
        return this.purchases_history;
    }

    public StorePurchaseHistory admin_view_store_purchases_history() {
        return this.purchases_history;
    }

    // -- find product by ----------------------------------------------------------------------------------

    public List<Product> find_products_by_name(String product_name) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getName().equals(product_name)) {
                products.add(p);
            }
        }
        return products;

    }

    public List<Product> find_products_by_category(String category) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getCategory().equals(category)) {
                products.add(p);
            }
        }
        return products;
    }

    public List<Product> find_products_by_key_words(String key_words) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getKey_words().contains(key_words)) {
                products.add(p);
            }
        }
        return products;
    }
    // -----------------------------------------------------------------------------------------------------


    public Map<Product, Integer> add_product(AssignUser user, String name, double price, String category, List<String> key_words, int quantity) throws MarketException {
        this.check_permission(user, StorePermission.add_item);
        if (price <= 0)
            throw new ProductAddingException("price must be more then zero");
        if (quantity < 1)
            throw new ProductAddingException("quantity must be more then zero");
        Utils.nameValidCheck(name);
        Utils.nameValidCheck(category);
        for (Product p : inventory.keySet()) {
            if (p.getName().equals(name))
                throw new ProductAddingException("product already exists in the store");
        }
        int product_id = this.product_ids_counter.getAndIncrement();
        Product product = new Product(name, product_id, price, category, key_words, store_id);
        inventory.put(product, quantity);
        return inventory;
    }

    public Map<Product, Integer> delete_product(int product_id, AssignUser user) throws MarketException {
        Product product_to_remove = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.remove_item);
        inventory.remove(product_to_remove);
        // remove all bids related to product
        for (Map.Entry<Integer, Bid> bid : bids.entrySet()) {
            // TODO: remove bid from database
            if (bid.getValue().getProduct().getProduct_id() == product_id) {
                bids.remove(bid.getKey());
                HibernateUtils.remove(bid.getValue());
            }
        }
//        // remove all predicts related to product
//        for (Map.Entry<String, Ipredict> entry : predictList.entrySet()) {
//            // TODO: remove predict from database
//            if (entry.getValue().getProduct().getProduct_id() == product_id)
//                predictList.remove(entry.getKey());
//        }
        return inventory;
    }

    // -- edit product - Start ----------------------------------------------------------------------------------

    public void edit_product_name(AssignUser user, int product_id, String name) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_name);
        to_edit.setName(name);
    }

    public void edit_product_price(AssignUser user, int product_id, double price) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_price);
        to_edit.setOriginal_price(price);
    }

    public void edit_product_category(AssignUser user, int product_id, String category) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_category);
        to_edit.setCategory(category);
    }

    public void edit_product_key_words(AssignUser user, int product_id, List<String> key_words) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_keywords);
        to_edit.setKey_words(key_words);
    }

    // -----------------------------------------------------------------------------------------------------


    public synchronized double check_available_products_and_calc_price(int user_age, Basket basket) throws MarketException {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        purchasePolicy.checkPolicy(user_age, basket);
        for (Product p : products_and_quantities.keySet()) {
            this.checkAvailablityAndGet(p.getProduct_id(), products_and_quantities.get(p));
        }
        double discount = discountPolicy.calculateDiscount(basket);
        return basket.getTotal_price() - discount;
    }


    //TODO: policies
    // check product is available - throws if no.
    public synchronized Product checkAvailablityAndGet(int product_id, int quantity) throws MarketException {
        Product p = this.getProduct_by_product_id(product_id);
        if (p == null) {
            throw new ProductAddingException("checkAvailablityAndGet: Product is not exist");
            //not suppose to happen
            //add to logger
        }
        int product_quantity = this.inventory.get(p);
        if (quantity <= product_quantity) {
            return p;
        }
        throw new ProductAddingException("Store.checkAvailablityAndGet: Product is not available");
    }

    /**
     * @param basket      we call this method with all the basket of a single cart
     * @param purchase_id the index from store controller
     * @return
     */
    public synchronized Purchase remove_basket_products_from_store(Basket basket, int purchase_id) throws MarketException {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();

        for (Product p : products_and_quantities.keySet()) {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove < 0)
                throw new StoreMethodException("Store.remove_basket_products_from_store: product quantity :" + quantity_to_remove + "" +
                        " is more then available for product id :" + p.getProduct_id());
        }
        for (Product p : products_and_quantities.keySet()) {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove == 0)
                this.inventory.remove(p);
            else
                this.inventory.put(p, first_quantity - quantity_to_remove);
        }
        String buyer_email = basket.get_buyer_email();
        Map<Integer, Integer> p_ids_quantity = basket.get_productsIds_and_quantity();
        Map<Integer, Double> p_ids_price = this.get_product_ids_and_total_price(basket);
        Map<Integer, String> p_ids_name = basket.getProducts_and_names();

        Purchase purchase = new Purchase(p_ids_quantity, p_ids_price, p_ids_name);
        HibernateUtils.persist(purchase);
        StorePurchase purchase_to_add = new StorePurchase(purchase, buyer_email, purchase_id);
        this.purchases_history.insert(purchase_to_add);
        this.send_message_to_the_store_stuff("new purchase, with id : " + purchase_id, buyer_email);
        return purchase;
    }

    public void add_owner(AssignUser appointer, AssignUser new_owner) throws MarketException {
        this.check_permission(appointer, StorePermission.add_owner);
        synchronized (owners_lock) {
            Appointment appointment = this.stuffs_and_appointments.get(new_owner);
            if (appointment != null) {
                throw new AppointmentException("User to appoint is already store member");
            }

            Appointment appointment_to_add = new Appointment(new_owner, appointer, this, StoreManagerType.store_owner);
            this.stuffs_and_appointments.put(new_owner, appointment_to_add);
            new_owner.add_owner(this, appointment_to_add);
            this.set_manager_in_bids(0, new_owner.get_user_email());
            this.send_message_to_the_store_stuff(new_owner.get_user_email()+" is a new owner in the store", appointer.get_user_email());
        }
    }

    public void add_manager(AssignUser appointer, AssignUser new_manager) throws MarketException {
        this.check_permission(appointer, StorePermission.add_manager);
        synchronized (managers_lock) {
            Appointment appointment = this.stuffs_and_appointments.get(new_manager);
            if (appointment != null)
                throw new AppointmentException("User to appoint is already store member");
            Appointment appointment_to_add = new Appointment(new_manager, appointer, this, StoreManagerType.store_manager);
            this.stuffs_and_appointments.put(new_manager, appointment_to_add);
            new_manager.add_manager(this, appointment_to_add);
            this.set_manager_in_bids(0, new_manager.get_user_email());
            this.send_message_to_the_store_stuff(new_manager.get_user_email()+" is a new manager in the store", appointer.get_user_email());
        }
    }


    public void remove_manager(AssignUser remover, AssignUser user_to_delete_appointment) throws MarketException {
        this.check_permission(remover, StorePermission.add_manager);
        synchronized (managers_lock) {
            Appointment appointment = this.stuffs_and_appointments.get(user_to_delete_appointment);
            if (appointment == null) {
                throw new AppointmentException("User to be removed is not stuff member of this store");
            }
            if (!appointment.is_manager()) {
                throw new AppointmentException("User to be removed is not owner/founder");
            }
            if (!appointment.getAppointer().equals(remover)) {
                throw new AppointmentException("User can not remove stuff member that is not appoint by him");
            }
            this.stuffs_and_appointments.remove(user_to_delete_appointment);
            user_to_delete_appointment.remove_appointment(this);
            this.set_manager_in_bids(1, user_to_delete_appointment.get_user_email());
            this.send_message_to_the_store_stuff(user_to_delete_appointment.get_user_email()+" is removing from manage the store", remover.get_user_email());
            HibernateUtils.remove(appointment);
        }
    }

    private void remove_all_appointments_by_user(AssignUser user_to_delete_appointment) throws MarketException {
        for (Appointment appointment1 : this.stuffs_and_appointments.values()) {
            if (appointment1.getAppointer().equals(user_to_delete_appointment)) {
                if (appointment1.is_owner())
                    this.remove_owner(user_to_delete_appointment, appointment1.getMember());
                else if (appointment1.is_manager())
                    this.remove_manager(user_to_delete_appointment, appointment1.getMember());
            }
        }
    }

    public void remove_owner(AssignUser remover, AssignUser user_to_delete_appointment) throws MarketException {
        this.check_permission(remover, StorePermission.add_manager);
        synchronized (owners_lock) {
            Appointment appointment = this.stuffs_and_appointments.get(user_to_delete_appointment);
            if (appointment == null) {
                throw new AppointmentException("User to be removed is not stuff member of this store");
            }

            if (!appointment.is_owner()) {
                throw new AppointmentException("User to be removed is not owner");
            }

            if (!appointment.getAppointer().equals(remover)) {
                throw new AppointmentException("User can not remove stuff member that is not appoint by him");
            }

            remove_all_appointments_by_user(user_to_delete_appointment);
            this.stuffs_and_appointments.remove(user_to_delete_appointment);
            user_to_delete_appointment.remove_appointment(this);
            this.set_manager_in_bids(1, user_to_delete_appointment.get_user_email());
            this.send_message_to_the_store_stuff(user_to_delete_appointment.get_user_email()+" is removing from owns the store", remover.get_user_email());
            HibernateUtils.remove(appointment);
        }
    }


    public Product getProduct_by_product_id(int product_id) throws ObjectDoesntExsitException {
        for (Product product : this.inventory.keySet()) {
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new ObjectDoesntExsitException("Store: Product is not exist - product id: " + product_id);
    }


    // -- Private Methods

    // -- Getters ------------------------------------------------------------------------

    public String getFoundation_date() {
        return foundation_date;
    }

    public StoreReview getStoreReview() {
        return storeReview;
    }

    public Map<Product, Integer> getInventory() {
        return this.inventory;
    }

    public StorePurchaseHistory getPurchase_history() {
        return purchases_history;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getName() {
        return name;
    }


//---------------------------------------------------------------------- Setters - Start ------------------------------------------------------------------------------------


    public void setName(String name) {
        this.name = name;
    }

    public void setInventory(HashMap<Product, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPurchasePolicy(AssignUser user, PurchasePolicy purchasePolicy) throws NoPremssionException {
        check_permission(user, StorePermission.edit_purchase_policy);
        this.purchasePolicy = purchasePolicy;
    }

    public void setDiscountPolicy(AssignUser user, DiscountPolicy discountPolicy) throws NoPremssionException {
        check_permission(user, StorePermission.edit_discount_policy);
        this.discountPolicy = discountPolicy;
    }


    public void check_permission(AssignUser user, StorePermission permission) throws NoPremssionException {
        if (!this.stuffs_and_appointments.containsKey(user))
            throw new NoPremssionException("user is no a store member");
        boolean flag = this.stuffs_and_appointments.get(user).has_permission(permission);
        if (!flag)
            throw new NoPremssionException("User has no permissions!");
    }

    public Map<Integer, Double> get_product_ids_and_total_price(Basket basket) {
        Map<Integer, Double> productsIds_and_totalPrice = new HashMap<>();
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for (Product p : products_and_quantities.keySet()) {
            int quantity = products_and_quantities.get(p);
            productsIds_and_totalPrice.put(p.getProduct_id(), this.calc_product_price(p, quantity));
        }
        return productsIds_and_totalPrice;
    }

    public AssignUser get_appointer(AssignUser manager) {
        Appointment appointment = this.stuffs_and_appointments.get(manager);
        if (appointment == null)
            throw new IllegalArgumentException("is not a manager");
        return this.stuffs_and_appointments.get(manager).getAppointer();
    }

    //TODO: pass
    public Double calc_product_price(Product product, int quantity) {
        //TODO :discount policy - version 2
        return product.getOriginal_price() * quantity;
    }

    public void send_message_to_the_store_stuff(String message, String sender_email) {
        for (AssignUser stuff_member : this.stuffs_and_appointments.keySet()) {
            if (!stuff_member.get_user_email().equals(sender_email))
                stuff_member.add_notification(message);
        }
    }

    //TODO: testing method
    public boolean has_appointment(AssignUser founder) {
        return stuffs_and_appointments.containsKey(founder);
    }

    public void edit_product_quantity(AssignUser assignUser, int product_id, int quantity) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(assignUser, StorePermission.edit_item_quantity);
        if (quantity < 1) {
            throw new WrongPermterException("quantity must be positive number");
        }
        this.inventory.put(to_edit, quantity);
    }


    // amit - bid

    public int add_bid_offer(int bid_id, Product product, int quantity, double offer_price, User buyer) {
        List<String> managers_emails = new ArrayList<>();
        for (Appointment appointment : this.stuffs_and_appointments.values()) {
            if (appointment.has_permission(StorePermission.answer_bid_offer)) {
                managers_emails.add(appointment.getMember().get_user_email());
            }
        }
        Bid bid = new Bid(bid_id, quantity, offer_price, managers_emails, product, buyer);
        HibernateUtils.persist(bid);
        this.bids.put(bid_id, bid);
        this.send_message_to_the_store_stuff("new bid offer for product :" + product.getName(), "");
        return bid_id;
    }

    public List<BidInformation> view_bids_status(AssignUser user) throws NoPremssionException {
        this.check_permission(user, StorePermission.view_bids_status);
        List<BidInformation> answer = new ArrayList<>();

        for (Map.Entry<Integer, Bid> entry : this.bids.entrySet()){
            BidInformation temp = entry.getValue().get_bid_information(entry.getKey());
            answer.add(temp);
        }
        return answer;
    }

    public void add_bid_answer(User user, boolean manager_answer, int bidID,
                               double negotiation_price) throws Exception {
        AssignUser assignUser = user.state_if_assigned();
        if (negotiation_price == -1) {
            this.check_permission(assignUser, StorePermission.answer_bid_offer);

        } else {
            this.check_permission(assignUser, StorePermission.answer_bid_offer_negotiate);
            if (!manager_answer)
                throw new Exception("illegal combination - negative answer with negotiation offer");
            if (negotiation_price < 0)
                throw new Exception("illegal price");

        }
        Bid bid = this.bids.get(bidID);
        bid.add_manager_answer(assignUser.get_user_email(), manager_answer, negotiation_price);

        User buyer = bid.getBuyer();
        if (bid.get_status() == BidStatus.closed_confirm) {
            buyer.add_notification("Your bid is confirm by the store managers.");
            Product product = bid.getProduct();
            buyer.add_product_to_cart_from_bid_offer(this, product, bid.getQuantity(), bid.get_offer_price());
        }

        if (bid.get_status() == BidStatus.negotiation_mode) {
            buyer.add_notification("Your bid has received a counter-bid.");
            Product product = bid.getProduct();
            buyer.add_product_to_cart_from_bid_offer(this, product, bid.getQuantity(), bid.get_offer_price());
        }

        if (bid.get_status() == BidStatus.closed_denied)
            buyer.add_notification("Your bid is denied by the store managers.");
    }

    /**
     * @param i          - 0 for add, 1 - for remove
     * @param user_email - to set
     */
    private void set_manager_in_bids(int i, String user_email) {
        for (Bid bid : this.bids.values()) {
            if (i == 0)
                bid.add_manager_of_store(user_email);
            if (i == 1)
                bid.remove_manager(user_email);
        }
    }

    public List<String> get_permissions(String manager_email) throws AppointmentException {
        List<String> permissions = new ArrayList<>();
        boolean user_exist = false;
        AssignUser user_get_permission = null;
        for (AssignUser user : stuffs_and_appointments.keySet()) {
            if (user.get_user_email().equals(manager_email)) {
                user_exist = true;
                user_get_permission = user;
            }
        }
        if (!user_exist) {
            throw new AppointmentException("This Store Stuff doesn't contains the user " + manager_email);
        }
        Appointment appointment = this.stuffs_and_appointments.get(user_get_permission);
        Map<StorePermission, Integer> manager_permissions = appointment.getPermissions();
        for (StorePermission s : manager_permissions.keySet()) {
            if (manager_permissions.get(s) == 1) {
                permissions.add(s.toString());
            }

        }
        return permissions;
    }

    public List<String> get_all_categories() {
        //  private Map<Product, Integer> inventory; // product & quantity
        List<String> categories = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            String cat = p.getCategory();
            if (!categories.contains(cat)) {
                categories.add(cat);
            }
        }
        return categories;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public Map<String, Ipredict> getPredictList() {
        return predictList;
    }

    public void setPredictList(Map<String, Ipredict> predictList) {
        this.predictList = predictList;
    }

    public HashMap<Integer, Bid> getBids() {
        return bids;
    }

    public void setBids(HashMap<Integer, Bid> bids) {
        this.bids = bids;
    }



}
