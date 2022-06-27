package TradingSystem.server.Domain.StoreModule.Appointment;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.Utils.Logger.MarketLogger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static TradingSystem.server.Domain.StoreModule.Appointment.AppointmentStatus.*;
import static TradingSystem.server.Domain.StoreModule.Appointment.StoreManagerType.*;
import static TradingSystem.server.Domain.StoreModule.Appointment.StorePermission.*;

@Entity
public class Appointment {
    // -- fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AssignUser member;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AssignUser appointer;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Store store;
    @Enumerated
    private StoreManagerType type;


    // TODO : GAL HIBERNATE
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "managersEmail_AppointmentsAnswers",
            joinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "manager_email") // the key column
    private Map<String, AppointmentAgreementManagerAnswer> managersEmail_answers;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;


    @ElementCollection
    @CollectionTable(name = "permissions")
    @MapKeyColumn(name = "permission_name")
    @Column(name = "onORoff")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<StorePermission, Integer> permissions;

    // -- constructors
    public Appointment(AssignUser manager, AssignUser appointer, Store store, StoreManagerType type, List<String> managers_emails) {
        this.member = manager;
        this.appointer = appointer;
        this.store = store;
        this.permissions = new HashMap<>();
        this.type = type;
        this.managersEmail_answers = new HashMap<>();
        if (type == store_founder){
            this.status = closed_confirm;
            this.set_founder_permissions();
        }
        else if (type == store_manager){
            this.status = closed_confirm;
            this.set_manager_permissions();
        }
        else if (type == store_owner)
        {
            for (String manager_email : managers_emails) {
                AppointmentAgreementManagerAnswer temp = new AppointmentAgreementManagerAnswer();
                this.managersEmail_answers.put(manager_email, temp);
            }
            this.status = open_waiting_for_answers;
            this.set_candidate_permissions();
            this.update_status();
        }

    }

    public Appointment() {
    }

    // -- init permissions methods
    private void set_manager_permissions() {
        this.permissions.put(add_item, 1);
        this.permissions.put(remove_item, 1);
        this.permissions.put(edit_item_name, 1);
        this.permissions.put(edit_item_price, 1);
        this.permissions.put(edit_item_category, 1);
        this.permissions.put(edit_item_keywords, 1);
        this.permissions.put(view_permissions, 0);
        this.permissions.put(view_users_questions, 1);
        this.permissions.put(edit_store_policy, 0);
        this.permissions.put(edit_discount_policy, 0);
        this.permissions.put(edit_purchase_policy, 0);
        this.permissions.put(view_purchases_history, 1);
        this.permissions.put(close_store_temporarily, 0);
        this.permissions.put(open_close_store, 0);
        this.permissions.put(add_manager, 0);
        this.permissions.put(remove_manager, 0);
        this.permissions.put(add_owner, 0);
        this.permissions.put(remove_owner, 0);
        this.permissions.put(edit_permissions, 0);
        this.permissions.put(answer_bid_offer, 0);
        this.permissions.put(view_bids_status, 0);
        this.permissions.put(answer_bid_offer_negotiate, 0);
        this.permissions.put(answer_appointment, 0);
//        HibernateUtils.merge(this);
    }

    private void set_owner_permissions() {
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
        this.permissions.put(answer_bid_offer_negotiate, 0);
        this.permissions.put(answer_appointment, 1);
//        HibernateUtils.merge(this);
    }

    private void set_founder_permissions() {
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
        this.permissions.put(answer_bid_offer_negotiate, 1);
        this.permissions.put(answer_appointment, 1);
//        HibernateUtils.merge(this);
    }

    private void set_candidate_permissions(){
        this.permissions.put(add_item, 0);
        this.permissions.put(remove_item, 0);
        this.permissions.put(edit_item_name, 0);
        this.permissions.put(edit_item_price, 0);
        this.permissions.put(edit_item_category, 0);
        this.permissions.put(edit_item_keywords, 0);
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
        this.permissions.put(answer_bid_offer_negotiate, 0);
//        HibernateUtils.merge(this);
    }

    // -- getters


    public AssignUser getMember() {
        return member;
    }

    public AssignUser getAppointer() {
        return appointer;
    }

    public Store getStore() {
        return store;
    }

    public StoreManagerType getType() {
        if (this.get_status()!=closed_confirm)
            return candidate;
        return type;
    }

    public Map<StorePermission, Integer> getPermissions() {
        return permissions;
    }

    // -- setters
    private void set_permission(StorePermission key, boolean value) {
        if (this.get_status() != closed_confirm){
            MarketLogger.getInstance().add_log("Failure, Try To Set Permission To Candidate: "+this.member.get_user_email());
            return;
        }
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

    public void setPermissions(Map<StorePermission, Integer> permissions) {
        if (this.get_status() != closed_confirm) {
            MarketLogger.getInstance().add_log("Failure, Try To Set Permission To Candidate: " + this.member.get_user_email());
            return;
        }
            this.permissions = permissions;

    }

    // -- methods

    /**
     * @param permission who ask to know
     * @return if this manager allowed to do it.
     */
    public boolean has_permission(StorePermission permission) {
        return this.permissions.get(permission) == 1;
    }

    public void set_permissions(List<StorePermission> permissions) {
        // reset all permissions
        if (this.get_status() != closed_confirm) {
            MarketLogger.getInstance().add_log("Failure, Try To Set Permission To Candidate: " + this.member.get_user_email());
            return;
        }
        for (StorePermission myVar : StorePermission.values()) {
            this.set_permission(myVar, false);
        }
        for (StorePermission myVar : permissions) {
            this.set_permission(myVar, true);
        }
    }

    public boolean is_owner() {
        return this.getType() == store_owner;
    }

    public boolean is_founder() {
        return this.getType() == store_founder;
    }

    public boolean is_manager() {
        return this.getType() == store_manager;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }









    // Appointment agreement
    public void add_manager_of_store(String manager_email) {
        this.managersEmail_answers.put(manager_email, new AppointmentAgreementManagerAnswer());
//        HibernateUtils.merge(this);
    }

    public void remove_manager(String email) {
        this.managersEmail_answers.remove(email);
//        HibernateUtils.merge(this);
    }

    public void add_manager_answer(String email, boolean answer) {
        this.managersEmail_answers.get(email).setHas_answer(true);
        this.managersEmail_answers.get(email).setAnswer(answer);
        if (!answer)
            this.status = closed_denied;
        else
            this.update_status();

//        HibernateUtils.merge(this);

    }

    public AppointmentStatus get_status() {
        return this.status;
    }



    private void update_status() {
        if (this.status == closed_denied)
            return;
        if (this.status == closed_confirm)
            return;
        for (AppointmentAgreementManagerAnswer answer : this.managersEmail_answers.values()){
            if (!answer.get_has_answer()){
                this.status = open_waiting_for_answers;
                return;
            }
        }
        this.status = closed_confirm;
        switch (type) {
            case store_owner:
                this.set_owner_permissions();
                break;
            case store_manager:
                this.set_manager_permissions();
                break;
        }
    }

    public AppointmentInformation get_appointment_information() {
        return new AppointmentInformation(this.member.get_user_email(), this.appointer.get_user_email(), this.type.toString(), this.get_status().toString());
    }
}




