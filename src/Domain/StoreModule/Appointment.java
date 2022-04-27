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
    private HashMap<StorePermission,Boolean> permissions;

    // -- constructors
    public Appointment(String manager_email, String appointer_email, int store_id, StoreManagerType type) {
        this.member_email = manager_email;
        this.appointer_email = appointer_email;
        this.store_id = store_id;
        this.permissions = new HashMap<StorePermission, Boolean>();
        this.type = type;
        switch (type){
            case store_founder:
                this.set_founder_permissions();
            case store_owner:
                this.set_owner_permissions();
            case store_manager:
                this.set_manager_permissions();
        }
    }


    // -- init permissions methods
    private void set_manager_permissions(){
        this.permissions.put(add_item, true);
        this.permissions.put(remove_item, true);
        this.permissions.put(edit_item_name, true);
        this.permissions.put(edit_item_price, true);
        this.permissions.put(edit_item_category, true);
        this.permissions.put(edit_item_keywords, true);
        this.permissions.put(view_permissions, false);
        this.permissions.put(view_users_questions, false);
        this.permissions.put(edit_store_policy, false);
        this.permissions.put(edit_discount_policy, false);
        this.permissions.put(edit_purchase_policy, false);
        this.permissions.put(view_purchases_history, false);
        this.permissions.put(close_store_temporarily, false);
        this.permissions.put(open_close_store, false);
        this.permissions.put(add_manager, false);
        this.permissions.put(remove_manager, false);
        this.permissions.put(add_owner, false);
        this.permissions.put(remove_owner, false);
        this.permissions.put(edit_permissions, false);
    }
    private void set_owner_permissions(){
        this.permissions.put(add_item, true);
        this.permissions.put(remove_item, true);
        this.permissions.put(edit_item_name, true);
        this.permissions.put(edit_item_price, true);
        this.permissions.put(edit_item_category, true);
        this.permissions.put(edit_item_keywords, true);
        this.permissions.put(view_permissions, true);
        this.permissions.put(view_users_questions, true);
        this.permissions.put(edit_store_policy, true);
        this.permissions.put(edit_discount_policy, true);
        this.permissions.put(edit_purchase_policy, true);
        this.permissions.put(view_purchases_history, true);
        this.permissions.put(close_store_temporarily, false);
        this.permissions.put(open_close_store, false);
        this.permissions.put(add_manager, true);
        this.permissions.put(remove_manager, true);
        this.permissions.put(add_owner, true);
        this.permissions.put(remove_owner, true);
        this.permissions.put(edit_permissions, true);
    }
    private void set_founder_permissions(){
        this.permissions.put(add_item, true);
        this.permissions.put(remove_item, true);
        this.permissions.put(edit_item_name, true);
        this.permissions.put(edit_item_price, true);
        this.permissions.put(edit_item_category, true);
        this.permissions.put(edit_item_keywords, true);
        this.permissions.put(view_permissions, true);
        this.permissions.put(view_users_questions, true);
        this.permissions.put(edit_store_policy, true);
        this.permissions.put(edit_discount_policy, true);
        this.permissions.put(edit_purchase_policy, true);
        this.permissions.put(view_purchases_history, true);
        this.permissions.put(close_store_temporarily, true);
        this.permissions.put(open_close_store, true);
        this.permissions.put(add_manager, true);
        this.permissions.put(remove_manager, true);
        this.permissions.put(add_owner, true);
        this.permissions.put(remove_owner, true);
        this.permissions.put(edit_permissions, true);
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
        this.permissions.put(key, value);
    }

    // -- methods
    /**
     *
      * @param permission who ask to know
     * @return if this manager allowed to do it.
     */
    public boolean has_permission(StorePermission permission){
        return this.permissions.get(permission);
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

