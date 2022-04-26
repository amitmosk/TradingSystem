package Domain.StoreModule;

import java.util.HashMap;

public class StoreManagersInfo {
    private HashMap<Integer, Appointment> managers;
    public StoreManagersInfo(HashMap<Integer, Appointment> managers) {
        this.managers = managers;
    }
}
