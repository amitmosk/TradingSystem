package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.Domain.StoreModule.Appointment;

import java.util.HashMap;

public class StoreManagersInfo {
    private HashMap<String, Appointment> managers; // email & Appointment
    private String store_name;

    public StoreManagersInfo(String store_name, HashMap<String, Appointment> managers) {
        this.store_name = store_name;
        this.managers = managers;
    }

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
