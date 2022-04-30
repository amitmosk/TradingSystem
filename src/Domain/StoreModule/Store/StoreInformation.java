package Domain.StoreModule.Store;

import Domain.Utils.Utils;

public class StoreInformation {

    private final String founder_email;
    private final String store_name;
    String foundation_date;
    private StoreReviewInformation storeReviewInformation;

    public StoreInformation(Store store) {
        this.founder_email = store.getFounder_email();
        this.store_name = store.getName();
        this.foundation_date = Utils.LocalDateToString(store.getFoundation_date());
        this.storeReviewInformation = new StoreReviewInformation(store.getStoreReview());
    }
}
