package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.Domain.StoreModule.Appointment;

import java.util.HashMap;

public class StoreManagersInfo {
    private HashMap<String, Appointment> managers; // email & Appointment
    private String store_name;

    // ------------------------------ constructors ------------------------------
    public StoreManagersInfo(String store_name, HashMap<String, Appointment> managers) {
        this.store_name = store_name;
        this.managers = managers;
    }

    public StoreManagersInfo() {
    }

    // ------------------------------ getters ------------------------------

    public HashMap<String, Appointment> getManagers() {
        return managers;
    }

    public String getStore_name() {
        return store_name;
    }

    // ------------------------------ setters ------------------------------

    public void setManagers(HashMap<String, Appointment> managers) {
        this.managers = managers;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    // ------------------------------ methods ------------------------------

    public String get_management_information() {
        StringBuilder answer = new StringBuilder("Store name :" + this.store_name + "\n");
        for (Appointment appointment : this.managers.values())
        {
            answer.append(appointment.toString()).append("\n");
        }
        return answer.toString();
    }

    public Appointment getMemberAppopintment(String email){
        return this.managers.get(email);
    }
}
