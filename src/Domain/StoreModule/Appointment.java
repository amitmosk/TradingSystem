package Domain.StoreModule;

import java.util.HashMap;
import java.util.LinkedList;

import static Domain.StoreModule.StoreManagerType.store_founder;
import static Domain.StoreModule.StorePermission.*;

public class Appointment {
    // -- fields
    private final int manager_id;
    private final int appointer_id;
    private final int store_id;
    private StoreManagerType type;
    private HashMap<StorePermission,Boolean> permissions;

    // -- constructors
    public Appointment(int manager_id, int appointer_id, int store_id, StoreManagerType type) {
        this.manager_id = manager_id;
        this.appointer_id = appointer_id;
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
    }
    private void set_owner_permissions(){
        this.permissions.put(add_item, true);
        this.permissions.put(remove_item, true);
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
    }
    private void set_founder_permissions(){
        this.permissions.put(add_item, true);
        this.permissions.put(remove_item, true);
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
    }

    // -- getters
    public int getManager_id() {
        return manager_id;
    }
    public int getStore_id() {
        return store_id;
    }
    public int getAppointer_id() {
        return appointer_id;
    }

    // -- setters
    private void set_permission(StorePermission key, boolean value){
        this.permissions.put(key, value);
    }
    public void setType(StoreManagerType type) {
        if (type == store_founder)
            throw new IllegalArgumentException("Cant appoint founder");
        this.type = type;
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
}
