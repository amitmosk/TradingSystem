package Domain.StoreModule;

import java.util.HashMap;
import java.util.LinkedList;

public class Store {
    // -- fields
    private int store_id;
    private int founder_id;
    private HashMap<Integer, Permission> owner_ids;
    private HashMap<Integer, Permission> manager_ids;
    private String name;
    private String foundation_date;
    private HashMap<Integer, Product> products;
    private boolean active;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private HashMap<Integer, Question> users_questions; // question_id x question
    private HashMap<Integer, Purchase> purchases_history;

    // -- constructor

    public Store(int store_id, int founder_id, String name) {
        this.store_id = store_id;
        this.founder_id = founder_id;
        this.name = name;
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
        return new StoreManagersInfo(this.manager_ids);
    }
    public void set_permissions(int user_id, int manager_id, LinkedList<StorePermission> permissions) {
        // check that the manager appointed by the user
        if (this.get_appointer(manager_id) != user_id)
            throw new IllegalArgumentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager_id == user_id)
            throw new IllegalArgumentException("User cant change himself permissions");

        Permission manager_permission = this.owner_ids.get(manager_id);
        manager_permission.set_permissions(permissions);

    }
    public boolean is_active() {
        return this.active;
    }
    public int get_appointer(int manager_id) {
        return this.manager_ids.get(manager_id).getAppointer_id();
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

    public HashMap<Integer, Purchase> view_store_purchases_history(int user_id) throws IllegalAccessException {
        this.check_permission(user_id, StorePermission.view_purchases_history);
        return this.purchases_history;
    }

    public boolean close_store_permanently(int user_id) {
        if (!this.is_active())
            return false;
        this.active = false;
        // TODO: 22/04/2022 : send message to all of the managers & owners.
        this.founder_id = -1;
        this.manager_ids = null;
        return true;
    }

    public void check_permission(int user_id, StorePermission permission) throws IllegalAccessException {
        // not just managers - FIX
        if(!(this.manager_ids.get(user_id).has_permission(permission)))
            throw new IllegalAccessException("User has no permissions!");
    }
}
