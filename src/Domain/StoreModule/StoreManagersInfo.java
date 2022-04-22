package Domain.StoreModule;

import java.util.HashMap;

public class StoreManagersInfo {
    private HashMap<Integer, Permission> managers;
    public StoreManagersInfo(HashMap<Integer, Permission> managers) {
        this.managers = managers;
    }
}
