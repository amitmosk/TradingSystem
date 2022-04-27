package Domain;

import Domain.StoreModule.*;
import Domain.Utils.Utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreInformation {

    private String founder_email;
    private HashMap<String, Appointment> stuff_emails_and_appointments;
    private String name;
    String foundation_date;
    private HashMap<Product, Integer> inventory; // product & quantity
    private boolean active;
    private StoreReviewInformation storeReviewInformation;

    public StoreInformation(Store store) {
        this.founder_email = store.getFounder_email();
        this.name = store.getName();
        this.foundation_date = Utils.LocalDateToString(store.getFoundation_date());
        this.inventory = store.getInventory();
        this.active = store.is_active();
        this.storeReviewInformation = new StoreReviewInformation(store.getStoreReview());
    }
}
