package Domain.StoreModule;

import java.util.HashMap;

public class StoreManagersInfo {
    private HashMap<String, Appointment> managers; // email & Appointment
    private String store_name;

    public StoreManagersInfo(String store_name, HashMap<String, Appointment> managers) {
        this.store_name = store_name;
        this.managers = managers;
    }

    public String get_management_information() {
        String answer = "Store name :" + this.store_name + "\n";
        for (Appointment appointment : this.managers.values())
        {
            answer = answer + appointment.toString() + "\n";
        }
        return answer;
    }
}
