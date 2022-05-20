package TradingSystem.server.Domain.StoreModule.Store;

public class StoreInformation {

    private String founder_email;
    private String store_name;
    String foundation_date;
    private StoreReviewInformation storeReviewInformation;

    // ------------------------------ constructors ------------------------------
    public StoreInformation(Store store) {
        String founder_email1 = "";
        try{ founder_email1 = store.getFounder().get_user_email();}
        catch (Exception e){}
        this.founder_email = founder_email1;
        this.store_name = store.getName();
        this.foundation_date = store.getFoundation_date();
        this.storeReviewInformation = new StoreReviewInformation(store.getStoreReview());
    }

    public StoreInformation() {
    }

    // ------------------------------ setters  ------------------------------

    public void setFounder_email(String founder_email) {
        this.founder_email = founder_email;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setFoundation_date(String foundation_date) {
        this.foundation_date = foundation_date;
    }

    public void setStoreReviewInformation(StoreReviewInformation storeReviewInformation) {
        this.storeReviewInformation = storeReviewInformation;
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

    // ------------------------------ getters ------------------------------
    public String getFounder_email() {
        return founder_email;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getFoundation_date() {
        return foundation_date;
    }

    public StoreReviewInformation getStoreReviewInformation() {
        return storeReviewInformation;
    }
}
