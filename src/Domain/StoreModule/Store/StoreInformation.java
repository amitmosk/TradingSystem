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
        this.foundation_date = store.getFoundation_date();
        this.storeReviewInformation = new StoreReviewInformation(store.getStoreReview());
    }

//    public String toString() {
//        String founder_name = "----------------------";
//        StringBuilder info = new StringBuilder();
//        info.append("Store info: "+this.name+"\n");
//        info.append("\tStore founder: "+ founder_name +"\n");
//        info.append("\tStore owners: ");
//        for (String email : stuff_emails_and_appointments.keySet())
//        {
//            String name = "";
//            info.append(name+", ");
//        }
//        info.append("\n");
//        info.append("\tStore managers: ");
//        for (String email : stuff_emails_and_appointments.keySet())
//        {
//            String name = "";
//            info.append(name+", ");
//        }
//        info.append("\n");
//        info.append("\tfoundation date: "+ Utils.DateToString(this.foundation_date)+"\n");
//
//
//        //products
//
//
//        String is_active;
//        if (active)
//            is_active="Yes";
//        else
//            is_active="No";
//
//        info.append("\tactive: "+ is_active+"\n");
//        info.append("\tpurchase policy: "+ this.purchasePolicy+"\n");
//        info.append("\tdiscount policy: "+ this.discountPolicy+"\n");
//
//        return info.toString();
//
//    }
}
