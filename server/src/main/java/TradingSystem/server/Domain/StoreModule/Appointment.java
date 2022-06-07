package TradingSystem.server.Domain.StoreModule;

import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagerType;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static TradingSystem.server.Domain.StoreModule.Store.StoreManagerType.*;
import static TradingSystem.server.Domain.StoreModule.StorePermission.*;

public class Appointment {
    // -- fields
    private AssignUser member;
    private AssignUser appointer;
    private Store store;

    private StoreManagerType type;
    private HashMap<StorePermission,Integer> permissions;

    // -- constructors
    public Appointment(AssignUser manager, AssignUser appointer, Store store, StoreManagerType type) {
        this.member = manager;
        this.appointer = appointer;
        this.store = store;
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

    public Appointment() {
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
        this.permissions.put(answer_bid_offer, 0);
        this.permissions.put(view_bids_status, 0);
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
        this.permissions.put(answer_bid_offer, 1);
        this.permissions.put(view_bids_status, 1);
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
        this.permissions.put(answer_bid_offer, 1);
        this.permissions.put(view_bids_status, 1);
    }

    // -- getters


    public AssignUser getMember() {
        return member; }

    public AssignUser getAppointer() {
        return appointer;
    }

    public Store getStore() {
        return store;
    }

    public StoreManagerType getType() {
        return type;
    }

    public HashMap<StorePermission, Integer> getPermissions() {
        return permissions;
    }
    // -- setters
    private void set_permission(StorePermission key, boolean value){
        if (value)
            this.permissions.put(key, 1);
        else
            this.permissions.put(key, 0);

    }

    public void setMember(AssignUser member) {
        this.member = member;
    }

    public void setAppointer(AssignUser appointer) {
        this.appointer = appointer;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setType(StoreManagerType type) {
        this.type = type;
    }

    public void setPermissions(HashMap<StorePermission, Integer> permissions) {
        this.permissions = permissions;
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
    public void set_permissions(List<StorePermission> permissions) {
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

/*    @Override
    public String toString() {
        return "Appointment{" +
                "member_email='" + member_email + '\'' +
                ", appointer_email='" + appointer_email + '\'' +
                ", type=" + type +
                '}';
    }*/
}

