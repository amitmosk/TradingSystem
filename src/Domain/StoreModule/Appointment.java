package Domain.StoreModule;

import Domain.StoreModule.Store.StoreManagerType;

import java.util.HashMap;
import java.util.LinkedList;

import static Domain.StoreModule.Store.StoreManagerType.*;
import static Domain.StoreModule.StorePermission.*;

public class Appointment {
    // -- fields
    private final String member_email;
    private final String appointer_email;
    private final int store_id;
    private StoreManagerType type;
    private HashMap<StorePermission,Integer> permissions;

    // -- constructors
    public Appointment(String manager_email, String appointer_email, int store_id, StoreManagerType type) {
        this.member_email = manager_email;
        this.appointer_email = appointer_email;
        this.store_id = store_id;
        this.permissions = new HashMap<>();
        this.type = type;
        switch (type){
            case store_founder:
                this.set_founder_permissions();
                break;
            case store_owner:
                this.set_owner_permissions();
                break;
            case store_manager:
                this.set_manager_permissions();
                break;
        }
    }


    // -- init permissions methods
    private void set_manager_permissions(){
        this.permissions.put(add_item, 1);
        this.permissions.put(remove_item, 1);
        this.permissions.put(edit_item_name, 1);
        this.permissions.put(edit_item_price, 1);
        this.permissions.put(edit_item_category, 1);
        this.permissions.put(edit_item_keywords, 1);
        this.permissions.put(view_permissions, 0);
        this.permissions.put(view_users_questions, 0);
        this.permissions.put(edit_store_policy, 0);
        this.permissions.put(edit_discount_policy, 0);
        this.permissions.put(edit_purchase_policy, 0);
        this.permissions.put(view_purchases_history, 0);
        this.permissions.put(close_store_temporarily, 0);
        this.permissions.put(open_close_store, 0);
        this.permissions.put(add_manager, 0);
        this.permissions.put(remove_manager, 0);
        this.permissions.put(add_owner, 0);
        this.permissions.put(remove_owner, 0);
        this.permissions.put(edit_permissions, 0);
    }
    private void set_owner_permissions(){
        this.permissions.put(add_item, 1);
        this.permissions.put(remove_item, 1);
        this.permissions.put(edit_item_name, 1);
        this.permissions.put(edit_item_price, 1);
        this.permissions.put(edit_item_category, 1);
        this.permissions.put(edit_item_keywords, 1);
        this.permissions.put(view_permissions, 1);
        this.permissions.put(view_users_questions, 1);
        this.permissions.put(edit_store_policy, 1);
        this.permissions.put(edit_discount_policy, 1);
        this.permissions.put(edit_purchase_policy, 1);
        this.permissions.put(view_purchases_history, 1);
        this.permissions.put(close_store_temporarily, 0);
        this.permissions.put(open_close_store, 0);
        this.permissions.put(add_manager, 1);
        this.permissions.put(remove_manager, 1);
        this.permissions.put(add_owner, 1);
        this.permissions.put(remove_owner, 1);
        this.permissions.put(edit_permissions, 1);
    }
    private void set_founder_permissions(){
        this.permissions.put(add_item, 1);
        this.permissions.put(remove_item, 1);
        this.permissions.put(edit_item_name, 1);
        this.permissions.put(edit_item_price, 1);
        this.permissions.put(edit_item_category, 1);
        this.permissions.put(edit_item_keywords, 1);
        this.permissions.put(view_permissions, 1);
        this.permissions.put(view_users_questions, 1);
        this.permissions.put(edit_store_policy, 1);
        this.permissions.put(edit_discount_policy, 1);
        this.permissions.put(edit_purchase_policy, 1);
        this.permissions.put(view_purchases_history, 1);
        this.permissions.put(close_store_temporarily, 1);
        this.permissions.put(open_close_store, 1);
        this.permissions.put(add_manager, 1);
        this.permissions.put(remove_manager, 1);
        this.permissions.put(add_owner, 1);
        this.permissions.put(remove_owner, 1);
        this.permissions.put(edit_permissions, 1);
    }

    // -- getters


    public int getStore_id() {
        return store_id;
    }

    public String getAppointer_email() {
        return appointer_email;
    }

    public String getMember_email() {
        return member_email;
    }

    // -- setters
    private void set_permission(StorePermission key, boolean value){
        if (value)
            this.permissions.put(key, 1);
        else
            this.permissions.put(key, 0);

    }

    // -- methods
    /**
     *
      * @param permission who ask to know
     * @return if this manager allowed to do it.
     */
    public boolean has_permission(StorePermission permission){
        return this.permissions.get(permission) == 1;
    }
    public void set_permissions(LinkedList<StorePermission> permissions) {
        // reset all permissions
        for (StorePermission myVar : StorePermission.values()) {
            this.set_permission(myVar, false);
        }
        for (StorePermission myVar : permissions){
            this.set_permission(myVar, true);
        }
    }

    public boolean is_owner() {
        return this.type == store_owner;
    }

    public boolean is_founder() {
        return this.type == store_founder;
    }

    public boolean is_manager() {
        return this.type == store_manager;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "member_email='" + member_email + '\'' +
                ", appointer_email='" + appointer_email + '\'' +
                ", type=" + type +
                '}';
    }
}

